package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.Plane;
import primitives.Point;
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
				() -> new Plane(new Point(1, 2, 3), new Point(1, 2, 3), new Point(1, 2, 4)),
				"ERROR: does not throw exception for two points are the same");
		// TC12: Test three points are on the same line
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(3, 6, 9)),
				"ERROR: does not throw exception for three points are on the same line");
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that the normal is the right one
		Plane p = new Plane(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0));
		Vector normal = p.getNormal(new Point(0, 0, 1));
		// test the length is 1
		assertEquals(1, normal.length(), DELTA, "ERROR: the length of the normal is not 1");
		// check the normal in orthogonal to the plane
	    assertEquals(0, normal.dotProduct(new Vector(0, 1, -1)), DELTA, "ERROR: the normal isn't orthogonal to the vector (0,1,-1) on the plane");
        assertEquals(0, normal.dotProduct(new Vector(1, 0, -1)), DELTA, "ERROR: the normal isn't orthogonal to the vector (1,0,-1) on the plane");
	}
}
