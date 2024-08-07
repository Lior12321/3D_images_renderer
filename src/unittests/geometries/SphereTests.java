package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import geometries.Sphere;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit test for geometries.Sphere class
 *
 * @author Lior &amp; Asaf
 */
public class SphereTests {
	/** A point with (1, 0, 0) coordinates */
	private final Point p100 = new Point(1, 0, 0);
	/** A vector with (1, 0, 0) coordinates */
	private final Vector v100 = new Vector(1, 0, 0);
	/** A vector with (0, 1, 0) coordinates */
	private final Vector v010 = new Vector(0, 1, 0);

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

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Sphere sphere = new Sphere(p100, 1d);
		final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
		final var exp = List.of(gp1, gp2);
		final Vector v310 = new Vector(3, 1, 0);
		final Vector v110 = new Vector(1, 1, 0);
		final Point p01 = new Point(-1, 0, 0);
		final Point p110 = new Point(1, 1, 0);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray's line is outside the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");

		// TC02: Ray starts before and crosses the sphere (2 points)
		final var result1 = sphere.findIntersections(new Ray(p01, v310)).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
		assertEquals(2, result1.size(), "Wrong number of points");
		assertEquals(exp, result1, "Ray crosses sphere");

		// TC03: Ray starts inside the sphere (1 point)
		final var result2 = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 2, 0)));
		assertEquals(1, result2.size(), "There should be one intersection");
		assertEquals(List.of(new Point(1, 1, 0)), result2, "Incorrect intersection point");

		// TC04: Ray starts after the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(3, 0.5, 0), v100)),
				"There shouldn't be any intersections");

		// =============== Boundary Values Tests ==================

		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		var result = sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-1, 1, 0)));
		assertEquals(1, result.size(), "There should be exactly one intersection");
		assertEquals(List.of(p110), result, "Incorrect intersection point");

		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(1, 0, 1), new Vector(1, 0, 2))), //
				"There shouldn't be any intersections");

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)
		result = sphere.findIntersections(new Ray(p01, v100)).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();

		assertEquals(2, result.size(), "There should be two intersections");
		assertEquals(List.of(new Point(0, 0, 0), new Point(2, 0, 0)), result, //
				"Incorrect intersection points");

		// TC14: Ray starts at sphere and goes inside (1 points)
		result = sphere.findIntersections(new Ray(new Point(1, -1, 0), v010));
		assertEquals(1, result.size(), "There should be one intersection");
		assertEquals(List.of(p110), result, "Incorrect intersection point");

		// TC15: Ray starts inside (1 points)
		result = sphere.findIntersections(new Ray(new Point(0.5, 0, 0), v100));
		assertEquals(1, result.size(), "There should be one intersection");
		assertEquals(List.of(new Point(2, 0, 0)), result, "Incorrect intersection point");

		// TC16: Ray starts at the center (1 points)
		result = sphere.findIntersections(new Ray(p100, v010));
		assertEquals(1, result.size(), "There should be one intersection");
		assertEquals(List.of(p110), result, "Incorrect intersection point");

		// TC17: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(1, 0, 1), new Vector(0, 0, 1))), //
				"There shouldn't be any intersections");

		// TC18: Ray starts after sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(3, 0, 0), v100)), //
				"There shouldn't be any intersections");

		// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
		// TC19: Ray starts before the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(0, 1, 0), v100)), //
				"There shouldn't be any intersections");

		// TC20: Ray starts at the tangent point
		assertNull(sphere.findIntersections(new Ray(p110, v100)), //
				"There shouldn't be any intersections");

		// TC21: Ray starts after the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(2, 1, 0), v100)), //
				"There shouldn't be any intersections");

		// **** Group: Special cases
		// TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's
		// center line
		assertNull(sphere.findIntersections(new Ray(new Point(1, 2, 0), v100)), //
				"There shouldn't be any intersections");
	}

	/**
	 * tests
	 * {@link geometries.Sphere#findGeoIntersectionsHelper(primitives.Ray, double)}
	 */
	@Test
	void testFindGeoIntersectionsDistance() {
		Sphere sphere = new Sphere(new Point(50, 0, 0), 50d);
		Ray rayFromInside = new Ray(new Point(10, 0, 0), new Vector(1, 0, 0));
		Ray rayFromOutside = new Ray(new Point(-10, 0, 0), new Vector(1, 0, 0));

		// ================= Equivalence Partitions Tests ===========================
		// **** Group: ray starts outside
		// TC01: both point on the sphere are not too far (2 points)
		assertEquals(2, sphere.findGeoIntersections(rayFromOutside, 200).size());

		// TC02: one point is too far and the other is not (1 points)
		assertEquals(1, sphere.findGeoIntersections(rayFromOutside, 20).size());

		// TC03: both points are too far (0 points)
		assertNull(sphere.findGeoIntersections(rayFromOutside, 1));

		// **** Group: ray starts inside
		// TC04: the point is not too far (1 points)
		assertEquals(1, sphere.findGeoIntersections(rayFromInside, 200).size());

		// TC05: the point is too far (0 points)
		assertNull(sphere.findGeoIntersections(rayFromInside, 1));

		// ================= BVA Tests ===========================
		// ****Group: ray starts outside
		// TC06: the first intersections is exactly at the max distance (0 points)
		assertNull(sphere.findGeoIntersections(rayFromOutside, 10));

		// TC07: the second intersections is exactly at the max distance (1 points)
		assertEquals(1, sphere.findGeoIntersections(rayFromOutside, 110).size());

		// **** Group: ray starts inside
		// TC08: the intersection is exactly at the max distance (0 points)
		assertNull(sphere.findGeoIntersections(rayFromInside, 40));

	}
}