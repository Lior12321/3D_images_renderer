package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

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

}
