package geometries;
import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
	private final Point base;
	private final Vector normal;
	
	
	Plane(Point p1, Point p2, Point p3)
	{
		base = p1;
		normal =null;
	}
	Plane (Vector normal, Point p1)
	{
		this.normal =normal; //צריך לנרמל את הווקטור הזה
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
}
