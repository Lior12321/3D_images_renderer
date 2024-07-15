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
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		Point head = ray.getHead();
		Vector dir = ray.getDir();

		// check if the ray starts from the center of the sphere
		if (head.equals(center))
			return List.of(new GeoPoint(this, ray.getPoint(radius)));

		// Identify the hypotenuse, base, and perpendicular of the triangle formed by
		// the ray's
		// starting point, the sphere's center, and the intersection point of the ray
		// with the perpendicular line that pass through the sphere's center.
		Vector hypotenuse = center.subtract(head);
		double base = dir.dotProduct(hypotenuse);
		double perpendicular = hypotenuse.lengthSquared() - base * base;

		// check if the ray is height to the sphere at the
		// intersection point, or go outside the sphere.
		if (isZero(perpendicular - squaredRadius) || perpendicular > squaredRadius)
			return null;

		// Return intersection points, ensuring that only those intersected by the
		// ray are returned.
		double inside = Math.sqrt(squaredRadius - perpendicular);
		double t2 = base + inside;
		if (alignZero(t2) <= 0)
			return null;

		double t1 = base - inside;
		return alignZero(t1) > 0 ? List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)))
				: List.of(new GeoPoint(this, ray.getPoint(t2)));
	}
}