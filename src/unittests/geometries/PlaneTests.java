package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit test for geometries.Plane class
 * 
 * @author Lior &amp; Asaf
 */

public class PlaneTests {
	/**
	 * Delta value for accuracy when comparing the numbers of type 'double' in
	 * assertEquals
	 */
	private final double DELTA = 0.000001;

	/**
	 * Test method for
	 * {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
	 */
	@Test
	public void testPlane() {
		// ============ Boundary Values Tests ==================
		// TC11: Test two points are the same
		assertThrows(IllegalArgumentException.class, //
				() -> new Plane(new Point(1, 2, 3), new Point(1, 2, 3), new Point(1, 2, 4)), //
				"ERROR: the first and second points coalescing");
		// TC12: Test three points are on the same line
		assertThrows(IllegalArgumentException.class, //
				() -> new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(3, 6, 9)), //
				"ERROR: all three points are on the same line");
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that the normal is the right one
		Plane p = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
		Vector normal = p.getNormal(new Point(0, 0, 1));
		// test the length is 1
		assertEquals(1, normal.length(), DELTA, "ERROR: the length of the normal is not 1");
		// check the normal in orthogonal to the plane
		assertEquals(0, normal.dotProduct(new Vector(0, 1, -1)), DELTA,
				"ERROR: the normal isn't orthogonal to the vector (0,1,-1) on the plane");
		assertEquals(0, normal.dotProduct(new Vector(1, 0, -1)), DELTA,
				"ERROR: the normal isn't orthogonal to the vector (1,0,-1) on the plane");
	}

	/**
	 * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Plane p = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
		Point inPlane = new Point(0.3, 0.3, 0.4);
		Vector v111 = new Vector(1, 1, 1);
		Vector v523 = new Vector(-5, 2, 3);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray crosses the plane (ray isn't parallel to the plane)
		var result = p.findIntersections(new Ray(new Point(0.5, 0.5, 1), new Vector(-0.5, -1, -1)));
		assertEquals(1, result.size(), "ERROR: findIntersections() did not return the right number of points");
		assertEquals(List.of(new Point(0.3, 0.1, 0.6)), result, "Incorrect intersection points");

		// TC02: Ray isn't crosses the plane (parallel)
		assertNull(p.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 2, -2))), //
				"ERROR: There shouldn't be any intersections");

		// =============== Boundary Values Tests ==================
		// **** Group: the ray parallel to the plane
		// TC10: Ray included in the plane
		assertNull(p.findIntersections(new Ray(inPlane, v523)), //
				"ERROR: There shouldn't be any intersections. The ray included in the plane");

		// TC11: ray is parallel to the plane and not included in the plane
		assertNull(p.findIntersections(new Ray(new Point(1, 1, 0), v523)),
				"ERROR: Wrong number of points (ray parallel to the plane and not included in the plane)");

		// **** Group: the ray is orthogonal to the plane
		// TC12: Ray orthogonal to the plane and starts before the plane
		result = p.findIntersections(new Ray(new Point(0, 0, 2), v111));
		assertEquals(1, result.size(), //
				"ERROR: Wrong number of points (ray orthogonal to the plane and starts before the plane)");

		// TC13: Ray is orthogonal to the plane and begins in the plane
		assertNull(p.findIntersections(new Ray(inPlane, v111)), //
				"ERROR: Wrong number of points (ray orthogonal to the plane and begins in the plane)");

		// TC14: Ray is orthogonal to the plane and begins after the plane
		assertNull(p.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-1, -1, -1))), //
				"ERROR: Wrong number of points (ray orthogonal to the plane and begins after the plane)");

		// **** Group: the ray cross the plane (not orthogonal/ parallel)
		// TC15: Ray begins in the plane
		assertNull(p.findIntersections(new Ray(inPlane, new Vector(2, 1, 1))), //
				"ERROR: Wrong number of points (ray cross the plane and begins in the plane)");

		// TC16: Ray starts in the same point as the point that is the base of the plane
		assertNull(p.findIntersections(new Ray(p.getBase(), new Vector(2, 1, 1))), //
				"ERROR: Wrong number of points (ray starts in the same point as the point that is the base of the plane)");
	}
}
