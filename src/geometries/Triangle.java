package geometries;

import java.util.List;

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
		List<Point> result = this.plane.findIntersections(ray);
		// If there is no intersection with the plane, return null
		if (result == null) {
			return null;
		}

		// Get vertices of the triangle
		Point p0 = this.vertices.get(0);
		Point p1 = this.vertices.get(1);
		Point p2 = this.vertices.get(2);
		Point p = result.get(0);

		try {
			// Calculate vectors from p0 to p1, p0 to p2, and p0 to the intersection point p
			Vector n1 = p1.subtract(p0).crossProduct(p0.subtract(p));
			Vector n2 = p2.subtract(p1).crossProduct(p1.subtract(p));
			Vector n3 = p0.subtract(p2).crossProduct(p2.subtract(p));

			// Check if the dot products of these vectors have consistent signs
			if ((n1.dotProduct(n2) > 0 && n2.dotProduct(n3) > 0 && n3.dotProduct(n1) > 0)
					|| (n1.dotProduct(n2) < 0 && n2.dotProduct(n3) < 0 && n3.dotProduct(n1) < 0)) {
				// If all dot products have the same sign, the intersection point is inside the
				// triangle, so return it
				return result;
			}
		} catch (IllegalArgumentException e) {
			// Catch any illegal argument exceptions that might occur during vector
			// calculations
		}
		// If the intersection point is not inside the triangle, return null
		return null;
	}
}
