package MapGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.Random;

import MapGenerator.delaunator.DPoint;
import MapGenerator.delaunator.Delaunator;
import main.GamePanel;

public class Voronoi {
	public int mapWidth;
	public int mapHeight;

	// CONSTANTS
	final int GRIDSIZE = 15;
	final double JITTER = 0.5;
	final double WAVELENGTH = 0.5;
	final double THRESHOLD = 0.4;
	final int PADDING = 100;

	public Map map = new Map();
	GamePanel gp;
	private Vector<DPoint> points = new Vector<DPoint>();
	private Delaunator delaunator;

	private BufferedImage image;
	private BufferedImage imageSecondary;
	private boolean toggle = true;

	public Vector<Rectangle> lava;
	public Vector<Image> landImage;
	public Vector<Rectangle> land;

	public Voronoi(int mapWidth, int mapHeight, GamePanel gp) {
		try {
//			TODO: get better textured dirt
			imageSecondary = ImageIO.read(getClass().getResourceAsStream("/tiles/dirtSecondary.jpg"));
			image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.gp = gp;
		this.mapHeight = mapHeight;
		this.mapWidth = mapWidth;

		for (int x = 0; x <= GRIDSIZE; x++) {
			for (int y = 0; y <= GRIDSIZE; y++) {
				double randX = (x + JITTER * (Math.random() - Math.random())) * (mapWidth / GRIDSIZE);
				double randY = (y + JITTER * (Math.random() - Math.random())) * (mapHeight / GRIDSIZE);
				DPoint point = new DPoint(randX, randY);
				points.add(point);
			}
		}

		delaunator = new Delaunator(points);

		map.points = points;
		map.triangles = delaunator.triangles;
		map.halfEdges = delaunator.halfedges;
		map.numEdges = delaunator.halfedges.length;
		map.numRegions = delaunator.points.length;
		map.numTriangles = delaunator.triangles.length;
		map.centers = calculateCentroids();
		map.elevation = assignElevation();
		processCells();
	}

	private Vector<DPoint> calculateCentroids() {
		int numTriangles = delaunator.halfedges.length / 3;
		Vector<DPoint> centroids = new Vector<DPoint>();

		for (int t = 0; t < numTriangles; t++) {
			int sumOfX = 0, sumOfY = 0;
			for (int i = 0; i < 3; i++) {
				int s = 3 * t + i;
				DPoint p = this.points.get(delaunator.triangles[s]);
				sumOfX += p.x;
				sumOfY += p.y;
			}
			centroids.insertElementAt(new DPoint(sumOfX / 3, sumOfY / 3), t);
		}
		return centroids;
	}

	private Vector<Double> assignElevation() {
		Random random = new Random();

		SimplexNoise noise = new SimplexNoise(1, 0.5, random.nextLong());

		Vector<Double> elevation = new Vector<Double>();

		for (int r = 0; r < map.numRegions; r++) {
			double nx = ((points.get(r).x / (mapWidth / GRIDSIZE)) / GRIDSIZE - 0.5);
			double ny = ((points.get(r).y / (mapHeight / GRIDSIZE)) / GRIDSIZE - 0.5);

			elevation.insertElementAt((1 + noise.noise(nx / WAVELENGTH, ny / WAVELENGTH)) / 2, r);

			double d = (2 * Math.max(Math.abs(nx), Math.abs(ny)));

			elevation.insertElementAt((1 + elevation.get(r) - d) / 2, r);
		}

		return elevation;
	}

	public void drawPoints(Graphics2D g2) {
		g2.setColor(new Color(150, 75, 0));

		for (int i = 0; i < points.size(); i++) {
			DPoint point = points.get(i);
			g2.fillRect((int) point.x, (int) point.y, 10, 10);
		}
	}

	public void drawCells(Graphics2D g2) {

		int screenX = gp.player.screenX - gp.player.worldX;
		int screenY = gp.player.screenY - gp.player.worldY;

		g2.setColor(Color.black);

		for (int e = 0; e < map.numEdges; e++) {
			if (e < delaunator.halfedges[e]) {
				DPoint p = map.centers.get(delaunator.triangleOfEdge(e));
				DPoint q = map.centers.get(delaunator.triangleOfEdge(map.halfEdges[e]));
				g2.drawLine((int) p.x + screenX, (int) p.y + screenY, (int) q.x + screenX, (int) q.y + screenY);
			}
		}
	}

	public void processCells() {
//		TODO: P3 Make all of these Rectangles in the first place (performance, simpler)
//		TODO: P2 Make drawCellColors() using vectors instead of arrays (performance, simpler)

//    	ALTERNATIVE
//		TODO: P1 Make the map bounding box the inside of the polygon (O(n^3)) -> might be worth checking out -> no code on the internet

		Vector<Integer> seen = new Vector<Integer>();
		land = new Vector<Rectangle>();
		landImage = new Vector<Image>();
		lava = new Vector<Rectangle>();

		int screenX = gp.player.screenX - gp.player.worldX;
		int screenY = gp.player.screenY - gp.player.worldY;

		for (int e = 0; e < map.numEdges; e++) {
			int r = map.triangles[delaunator.nextHalfEdge(e)];
			if (!seen.contains(r)) {
				seen.add(r);

				List<Integer> edgesAroundPoint = delaunator.edgesAroundPoint(e);

				int[] verticesX = new int[edgesAroundPoint.size()];
				int[] verticesY = new int[edgesAroundPoint.size()];

				for (int i = 0; i < edgesAroundPoint.size(); i++) {
					int edge = edgesAroundPoint.get(i);
					DPoint vertice = map.centers.get(delaunator.triangleOfEdge(edge));

					verticesX[i] = (int) vertice.x + screenX;
					verticesY[i] = (int) vertice.y + screenY;
				}

				Shape polygon = new Polygon(verticesX, verticesY, verticesX.length);
				Rectangle bounds = polygon.getBounds();
				if (map.elevation.get(r) > THRESHOLD) {
					land.add(bounds);

					int scaleX = bounds.width + (2 * gp.player.footArea.width) + 2 * PADDING;
					int scaleY = bounds.height + (2 * gp.player.footArea.height) + 2 * PADDING;

					if (toggle) {
						landImage.add(image.getScaledInstance(scaleX, scaleY, Image.SCALE_DEFAULT));
					} else {
						landImage.add(imageSecondary.getScaledInstance(scaleX, scaleY, Image.SCALE_DEFAULT));
					}
				} else {
					lava.add(bounds);
				}

				toggle = !toggle;
			}
		}

		land.trimToSize();
		landImage.trimToSize();
		lava.trimToSize();
	}

	public void drawCellColors(Graphics2D g2) {

		int screenX = gp.player.screenX - gp.player.worldX;
		int screenY = gp.player.screenY - gp.player.worldY;

		int solidX = gp.player.footArea.width + PADDING;
		int solidY = gp.player.footArea.height + PADDING;

		for (int i = 0; i < land.size(); i++) {
			g2.drawImage(landImage.get(i), land.get(i).x + screenX - solidX, land.get(i).y + screenY - solidY, null);
			// Bounding Box
//			g2.drawRect(land.get(i).x + screenX - solidX, land.get(i).y + screenY - solidY,
//					land.get(i).width + 2 * solidX, land.get(i).height + 2 * solidY);
		}

	}

	public boolean inside(int top, int bottom, int left, int right) {
		for (int i = 0; i < land.size(); i++) {
			Rectangle mapRect = land.get(i);

			int width = gp.player.footArea.width + PADDING;
			int height = gp.player.footArea.height + PADDING;

			if (right <= (mapRect.x + mapRect.width + width) && left >= mapRect.x - width && top >= mapRect.y - height
					&& bottom <= (mapRect.y + mapRect.height + height)) {
				return true;
			}
		}
		return false;
	}
}
