package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Tube;

/**
 * Unit test for geometries.Tube class
 * 
 * @author Lior &amp; Asaf
 */
public class TubeTests {
	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that the normal is the correct one
		Tube t1 = new Tube(new Ray(new Point(1, 1, 1), new Vector(0, 1, 0)), 1);
		assertEquals(new Vector(0, 0, 1), t1.getNormal(new Point(1, 3, 2)), //
				"ERROR: getNormal() for Tube wrong result");

		// =============== Boundary Values Tests ==================
		// TC11: getNormal works for a normal that is vertical to the axis ray
		assertEquals(t1.getAxis().getDir(), t1.getNormal(new Point(1, 1, 2)), //
				"getNormal() didn't work properly for normal that is perpendicular to the axis ray");
	}
}