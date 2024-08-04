package geometries;

import primitives.Vector;

import static primitives.Util.alignZero;

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
		// Getting the direction of the ray
		Vector v = ray.getDir();
		// Getting the direction of the axis
		Vector v1 = axis.getDir();
		// The difference between the head of the ray and the head of the axis
		Vector deltaP = ray.getHead().subtract(axis.getHead());
		// Calculating the dot product of the direction of the ray and the direction of
		// the axis
		double vv1 = v.dotProduct(v1);
		// Calculating the dot product of the difference between the head of the ray and
		// the head of the axis and the direction of the axis
		double deltaPv1 = deltaP.dotProduct(v1);

		// Calculating the coefficients of the quadratic equation
		double a = v.subtract(v1.scale(vv1)).lengthSquared();
		double b = deltaP.subtract(v1.scale(deltaPv1)).dotProduct(v.subtract(v1.scale(vv1))) * 2;
		double c = deltaP.subtract(v1.scale(deltaPv1)).lengthSquared() - radius * radius;
		double delta = alignZero(b * b - 4 * a * c);
		// If the delta is negative, there are no intersections
		if (delta < 0)
			return null;
		// Calculating the intersection points
		double t1 = alignZero((-b + Math.sqrt(delta)) / (2 * a));
		double t2 = alignZero((-b - Math.sqrt(delta)) / (2 * a));
		// If both intersection points are negative, there are no intersections
		if (t1 <= 0 && t2 <= 0)
			return null;
		// If both intersection points are positive, return both intersection points
		if (t1 > 0 && t2 > 0)
			return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
		// If only one of the intersection points is positive, return the positive one
		return t1 > 0 ? List.of(new GeoPoint(this, ray.getPoint(t1))) : List.of(new GeoPoint(this, ray.getPoint(t2)));
	}
}