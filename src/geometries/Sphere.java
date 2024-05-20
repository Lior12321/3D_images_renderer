package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Sphere class represents a sphere in 3D space, defined by a center point
 * and a radius. It extends the RadialGeometry class, inheriting the radius property.
 * 
 * @author Lior &amp; Asaf
 */
public class Sphere extends RadialGeometry {
	private static Point center;

	/**
	 * Constructs a Sphere with the specified center point and radius.
	 *
	 * @param _center the center point of the sphere
	 * @param _radius the radius of the sphere
	 * @throws IllegalArgumentException if the radius is not a positive number
	 */
	public Sphere(Point _center, double _radius) {
		super(_radius);
		this.center = _center;
	}

	@Override
	public Vector getNormal(Point... p) {
		return null;
	}

	@Override
	public Vector getNormal(Point point) {
		return null;
	}
}