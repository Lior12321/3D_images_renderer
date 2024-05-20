package geometries;

import primitives.*;

public class Cylinder extends Tube {
	private final double height;

	/**
	 * constructor with parameters for Cylinder
	 * 
	 * @param axis the ray axis of the Cylinder
	 * @param radius the radius of the Cylinder
	 * @param height the height of the Cylinder
	 */
	public Cylinder(Ray axis, double radius, double height) {
		super(axis, radius);
		this.height = height;
	}
}