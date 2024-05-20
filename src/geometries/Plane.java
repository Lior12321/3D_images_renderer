package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Plane class represents a plane in 3D space, defined by either three
 * points on the plane or a normal vector and a base point.
 * 
 * @author Lior &amp; Asaf
 */
public class Plane implements Geometry {
	private Point base; // the base point of the plane
	private Vector normal; // the narmal vector of the plane

	/**
	 * Constructs a Plane given three points. The first point is used as the base
	 * point of the plane, and the normal to the plane is calculated based on the
	 * three points.
	 *
	 * @param p1 the first point on the plane, used as the base point
	 * @param p2 the second point on the plane
	 * @param p3 the third point on the plane
	 */
	Plane(Point p1, Point p2, Point p3) {
		base = p1;
		normal = null; // TODO In the next step we will normalize it. For now, it's NULL
	}

	/**
	 * Constructs a Plane given a normal vector and a base point. The normal vector
	 * is normalized.
	 *
	 * @param normal the normal vector to the plane
	 * @param p1     the base point on the plane
	 */
	Plane(Vector normal, Point p1) {
		this.normal = normal.normalize();
		base = p1;
	}

	/**
	 * get the normal of the plane
	 * @return the normal
	 */
	public Vector getNormal() {
		return normal;
	}

	/**
	 * get the normal of a specific point of the plane (which is the same for all points of the plane)
	 * @param point the point we want it's normal
	 * @return the normal
	 */
	@Override
	public Vector getNormal(Point point) {
		return normal;
	}

}
