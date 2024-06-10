package geometries;

import static primitives.Util.*;

/**
 * The RadialGeometry class represents a radial geometric shape characterized by
 * a radius. It is an abstract class that serves as a base for all radial
 * geometries.
 * 
 * @author Lior &amp; Asaf
 */
public abstract class RadialGeometry implements Geometry {
	/**
	 * The radius of the radial shape
	 */
	protected final double radius;
	/**
	 * The squared radius of the radial shape
	 */
	protected final double squaredRadius;

	/**
	 * Constructs a RadialGeometry with the specified radius.
	 *
	 * @param radius the radius of the radial geometry. It must be a positive
	 *               number.
	 * @throws IllegalArgumentException if the radius is not a positive number.
	 */
	public RadialGeometry(double radius) {
		if (alignZero(radius) <= 0)
			throw new IllegalArgumentException("The radius must be positive number!");
		this.radius = radius;
		squaredRadius = radius * radius;
	}
}
