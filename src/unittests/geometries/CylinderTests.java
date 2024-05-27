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
		Cylinder cylinder = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 1, 5);

		// ============ Equivalence Partitions Tests ==============
		// TC01: check the first base of the cylinder
		assertEquals(new Vector(0, 1, 0), cylinder.getNormal(new Point(0, 0.5, 0)), //
				"ERROR: GetNormal() dose not calculate the first base cylinder normal corectly");

		// TC02: check the second base of the cylinder
		assertEquals(new Vector(0, 1, 0), cylinder.getNormal(new Point(0, 0.5, 5)), //
				"ERROR: GetNormal() dose not calculate the second base cylinder normal corectly");
		
		// TC03: check the cylinder side
		assertEquals(new Vector(0, 1, 0), cylinder.getNormal(new Point(0, 1, 2)), //
				"ERROR: GetNormal() dose not calculate the cylinder side normal corectly");
	}

}
