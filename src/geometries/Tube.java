package geometries;

import primitives.Vector;
import primitives.Point;
import primitives.Ray;

/**
 * The Tube class represents a tube in 3D space, defined by a central axis and a radius.
 * 
 * @author Lior &amp; Asaf
 */
public class Tube extends RadialGeometry {
	protected Ray axis;

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
	 * Returns the normal vector to the tube at the specified points.
	 * 
	 * @param p one or more points on the tube where the normal is to be calculated
	 * @return the normal vector at the specified points
	 */
	@Override
	public Vector getNormal(Point... p) {
		return null; // will return NULL for now
	}
}
