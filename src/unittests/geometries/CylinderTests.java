package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.Cylinder;

/**
 * Unit test for geometries.Cylinder class
 * 
 * @author Lior &amp; Asaf
 */
public class CylinderTests {

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Cylinder c = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1, 5);
		Vector v010 = new Vector(0, 1, 0);
		// ============ Equivalence Partitions Tests ==============//
		// TC01: check the first base
		assertEquals(v010, c.getNormal(new Point(0, 0.5, 0)),
				"ERROR: The normal to the cylinder first base isn't calculated correctly");

		// TC02: check the second base
		assertEquals(v010, c.getNormal(new Point(0, 0.5, 5)),
				"ERROR: The normal to the cylinder second base isn't calculated correctly");

		// TC03: check the side of the cylinder
		assertEquals(v010, c.getNormal(new Point(0, 1, 5)),
				"ERROR: The normal to the cylinder side isn't calculated correctly");

		// =============== Boundary Values Tests ==================
		// TC10: check first center base normal (if p = o)
		assertEquals(c.getAxis().getDir().scale(-1), c.getNormal(new Point(0, 0, 0)),
				"ERROR: The normal to the cylinder first base center isn't calculated correctly");

		// TC11: check second center base normal (if p = o)
		assertEquals(c.getAxis().getDir(), c.getNormal(new Point(0, 0, 5)),
				"ERROR: The normal to the cylinder second base center isn't calculated correctly");
	}

}
