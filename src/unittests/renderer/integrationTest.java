package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.Point;
import renderer.Camera;
import renderer.Camera.Builder;

class integrationTest {
	static final Point ZERO_POINT = new Point(0, 0, 0);
	static final String wrongIntersection = "Wrong number of intersections";

	private int sendRays(Camera cam, Intersectable obj, int nX, int nY) {
		int intersectionCount = 0;
		List<Point> temp = null;

		for (int x = 0; x < nX; x++) {
			for (int y = 0; y < nY; y++) {
				temp = obj.findIntersections(cam.constructRay(nX, nY, y, x));
				if (temp != null)
					intersectionCount += temp.size();
			}
		}
		return intersectionCount;
	}

	Builder camera = new Builder();

	@Test
	void test() {
		Sphere sphere = new Sphere(new Point(2, 1, 1), 2);
		// assertEquals(null, null);
		fail("Not yet implemented");
	}

}
