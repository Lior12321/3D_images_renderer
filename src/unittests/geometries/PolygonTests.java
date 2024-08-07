package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Polygons
 * 
 * @author Dan
 */
public class PolygonTests {
	/**
	 * Delta value for accuracy when comparing the numbers of type 'double' in
	 * assertEquals
	 */
	private final double DELTA = 0.000001;

	/** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		assertDoesNotThrow(
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1)),
				"Failed constructing a correct polygon");

		// TC02: Wrong vertices order
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
				"Constructed a polygon with wrong order of vertices");

		// TC03: Not in the same plane
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
				"Constructed a polygon with vertices that are not in the same plane");

		// TC04: Concave quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
						new Point(0.5, 0.25, 0.5)), //
				"Constructed a concave polygon");

		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
				"Constructed a polygon with vertix on a side");

		// TC11: Last point = first point
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
				"Constructed a polygon with vertice on a side");

		// TC12: Co-located points
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
				"Constructed a polygon with vertice on a side");

	}

	/** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here - using a quad
		Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
		Polygon pol = new Polygon(pts);
		// ensure there are no exceptions
		assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
		// generate the test result
		Vector result = pol.getNormal(new Point(0, 0, 1));
		// ensure |result| = 1
		assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
		// ensure the result is orthogonal to all the edges
		for (int i = 0; i < 3; ++i)
			assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
					"Polygon's normal is not orthogonal to one of the edges");
	}

	/**
	 * tests
	 * {@link geometries.Polygon#findGeoIntersectionsHelper(primitives.Ray, double)}
	 */
	@Test
	void testFindGeoIntersectionsDistance() {
		Polygon rectangle = new Polygon(new Point(1, 0, 0), new Point(-2, 0, -2), new Point(0, 0, 2),
				new Point(2, 0, 2));
		Ray ray = new Ray(new Point(0, 2, 0), new Vector(0, -1, 0));
		// ================= Equivalence Partitions Tests ===========================
		// TC01: the rectangle is not too far
		assertEquals(1, rectangle.findGeoIntersections(ray, 10).size());

		// TC02: the rectangle is too far
		assertNull(rectangle.findGeoIntersections(ray, 1));

		// ================= BVA Tests ===========================
		// TC03: the intersection is exactly at the max distance (0 points)
		assertNull(rectangle.findGeoIntersections(ray, 2));

	}
}
