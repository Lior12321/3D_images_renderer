package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.logging.ErrorManager;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit test for primitives.Ray class
 * 
 * @author Lior &amp; Asaf
 */
class RayTests {

	/**
	 * Test method for {@link primitives.Ray#getPoint(double)}.
	 */
	@Test
	void testGetPoint() {
		Point p1 = new Point(1, 2, 3);
		Ray r = new Ray(p1, new Vector(1, 0, 0));

		// ============ Equivalence Partitions Tests ==============

		// TC01: Test that the new point is the right one (t > 0)
		assertEquals(new Point(3, 2, 3), r.getPoint(2), "getPoint() wrong result for positive t");

		// TC02: Test that the new point is the right one (t < 0)
		assertEquals(new Point(-1, 2, 3), r.getPoint(-2), "getPoint() wrong result for negative t");

		// ============ Boundary Values Tests ==============

		// TC10: Test that the new point is the head (t = 0)
		assertEquals(p1, r.getPoint(0), "getPoint() wrong result for t=0");
	}

	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(List<Point>)}.
	 */
	@Test
	void testFindClosestPoint() {
		Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 1));
		Point p222 = new Point(2, 2, 2);
		Point p333 = new Point(3, 3, 3);
		Point p444 = new Point(4, 4, 4);
		List<Point> points = List.of(p333, p222, p444);
		String errorMassage = "ERROR: Returned wrong point";
		// ============ Equivalence Partitions Tests ==============
		// TC01: Point in the middle of the list is the closest to the ray start
		assertEquals(p222, ray.findClosestPoint(points), errorMassage);

		// ============ Boundary Values Tests ==============
		// TC10: list is empty - should return NULL
		points = List.of();
		assertNull(ray.findClosestPoint(points), "should return NULL");

		// TC11: first point is the closest point to the ray start
		points = List.of(p222, p333, p444);
		assertEquals(p222, ray.findClosestPoint(points), errorMassage);

		// TC12: last point is the closest point to the ray start
		points = List.of(p444, p333, p222);
		assertEquals(p222, ray.findClosestPoint(points), errorMassage);
	}

}
