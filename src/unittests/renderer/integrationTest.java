package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import scene.Scene;

/**
 * integration test class
 *
 * @author Lior &amp; Asaf
 */
class integrationTest {
	/**
	 * Counts the number of intersections between the rays constructed by the camera
	 * and the given geometry.
	 * 
	 * @param camera the camera
	 * @param obj    the geometry to test intersections with
	 * @param width  the width of the view plane
	 * @param height the height of the view plane
	 * @return the number of intersection points
	 */
	private int intersectionCount(Camera camera, Intersectable obj, int width, int height) {
		int intersectionCount = 0;
		List<Point> temp = null;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				temp = obj.findIntersections(camera.constructRay(width, height, y, x));
				if (temp != null)
					intersectionCount += temp.size();
			}
		}
		return intersectionCount;
	}

	 /** Constructing the camera */
	Camera.Builder camera = Camera.getBuilder();
	/** Error message for assertion failures */
	private final String errorMassage = "ERROR: Wrong intersections number";
	//* ImageWriter example  */
	private final ImageWriter imageWriter = new ImageWriter("base render test", 500, 800);
	//* Scene example */
	private final Scene scene = new Scene("test scene");
	//* SimpleRayTracer example */
	private final SimpleRayTracer rayTracer = new SimpleRayTracer(scene);

	/**
	 * Test method for intersections of camera rays with spheres.
	 */
	@Test
	void sphereTest() {
		final Vector v010 = new Vector(0, 1, 0);
		final Vector nv001 = new Vector(0, 0, -1);
		camera.setVpSize(3, 3)
		.setVpDistance(1)
		.setDirection(nv001, v010)
		.setLocation(Point.ZERO)
		.setImageWriter(imageWriter)
		.setRayTracer(rayTracer)
		.build();
		
		// TC01: Two intersection points
		Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
		
		assertEquals(2, intersectionCount(camera.getCamera(), sphere, 3, 3), errorMassage);

		// TC02: Eighteen intersection points
		sphere = new Sphere(new Point(0, 0, -2.5), 2.5);
		camera.setLocation(new Point(0, 0, 0.5));
		camera.build();
		assertEquals(18, intersectionCount(camera.getCamera(), sphere, 3, 3), errorMassage);

		// TC03: Ten intersection points
		sphere = new Sphere(new Point(0, 0, -2), 2);
		assertEquals(10, intersectionCount(camera.getCamera(), sphere, 3, 3), errorMassage);

		// TC04: Nine intersection points
		sphere = new Sphere(Point.ZERO, 4);
		assertEquals(9, intersectionCount(camera.getCamera(), sphere, 3, 3), errorMassage);

		// TC05: Zero intersection points
		sphere = new Sphere(new Vector(0, 0, 1), 0.5);
		assertEquals(0, intersectionCount(camera.getCamera(), sphere, 3, 3), errorMassage);
	}

	/**
	 * Test method for intersections of camera rays with plane.
	 */
	@Test
	void planeTest() {
		camera.setVpSize(3, 3)
		.setVpDistance(1)
		.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
		.setLocation(Point.ZERO)
		.setImageWriter(imageWriter)
		.setRayTracer(rayTracer)
		.build();
		final Point np003 = new Point(0, 0, -3);

		// TC01: Nine intersection points
		Plane plane = new Plane(new Vector(0, 0, 1), np003);
		assertEquals(9, intersectionCount(camera.getCamera(), plane, 3, 3), errorMassage);

		// TC02: Nine intersection points
		plane = new Plane(new Vector(0, 0.2, -1), np003);
		assertEquals(9, intersectionCount(camera.getCamera(), plane, 3, 3), errorMassage);

		// TC03: Six intersection points
		plane = new Plane(new Vector(0, 1, -1), np003);
		assertEquals(6, intersectionCount(camera.getCamera(), plane, 3, 3), errorMassage);

	}

	/**
	 * Test method for intersections of camera rays with triangle.
	 */
	@Test
	void triangleTest() {
		camera.setVpSize(3, 3)
		.setVpDistance(1)
		.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
		.setLocation(Point.ZERO)
		.setImageWriter(imageWriter)
		.setRayTracer(rayTracer)
		.build();
		
		final Point p112 = new Point(1, -1, -2);
		final Point np112 = new Point(-1, -1, -2);

		// TC01: One intersection points
		Triangle triangle = new Triangle(p112, np112, new Point(0, 1, -2));
		assertEquals(1, intersectionCount(camera.getCamera(), triangle, 3, 3), errorMassage);

		// TC01: Two intersection points
		triangle = new Triangle(p112, np112, new Point(0, 20, -2));
		assertEquals(2, intersectionCount(camera.getCamera(), triangle, 3, 3), errorMassage);
	}

}
