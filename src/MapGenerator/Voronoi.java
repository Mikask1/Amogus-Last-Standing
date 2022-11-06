package MapGenerator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;
import java.util.Vector;

public class Voronoi {
	
	final int GRIDSIZE = 25;
	
	final double JITTER = 0.5;
	Vector<Point> points = new Vector<Point>();
	Random random = new Random();

	public Voronoi(int screenWidth, int screenHeight) {
		for (int x = 0; x <= GRIDSIZE; x++) {
			for (int y = 0; y <= GRIDSIZE; y++) {
				int randX = (int) (x + JITTER * (random.nextDouble() - random.nextDouble())) * screenWidth/GRIDSIZE;
				int randY = (int) (y + JITTER * (random.nextDouble() - random.nextDouble())) * screenHeight/GRIDSIZE;
				Point point = new Point(randX, randY);
				points.add(point);
			}
		}
	}

	public void paint(Graphics2D g2) {
		g2.setColor(new Color(150, 75, 0));

		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			g2.fillRect(point.x, point.y, 10, 10);
		}
	}
}