package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.List;

/**
 * Unit test for geometries.Triangle class
 *
 * @author Lior &amp; Asaf
 */
public class TriangleTests {
	/**
	 * Delta value for accuracy when comparing the numbers of type 'double' in
	 * assertEquals
	 */
	private final double DELTA = 0.000001;

	/**
	 * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that the normal is the right one
		Triangle t = new Triangle(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0));
		Vector result = t.getNormal(new Point(0, 0, 1));
		// test the length is 1
		assertEquals(1, result.length(), DELTA, "ERROR: the length of the normal is not 1");
		// check the normal in orthogonal to all the edges
		assertTrue(isZero(t.getNormal(new Point(0, 0, 1)).dotProduct(new Vector(0, 1, -1))),
				"ERROR: the normal is not orthogonal to the 1st edge");
		assertTrue(isZero(t.getNormal(new Point(0, 0, 1)).dotProduct(new Vector(1, -1, 0))),
				"ERROR: the normal is not orthogonal to the 2nd edge");
		assertTrue(isZero(t.getNormal(new Point(0, 0, 1)).dotProduct(new Vector(-1, 0, 1))),
				"ERROR: the normal is not orthogonal to the 3rd edge");
	}

	/**
	 * Test method for
	 * {@link geometries.Triangle#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Triangle t = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
		Point p111 = new Point(1, 1, 1);
		Vector v111 = new Vector(1, 1, 1);
		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray intersects the triangle
		final var result = t.findIntersections(new Ray(p111, new Vector(-1, -1, -0.5)));
		assertEquals(1, result.size(), "ERROR: Wrong number of points");
		assertEquals(List.of(new Point(0.2, 0.2, 0.6)), result, "ERROR: Wrong intersection points");

		// TC02: Ray outside against edge
		assertNull(t.findIntersections(new Ray(new Point(1.1, -0.1, 0), v111)),
				"ERROR: Wrong intersection points (didn't return null)");

		// TC03: Ray outside against vertex
		assertNull(t.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(1, -0.5, -1))),
				"ERROR: Wrong intersection points (didn't return null)");

		// =============== Boundary Values Tests ==================
		// TC10: Ray on edge
		assertNull(t.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, -0.1, -0.4))),
				"ERROR: findIntersections() did not return null");

		// TC11: Ray on vertex
		assertNull(t.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, 0.5, -1))),
				"ERROR: findIntersections() did not return null");

		// TC12: Ray on edge continue
		assertNull(t.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, -1, 0.5))),
				"ERROR: findIntersections() did not return null");
	}
}