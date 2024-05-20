package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Sphere class represents a sphere in 3D space, defined by a center point
 * and a radius. It extends the RadialGeometry class, inheriting the radius
 * property.
 * 
 * @author Lior &amp; Asaf
 */
public class Sphere extends RadialGeometry {
	private static Point center; // the central point of the sphere

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
	 * get the normal of a specific point of the sphere
	 * @param point the point we want it's normal
	 * @return the normal of the point
	 */
	@Override
	public Vector getNormal(Point point) {
		return null;
	}
}