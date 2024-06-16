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

	/*
	 * @Override public List<Point> findIntersections(Ray ray) { // Find the
	 * intersection point with the plane containing the triangle (if there // is
	 * one) List<Point> result = plane.findIntersections(ray); // If there is no
	 * intersection with the plane, return null if (result == null) { return null; }
	 * 
	 * // Get vertices of the triangle Point p0 = vertices.get(0); Point p1 =
	 * vertices.get(1); Point p2 = vertices.get(2); Point p = result.get(0);
	 * 
	 * try { // Calculate vectors from p0 to p1, p0 to p2, and p0 to the
	 * intersection point p Vector n1 =
	 * p1.subtract(p0).crossProduct(p0.subtract(p)); Vector n2 =
	 * p2.subtract(p1).crossProduct(p1.subtract(p)); Vector n3 =
	 * p0.subtract(p2).crossProduct(p2.subtract(p));
	 * 
	 * // Check if the dot products of these vectors have consistent signs if
	 * ((n1.dotProduct(n2) > 0 && n2.dotProduct(n3) > 0 && n3.dotProduct(n1) > 0) ||
	 * (n1.dotProduct(n2) < 0 && n2.dotProduct(n3) < 0 && n3.dotProduct(n1) < 0)) {
	 * // If all dot products have the same sign, the intersection point is inside
	 * the // triangle, so return it return result; } } catch
	 * (IllegalArgumentException e) { return null; // Catch any illegal argument
	 * exceptions that might occur during vector // calculations } // If the
	 * intersection point is not inside the triangle, return null return null; }
	 */

	@Override
	public List<Point> findIntersections(Ray ray) {
		// Find the intersection point with the plane containing the triangle (if there
		// is one)
		List<Point> result = plane.findIntersections(ray);
		// If there is no intersection with the plane, return null
		if (result == null) {
			return null;
		}

		// Get vertices of the triangle
		final Point a = vertices.get(0);
		final Point b = vertices.get(1);
		final Point c = vertices.get(2);
		final Point p = result.get(0);
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

			// Calculate barycentric coordinates
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
}