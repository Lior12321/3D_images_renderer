package renderer;

import java.util.List;
import geometries.Intersectable.GeoPoint;

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
	}

	@Override
	public Color traceRay(Ray ray) {
		var intersections = scene.geometries.findGeoIntersections(ray);
		return intersections == null ? scene.background : calcColor(ray.findClosestGeoPoint(intersections));
	}

	/**
	 * Calculates the color at a given point of intersection in the scene.
	 * This method computes the resultant color at a specified geometric point of
	 * intersection by considering the scene's ambient light and the emission of the
	 * geometry at that point.
	 * 
	 * @param gp the GeoPoint representing the intersection point and its associated
	 *           geometry
	 * @return the calculated color at the given intersection point
	 */
	private Color calcColor(GeoPoint gp) {
		return scene.ambientLight.getIntensity().add(gp.geometry.getEmission());
	}

}
