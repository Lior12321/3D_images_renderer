package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.Point;
import primitives.Vector;
import static primitives.Util.*;


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

}
