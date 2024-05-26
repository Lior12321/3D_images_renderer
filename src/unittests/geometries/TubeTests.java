/**
 * 
 */
package unittests.geometries;

/**
 * Unit test for geometries.Tube class
 * 
 * @author Lior &amp; Asaf
 */
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 */
class TubeTests {
	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() {
		Tube t1 = new Tube(new Ray(new Point(1, 1, 1), new Vector(0, 1, 0)), 1);

		// ============ Equivalence Partitions Tests ==============
		Vector e1 = t1.getNormal(new Point(1, 3, 2));
		// TC01: Test that the normal is the correct one
		assertEquals(new Vector(0, 0, 1), e1, "getNormal() wrong result");

		// =============== Boundary Values Tests ==================
		Vector b1 = t1.getNormal(new Point(1, 1, 2));
		// TC11: Test that getNormal works for a normal that is perpendicular to the axis ray
		assertEquals(new Vector(0, 0, 1), b1,
				"getNormal() didn't work properly for normal that is perpendicular to the axis ray");
	}
}