package primitives;

import static primitives.Util.*;
import geometries.Intersectable.GeoPoint;
import java.util.List;

/**
 * Represents a ray in 3D space. A ray is defined by a starting point (head) and
 * a direction vector.
 * 
 * @author Lior &amp; Asaf
 */
public class Ray {

	/**
	 * The starting point of the ray.
	 */
	private final Point head;

	/**
	 * The direction vector of the ray.
	 */
	protected final Vector direction;

	/**
	 * Constructs a new Ray with the specified head and direction.
	 *
	 * @param head      the starting point of the ray
	 * @param direction the direction vector of the ray
	 */
	public Ray(Point head, Vector direction) {
		this.head = head;
		this.direction = direction.normalize();
	}

	// getters:

	/**
	 * Returns the direction vector of the ray.
	 * 
	 * @return the direction vector of the ray.
	 */
	public Vector getDir() {
		return direction;
	}

	/**
	 * Returns the starting point of the ray.
	 *
	 * @return the starting point of the ray
	 */
	public Point getHead() {
		return head;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
	}

	@Override
	public String toString() {
		return head + "->" + direction;
	}

	/**
	 * get Point in specific distance in the direction of the ray
	 * 
	 * @param t scalar. the distance to the new Point
	 * @return the wanted point
	 */
	public Point getPoint(double t) {
		return isZero(t) ? head : head.add(direction.scale(t));
	}

	/**
	 * finds the closest point to the ray's origin point and returns it
	 * 
	 * @param points list of points to check
	 * @return closest point or null if the list is empty
	 */
	public Point findClosestPoint(List<Point> points) {
		return points == null || points.isEmpty() ? null
				: findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}

	/**
	 * Returns the closest GeoPoint along the ray.
	 * 
	 * @param points GeoPoints to check
	 * @return closest GeoPoint
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
		GeoPoint closest = null;
		double minDistance = Double.POSITIVE_INFINITY;
		double calcDistance;

		for (GeoPoint point : intersections) {
			calcDistance = point.point.distanceSquared(head);
			if (calcDistance < minDistance) {
				closest = point;
				minDistance = calcDistance;
			}
		}
		return closest;
	}

}
