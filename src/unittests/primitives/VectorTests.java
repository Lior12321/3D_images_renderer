package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Vector;

/**
 * Unit test for primitives.Vector class
 * 
 * @author Lior &amp; Asaf
 */
class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test length squared for vector (ignore from difference that <= 0.00001)
		assertEquals((new Vector(1, 2, 3)).lengthSquared(), 14, 0.00001,
				"ERROR: lengthSquared() does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test length for vector (ignore from difference that <= 0.00001)
		assertEquals((new Vector(0, 3, 4)).length(), 5, 0.00001, "ERROR: length() does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Add two positive vectors
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(2, 4, 6);
		assertEquals(v1.add(v2), new Vector(3, 6, 9), "ERROR: Add 2 vectors does not work correctly");

		// TC02: Add positive and negative vectors
		v1 = new Vector(1, 2, 3);
		v2 = new Vector(-2, -4, -6);
		assertEquals(v1.add(v2), new Vector(-1, -2, -3), "ERROR: Add 2 vectors does not work correctly");

		// =============== Boundary Values Tests ==================
		// TC11: Test opposite direction vector throws exception
		Vector v3 = new Vector(1, 2, 3);
		assertThrows(IllegalArgumentException.class, () -> v3.add(new Vector(-1, -2, -3)),
				"ERROR: does not throw exception for adding two vectors with opposite direction");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: multiply vector by positive number
		Vector v1 = new Vector(1, 3, 5);
		assertEquals(v1.scale(2), new Vector(2, 6, 10), "ERROR: scale() does not work correctly");

		// =============== Boundary Values Tests ==================
		// TC11: Test scaling by zero throws exception
		assertThrows(IllegalArgumentException.class, () -> v1.scale(0),//
				"ERROR: scale() does not throw exception for scaling by zero");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test vectors in acute angle
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(4, 5, 6);
		assertEquals(v1.dotProduct(v2), 32, "ERROR: dotProduct() for acute angle does not work correctly");
		// TC02: Test vectors in obtuse angle
		Vector v3 = new Vector(-1, -1, -3);
		assertEquals(v1.dotProduct(v3), -12, "ERROR: dotProduct() for obtuse angle does not work correctly");

		// ================== Boundary Values Tests ==================
		// TC11: Test vectors in 90 degree angle (orthogonal vectors)
		Vector v4 = new Vector(1, 4, -3);
		assertEquals(v1.dotProduct(v4), 0, 0.00001, "ERROR: dotProduct() for 90 degree angle does not work correctly");
		// TC12: Test vectors in 180 degree angle
		assertEquals(v1.dotProduct(v1.scale(-1.5)), -21, 0.00001,//
				"ERROR: dotProduct() for 180 degree angle does not work correctly");
		// TC13: Test dotProduct when one of the vectors is the unit vector
		Vector v5 = new Vector(1, 0, 0);
		assertEquals(v1.dotProduct(v5), 1, 0.00001, "ERROR: dotProduct() for unit vector does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(-2, -4, -6);
		Vector v3 = new Vector(0, 3, -2);

		// ============ Equivalence Partitions Tests ==============
		Vector vr = v1.crossProduct(v3);
		// TC01: Test that the length of the cross-prouct is proper
		// Check that the length of the resulting vector is proper
		assertEquals(vr.length(), v1.length() * v3.length(), 0.00001, "ERROR: crossProduct() wrong result length");
		// Test cross-product result orthogonality to its operands
		assertEquals(vr.dotProduct(v1), 0, "ERROR: crossProduct() result is not orthogonal to 1st operand");
		assertEquals(vr.dotProduct(v3), 0, "ERROR: crossProduct() result is not orthogonal to 2nd operand");

		// =============== Boundary Values Tests ==================
		// TC11: Test zero vector from cross-product of co-lined vectors
		assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
				"ERROR: does not throw exception for cross-product of co-lined vectors");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		Vector v1 = new Vector(0, 4, 3);
		Vector normal = v1.normalize();
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test normalize
		assertEquals(normal.lengthSquared(), 1, 0.00001, "ERROR: normalize() result is not a unit vector");
		// TC02: check the value of the normal vector
		assertEquals(new Vector(0, 0.8, 0.6), normal, "ERROR: normalize() result is wrong");
	}
}
