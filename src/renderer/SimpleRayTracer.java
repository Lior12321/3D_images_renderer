package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/**
 * This class extends RayTracerBase and provides a basic implementation for
 * tracing rays in a scene.
 * 
 * @author Lior &amp; Asaf
 */
public class SimpleRayTracer extends RayTracerBase {

	/**
	 * Constructs a SimpleRayTracer with a given scene.
	 *
	 * @param scene the scene to be rendered
	 */
	public SimpleRayTracer(Scene scene) {
		super(scene);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Color traceRay(Ray ray) {
		List<Point> intersectionPoints = scene.geometries.findIntersections(ray);
		if (intersectionPoints == null)
			return scene.background;
		return calcColor(ray.findClosestPoint(intersectionPoints));
	}

	/**
	 * Calculates the color at a given point of intersection in the scene.
	 * (Currently, it only returns the ambient light intensity)
	 * 
	 * @param p the intersection point at which to calculate the color
	 * @return the color at the given intersection point
	 */
	private Color calcColor(Point p) {
		return scene.ambientLight.getIntensity();
		//TODO: use the point that we got as a parameter to determinate the color
	}

}
