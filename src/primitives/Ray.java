package primitives;

import static geometries.Intersectable.GeoPoint;
import java.util.List;

/**
 * Represents a ray in 3D space. A ray is defined by a starting point (head) and
 * a direction vector.
 * 
 * @author Lior &amp; Asaf
 */
public class Ray {
	/** The constant value used to determine the accuracy of the calculations. */
	public static final double DELTA = 0.00001;

	/** The starting point of the ray. */
	private final Point head;

	/** The direction vector of the ray. */
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

	/**
	 * constructor for ray that gets a normal vector. It move the ray's origin a
	 * short distance in the normal's direction.
	 * 
	 * @param head      the original point
	 * @param direction the direction vector of the ray - <b><i>must be normalized</i></b>
	 * @param normal    the normal along which to move the origin point
	 */
	public Ray(Point head, Vector direction, Vector normal) {
		this.direction = direction;
		this.head = head.add(normal.scale(this.direction.dotProduct(normal) < 0 ? -DELTA : DELTA));
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
		try {
			return head.add(direction.scale(t));
		} catch (IllegalArgumentException e) {
			return head;
		}
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
	 * finds the closest GeoPoint to the ray's origin point and returns it
	 * 
	 * @param intersections list of GeoPoints to check
	 * @return the closest GeoPoint or null if the list is empty
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
		if (intersections == null)
			return null;

		GeoPoint closest = null;
		double minDistance = Double.POSITIVE_INFINITY;
		for (GeoPoint point : intersections) {
			double calcDistance = point.point.distanceSquared(head);
			if (calcDistance < minDistance) {
				closest = point;
				minDistance = calcDistance;
			}
		}
		
		return closest;
	}

}