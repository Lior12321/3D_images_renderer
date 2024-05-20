package geometries;

import primitives.Vector;
import primitives.Point;
import primitives.Ray;

/**
 * The Tube class represents a tube in 3D space, defined by a central axis and a
 * radius.
 * 
 * @author Lior &amp; Asaf
 */
public class Tube extends RadialGeometry {
	protected Ray axis; // the ray axis of the Tube

	/**
	 * Constructs a Tube with the specified axis and radius.
	 *
	 * @param ray    the central axis of the tube.
	 * @param radius the radius of the tube.
	 * @throws IllegalArgumentException if the radius is not a positive number.
	 */
	public Tube(Ray ray, double radius) {
		super(radius);
		this.axis = ray;
	}

	/**
	 * get the normal of a specific point of the Tube
	 * @param point the point we want it's normal
	 * @return the normal of the point
	 */
	@Override
	public Vector getNormal(Point point) {
		return null;
	}
}
