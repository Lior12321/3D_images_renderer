package geometries;

import primitives.*;

/**
 * Represents a cylinder in 3D space. A cylinder is defined by its central axis,
 * radius, and height.
 */
public class Cylinder extends Tube {
	/**
	 * The height represents the distance between the two bases of the cylinder
	 * along the central axis.
	 */
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

	/**
	 * Returns the height of the cylinder.
	 *
	 * @return the height of the cylinder
	 */
	public double getHeight() {
		return height;
	}
}
