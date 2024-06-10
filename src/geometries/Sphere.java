package geometries;

import java.util.*;
import primitives.Point;
import primitives.Ray;
import static primitives.Util.*;
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
	 * 
	 * @param point the point we are calculating the normal from
	 * @return the normal
	 */
	@Override
	public Vector getNormal(Point point) {
		return (point.subtract(center)).normalize();
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// Check if the ray's origin intersects with the center of the sphere
		if (ray.getHead().equals(center)) {
			// If so, return the center plus the radius as the only intersection
			return List.of(ray.getPoint(this.radius));
		}
		// Calculate the vector from the ray's head to the sphere's center
		Vector pointToCenter = center.subtract(ray.getHead());
		// Calculate the projection of this vector onto the ray's direction
		double tm = pointToCenter.dotProduct(ray.getDir());
		// Calculate the distance from the sphere's center to the ray
		double distanceFromCenter = Math.sqrt(pointToCenter.dotProduct(pointToCenter) - (tm * tm));

		// If the distance from the center is greater than the radius, there are no
		// intersections
		if (distanceFromCenter == radius) {
			return null;
		}

		// Calculate the distance along the ray to the intersection points
		double th = Math.sqrt((radius * radius) - (distanceFromCenter * distanceFromCenter));
		double firstDistance = tm - th;
		double secondDistance = tm + th;

		// If either intersection point is in front of the ray's origin, return them
		if (alignZero(firstDistance) > 0 && alignZero(secondDistance) > 0) {
			return List.of(ray.getPoint(firstDistance), ray.getPoint(secondDistance));
		}
		if (alignZero(firstDistance) > 0) {
			return List.of(ray.getPoint(firstDistance));
		}
		if (alignZero(secondDistance) > 0) {
			return List.of(ray.getPoint(secondDistance));
		}

		// If no intersection points are found, return null
		return null;
	}
}