package geometries;
import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
	private Point base;
	private Vector normal;
	
	/**
	 * The builder gets three points and set one of them to be the base point to the plane,
	 * and calculate the normal to the plane   
	 * 
	 * @param p1 point 1 to the plane
	 * @param p2 point 2 to the plane
	 * @param p3 point 3 to the plane
	 */
	Plane(Point p1, Point p2, Point p3) {
		base = p1;
		normal = null; // In the next step we will normalize if. for now it's NULL
	}

	Plane (Vector normal, Point p1) {
		this.normal = normal.normalize();
		base = p1;
	}

	@Override
	public Vector getNormal()
	{
		return normal.normalize();
	}
	
	@Override
	public Vector getNormal(Point p)
	{
		return normal.normalize();
	}

	@Override
	public Vector getNormal(Point... p) {
		// TODO Auto-generated method stub
		return null;
	}

}
