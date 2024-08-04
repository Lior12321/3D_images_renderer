package geometries;

import java.util.List;
import static primitives.Util.*;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a triangle in 3D space. A triangle is defined by three vertices
 * 
 * @author Lior &amp; Asaf
 */
public class Triangle extends Polygon {

	/**
	 * Constructs a new Triangle with the specified vertices. the constructor send
	 * all the arguments to polygon.
	 *
	 * @param p1 the first vertex of the triangle
	 * @param p2 the second vertex of the triangle
	 * @param p3 the third vertex of the triangle
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// Find the intersection point with the plane containing the triangle (if there
		// is one)
		List<Point> result = plane.findIntersections(ray);
		// If there is no intersection with the plane, return null
		if (result == null)
			return null;

		// Get vertices of the triangle
		final Point a = vertices.getFirst();
		final Point b = vertices.get(1);
		final Point c = vertices.getLast();
		final Point p = result.getFirst();
		// Vector normal = plane.getNormal();

		final Vector ab, bc, ac, aq, bq, cq;
		final double area, alpha, beta, gamma;

		try {
			ab = b.subtract(a); // a->b
			bc = c.subtract(b); // b->c
			ac = c.subtract(a); // a->c
			aq = p.subtract(a); // a->p
			bq = p.subtract(b); // b->p
			cq = p.subtract(c); // c->p
			// Alpha, beta, and gamma are calculated by the ratio between the respective
			// triangles and the entire one.

			// Calculate area of the triangle
			area = ab.crossProduct(ac).length();

			// Calculate baricentric coordinates
			alpha = ab.crossProduct(aq).length() / area;
			beta = bc.crossProduct(bq).length() / area;
			gamma = ac.crossProduct(cq).length() / area;

			if (alignZero(alpha) > 0 && alignZero(beta) > 0 && alignZero(gamma) > 0 && isZero(alpha + beta + gamma - 1))
				return List.of(p);

		} catch (IllegalArgumentException e) {
			// Catch illegal argument exceptions that might occur during the calculations
			return null;
		}

		// If the intersection point is not inside the triangle, return null
		return null;
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		var intersections = plane.findGeoIntersectionsHelper(ray, maxDistance);
		if (intersections == null)
			return null;
		intersections = List.of(new GeoPoint(this, intersections.get(0).point));
		// The intersection point is inside the triangle
		Point rayP0 = ray.getHead();
		Vector rayDir = ray.getDir();
		Vector v1 = vertices.get(0).subtract(rayP0);
		Vector v2 = vertices.get(1).subtract(rayP0);
		double t1 = alignZero(rayDir.dotProduct(v1.crossProduct(v2)));
		// check if the ray is parallel to the triangle and there is no intersection
		if (t1 == 0)
			return null;

		Vector v3 = vertices.get(2).subtract(rayP0);
		double t2 = alignZero(rayDir.dotProduct(v2.crossProduct(v3)));
		// if t1 and t2 signs are different, the intersection is not inside the triangle
		if (t1 * t2 <= 0)
			return null;

		double t3 = alignZero(rayDir.dotProduct(v3.crossProduct(v1)));
		// if t1 and t2 signs are different, the intersection is not inside the triangle
		return t1 * t3 <= 0 ? null : intersections;
	}
}