package geometries;

import primitives.*;
import primitives.Point;

/**
 * Represents a cylinder in 3D space. A cylinder is defined by its central axis,
 * radius, and height.
 */
public class Cylinder extends Tube {
	/**
	 * The height represents the distance between the two bases of the cylinder
	 * along the central axis.
	 */
	protected final double height;

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

	// @Override
	public Vector getNormal(Point p) {
		Point firstCenter = axis.getHead();
		// the second center is in front of the first one, and we just need to add the
		// cylinder height
		Point secondCenter = axis.getHead().add(axis.getDir().scale(height));
		Vector cylinderCenterVector = axis.getDir();

		// The normal at a base will be equal to central ray's
		// direction vector is v or opposite to it
		if (p.equals(firstCenter)) {
			return cylinderCenterVector.scale(-1);
		} else if (p.equals(secondCenter)) {
			return cylinderCenterVector;
		}

		// If the point on one of the cylinder bases, but it's not the center point
		double projection = cylinderCenterVector.dotProduct(p.subtract(firstCenter));
		if (projection == 0) {
			Vector v1 = p.subtract(firstCenter);
			return v1.normalize();
		}

		// If the point on the side of the cylinder.
		Point center = firstCenter.add(cylinderCenterVector.scale(projection));
		Vector v = p.subtract(center);
		return v.normalize();
	}
}