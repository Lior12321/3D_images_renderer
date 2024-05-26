package unittests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

/**
 * Unit test for primitives.Point class
 * 
 * @author Lior &amp; Asaf
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Add two positive points
		Point p1 = new Point(1, 2, 3);
		Vector v1 = new Vector(1, 2, 3);
		assertEquals(new Point(2, 4, 6), p1.add(v1), "add() wrong result");

		// TC02: Add positive and negative points
		p1 = new Point(1, 2, 3);
		v1 = new Vector(-2, -4, -6);
		assertEquals(new Point(-1, -2, -3), p1.add(v1), "add() wrong result");
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Subtract two positive points
		Point p1 = new Point(1, 2, 3);
		Vector v1 = new Vector(2, 4, 6);
		assertEquals(new Vector(-1, -2, -3), p1.subtract(v1), "subtract() wrong result");

		// TC02: Subtract negative from positive
		p1 = new Point(1, 2, 3);
		v1 = new Vector(-2, -4, -6);
		assertEquals(new Vector(3, 6, 9), p1.subtract(v1), "subtract() wrong result");

		// =============== Boundary Values Tests ==================
		// TC11: Test that exception is thrown for zero vector
		Point p3 = new Vector(1, 2, 3);
		assertThrows(IllegalArgumentException.class, () -> p3.subtract(p3), //
				"subtract() does not throw an exception for subtracting point from itself");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that the distance between two points is right
		// (ignore from difference that <= 0.00001)
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(2, 4, 6);
		assertEquals(p1.distanceSquared(p2), 14, 0.00001, "distanceSquared() wrong result");

		// =============== Boundary Values Tests ==================
		// TC11: distance Squared from the point to itself
		assertEquals(p1.distanceSquared(p1), 0, "distanceSquared() does not work for distance from point to itself");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that the new point is the right one
		// (ignore from difference that <= 0.00001)
		Point p1 = new Point(1, 2, 3);
		Point p2 = new Point(1, 5, 7);
		assertEquals(p1.distance(p2), 5, 0.00001, "distanceSquared() wrong result");

		// =============== Boundary Values Tests ==================
		// TC11: Tests that distanceSquared works for the distance of a point from
		// itself
		assertEquals(p1.distanceSquared(p1), "distanceSquared() does not work for distance from point to itself");
	}
}
