package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit test for geometries.Geometries class
 * 
 * @author Lior &amp; Asaf
 */
class GeometriesTests {

	/**
	 * Test method for
	 * {@link geometries.Geometries#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		Geometries geos = new Geometries( //
				new Plane(new Point(1, 0, 0), new Point(2, 0, 0), new Point(1.5, 0, 1)), //
				new Triangle(new Point(0, 2, 0), new Point(2, 2, 0), new Point(1.5, 2, 2)), //
				new Sphere(new Point(1, 0, 1), 1d));

		// ============ Equivalence Partitions Tests ==============
		// TC01: More then one object intersect (but not all the objects)
		List<Point> result = geos.findIntersections(new Ray(new Point(1, 1.5, 1), new Vector(0, -1, 0)));
		assertEquals(3, result.size(), "More then one object intersect (but not all the objects)");

		// =============== Boundary Values Tests ==================
		// TC10: Empty list		
        result = new Geometries().findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, -1, 0)));
        assertNull(result, "The List empty");

		// TC11: No intersection with the objects
		result = geos.findIntersections(new Ray(new Point(1, -1, 1), new Vector(0, -1, 0)));
		assertNull(result, "The ray suppose not intersect the objects");

		// TC12: One object intersect
		result = geos.findIntersections(new Ray(new Point(1.5, 1.5, 0.5), new Vector(0, 1, 0)));
		assertEquals(1, result.size(), "Suppose to be one intersection point (one object intersect)");

		// TC13: All the objects intersect
		result = geos.findIntersections(new Ray(new Point(1, 2.5, 1), new Vector(0, -1, 0)));
		assertEquals(4, result.size(), "Suppose to be 4 intersection points");

	}

}
