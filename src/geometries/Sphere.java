package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The Sphere class represents a sphere in 3D space, defined by a center point
 * and a radius. It extends the RadialGeometry class, inheriting the radius
 * property.
 * 
 * @author Lior &amp; Asaf
 */
public class Sphere extends RadialGeometry {
	/**
	 * The center point of sphere
	 */
	private final Point center;

	/**
	 * Constructs a Sphere with the specified center point and radius.
	 *
	 * @param center the center point of the sphere
	 * @param radius the radius of the sphere
	 * @throws IllegalArgumentException if the radius is not a positive number
	 */
	public Sphere(Point center, double radius) {
		super(radius);
		this.center = center;
	}

	/**
	 * checks the normal of the sphere
	 * @param point the point we are calculating the normal from
	 * @return the normal
	 */
	@Override
	public Vector getNormal(Point point) {
		return (point.subtract(center)).normalize();
	}

	/**
	 * checks for intersections between a given ray and the sphere
	 * @param ray a given ray
	 * @return a list of the points of the intersections
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		// Check if the ray's origin intersects with the center of the sphere
		if (ray.geHead().equals(center)) {
			// If so, return the center plus the radius as the only intersection
			List<Point> intersections = new java.util.ArrayList<Point>();
			intersections.add(center.add(ray.getDir().scale(radius)));
			return intersections;
		}
		// Calculate the vector from the ray's head to the sphere's center
		Vector pointToCenter = center.subtract(ray.getHead());
		// Calculate the projection of this vector onto the ray's direction
		double tm = pointToCenter.dotProduct(ray.getDir());
		// Calculate the distance from the sphere's center to the ray
		double distanceFromCenter = sqrt(pointToCenter.dotProduct(pointToCenter) - tm * tm);

		// If the distance from the center is greater than the radius, there are no intersections
		if (distanceFromCenter >= radius) {
			return null;
		}

		// Calculate the distance along the ray to the intersection points
		double th = sqrt(radius * radius - distanceFromCenter * distanceFromCenter);
		double firstDistance = tm - th;
		double secondDistance = tm + th;

		// If either intersection point is in front of the ray's origin, return them
		if (firstDistance > 0 || secondDistance > 0) {
			List<Point> intersections = new java.util.ArrayList<Point>();
			if (Util.alignZero(firstDistance) > 0) {
				Point firstIntersection = ray.getPoint(firstDistance);
				intersections.add(firstIntersection);
			}
			if (Util.alignZero(secondDistance) > 0) {
				Point secondIntersection = ray.getPoint(secondDistance);
				intersections.add(secondIntersection);
			}
			else {
				return null;
			}
			return intersections;
		}
		// If no intersection points are found, return null
		return null;
	}
}