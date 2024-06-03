package geometries;

import primitives.Vector;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import static primitives.Util.*;

/**
 * The Tube class represents a tube in 3D space, defined by a central axis and a
 * radius.
 * 
 * @author Lior &amp; Asaf
 */
public class Tube extends RadialGeometry {
	/**
	 * The central axis of the tube. This axis is represented as a Ray, defining the
	 * direction and position of the tube in 3D space.
	 */
	protected final Ray axis;

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

	// getters:
	/**
	 * Returns the central axis of the tube.
	 *
	 * @return The central axis of the tube.
	 */
	public Ray getAxis() {
		return axis;
	}

	@Override
	public Vector getNormal(Point p) {
		// Project the point onto the axis
		Point p0 = axis.getHead();
		Vector centerVec = axis.getDir();

		// Calculate the projection of the point onto the axis
		double projection = p.subtract(p0).dotProduct(centerVec);
		// Find the closest point on the centerVec to the given point
		Point centerP = isZero(projection) ? p0 : p0.add(centerVec.scale(projection));

		// Calculate the normal vector
		return p.subtract(centerP).normalize();
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}