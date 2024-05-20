package geometries;

import primitives.*;

public class Cylinder extends Tube {
	private final double height;

	/**
	 * Constructs a new Cylinder with the specified axis, radius, and height.
	 *
	 * @param axis   the central axis of the cylinder, represented as a Ray
	 * @param radius the radius of the cylinder
	 * @param height the height of the cylinder
	 */
	public Cylinder(Ray axis, double radius, double height) {
		super(axis, radius);
		this.height = height;
	}
}