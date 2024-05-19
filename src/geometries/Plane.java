package geometries;
import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
	private Point base;
	private Vector normal;
	
	
	Plane(Point p1, Point p2, Point p3) {
		base = p1;
		normal = null; // In the next step we will normalize if. for now it's NULL
	}

	Plane (Vector normal, Point p1) {
		this.normal = normal.normalize(); // TODO: Normalize this vector
		base = p1;
	}

	@Override
	public Vector getNormal()
	{
		return null;
	}
	
	@Override
	public Vector getNormal(Point p)
	{
		return null;
	}

	@Override
	public String toString() {
		return "Plane{" + "q0=" + q0 + ", normal=" + normal + '}';
	}
}
