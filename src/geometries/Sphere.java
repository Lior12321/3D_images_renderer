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

	@Override
	public Vector getNormal(Point point) {
		return (point.subtract(center)).normalize();
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// Check if the ray's origin intersects with the center of the sphere
		if (ray.getHead().equals(center))
			// If so, return the center plus the radius as the only intersection
			return List.of(ray.getPoint(this.radius));

		// Calculate the vector from the ray's head to the sphere's center
		Vector pointToCenter = center.subtract(ray.getHead());
		// Calculate the projection of this vector onto the ray's direction
		double projection = pointToCenter.dotProduct(ray.getDir());
		// Calculate the distance from the sphere's center to the ray
		double squaredDistanceFromCenter = pointToCenter.lengthSquared() - projection * projection;
		double squaredDistance = squaredRadius - squaredDistanceFromCenter;

		// If the distance from the center is greater than the radius, there are no
		// intersections
		if (alignZero(squaredDistance) <= 0)
			return null;

		// Calculate the distance along the ray to the intersection points
		double distance = Math.sqrt(squaredDistance); // always positive => firstD < sedondD
		double secondDistance = projection + distance;
		if (alignZero(secondDistance) <= 0)
			return null;

		double firstDistance = projection - distance;
		return alignZero(firstDistance) <= 0 //
				? List.of(ray.getPoint(secondDistance)) //
				: List.of(ray.getPoint(firstDistance), ray.getPoint(secondDistance));

	}
}