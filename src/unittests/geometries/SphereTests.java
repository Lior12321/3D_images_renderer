package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import primitives.Point;
import primitives.Vector;

/**
 * Unit test for geometries.Sphere class
 * 
 * @author Lior &amp; Asaf
 */
public class SphereTests {
	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test that the normal is the correct one
		Sphere s1 = new Sphere(new Point(0, 0, 0), 2);
		assertEquals(new Vector(1, 0, 0), s1.getNormal(new Point(2, 0, 0)), //
				"ERROR: getNormal() for Sphere wrong result");
	}
}