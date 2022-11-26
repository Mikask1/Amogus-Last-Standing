package MapGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Vector;
import java.util.List;
import java.util.Random;

import MapGenerator.delaunator.DPoint;
import MapGenerator.delaunator.Delaunator;
import main.GamePanel;

public class Voronoi {
	int screenWidth;
	int screenHeight;

	// CONSTANTS
	final int GRIDSIZE = 25;
	final double JITTER = 0.5;
	final double WAVELENGTH = 0.5;

	public Map map = new Map();
	GamePanel gp;
	private Vector<DPoint> points = new Vector<DPoint>();
	private Delaunator delaunator;

	public Voronoi(int screenWidth, int screenHeight, GamePanel gp) {
		this.gp = gp;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;

		for (int x = 0; x <= GRIDSIZE; x++) {
			for (int y = 0; y <= GRIDSIZE; y++) {
				double randX = (x + JITTER * (Math.random() - Math.random())) * (screenWidth / GRIDSIZE);
				double randY = (y + JITTER * (Math.random() - Math.random())) * (screenHeight / GRIDSIZE);
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
			double nx = ((points.get(r).x / (screenWidth / GRIDSIZE)) / GRIDSIZE - 0.5);
			double ny = ((points.get(r).y / (screenHeight / GRIDSIZE)) / GRIDSIZE - 0.5);

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

		g2.setColor(Color.black);

		for (int e = 0; e < map.numEdges; e++) {
			if (e < delaunator.halfedges[e]) {
				DPoint p = map.centers.get(delaunator.triangleOfEdge(e));
				DPoint q = map.centers.get(delaunator.triangleOfEdge(map.halfEdges[e]));
				g2.drawLine((int) p.x, (int) p.y, (int) q.x, (int) q.y);
			}
		}
	}

	public void drawCellColors(Graphics2D g2) {
		Vector<Integer> seen = new Vector<Integer>();

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

				// System.out.println(map.elevation.get(r));
				if (map.elevation.get(r) < 0.3) {
					g2.setColor(Color.white);
				} else {
					g2.setColor(new Color(180, 101, 30));
				}

				g2.fillPolygon(verticesX, verticesY, verticesX.length);
			}
		}
	}
}