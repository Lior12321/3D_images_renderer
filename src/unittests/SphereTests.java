/**
 * 
 */
package unittests;

/**
 * Unit test for geometries.Sphere class
 * 
 * @author Lior &amp; Asaf
 */
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 */
class SphereTests {
	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() {
		Sphere s1 = new Sphere(new Point(1, 2, 3), 1);
		// ============ Equivalence Partitions Tests ==============
		Vector v1 = s1.getNormal(new Point(2, 2, 3));
		// TC01: Test that the normal is the correct one
		assertEquals(new Vector(1, 0, 0), v1, "getNormal() wrong result");
	}
}
