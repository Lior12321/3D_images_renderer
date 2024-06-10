package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import static primitives.Util.*;
import primitives.Vector;

/**
 * The Plane class represents a plane in 3D space, defined by either three
 * points on the plane or a normal vector and a base point.
 * 
 * @author Lior &amp; Asaf
 */
public class Plane implements Geometry {
	/**
	 * The base point of the plane.
	 */
	private final Point base;

	/**
	 * The normal vector to the plane.
	 */
	private final Vector normal;

	/**
	 * Constructs a Plane given three points. The first point is used as the base
	 * point of the plane, and the normal to the plane is calculated based on the
	 * three points.
	 *
	 * @param p1 the first point on the plane, used as the base point
	 * @param p2 the second point on the plane
	 * @param p3 the third point on the plane
	 * @throws IllegalArgumentException when there are convergent points or the
	 *                                  points are co-linear
	 */
	public Plane(Point p1, Point p2, Point p3) {
		base = p1;
		normal = ((p2.subtract(p1)).crossProduct(p3.subtract(p1))).normalize();
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
	 * Returns the normal vector of the plane.
	 *
	 * @return the normal vector, which is perpendicular to the surface or plane
	 *         defined by the entity.
	 */
	public Vector getNormal() {
		return normal;
	}

	@Override
	public Vector getNormal(Point point) {
		return normal;
	}

	// getters:
	/**
	 * Returns the base point.
	 *
	 * @return the base point
	 */
	public Point getBase() {
		return base;
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		if (isZero(normal.dotProduct(ray.getDir()))) {
			return null;
		}

		if (base.equals(ray.getHead())) {
			return null;
		}
		double t = alignZero(base.subtract(ray.getHead()).dotProduct(normal)) / (ray.getDir().dotProduct(normal));
		if (t <= 0) {
			return null;
		}
		return List.of(ray.getPoint(t));
	}
}

