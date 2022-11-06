package MapGenerator;

import java.util.Vector;

import MapGenerator.delaunator.DPoint;

public class Map {
	public Vector<DPoint> points;
	public int numRegions;
	public double numTriangles;
	public int[] halfEdges;
	public int numEdges;
	public int[] triangles;
	public Vector<DPoint> centers;
	public Vector<Double> elevation;
}
