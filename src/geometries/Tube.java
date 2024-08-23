package geometries;

import primitives.Vector;

import java.util.List;
import primitives.Point;
import primitives.Ray;

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
		// Finding the deference between the closest point to the given point that is on
		// the
		// axis ray
		double t = axis.getDir().dotProduct(p.subtract(axis.getHead()));
		// Returning the subtraction of one from the other
		return p.subtract(axis.getPoint(t)).normalize();
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		return null;
	}
}