package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;

import static primitives.Util.*;

import java.util.List;

import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

/**
 * This class extends RayTracerBase and provides a basic implementation for
 * tracing rays in a scene.
 * 
 * @author Lior &amp; Asaf
 */
public class SimpleRayTracer extends RayTracerBase {
	/** The constant value used to determine the accuracy of the calculations. */
	private static final double DELTA = 0.1;

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
		return intersections == null ? scene.background : calcColor(ray.findClosestGeoPoint(intersections), ray);
	}

	/**
	 * Calculates the color at a given point of intersection in the scene. This
	 * method computes the resultant color at a specified geometric point of
	 * intersection by considering the scene's ambient light and the emission of the
	 * geometry at that point.
	 * 
	 * @param intersection the GeoPoint representing the intersection point and its
	 *                     associated geometry
	 * @param ray          the ray that intersects the geometry
	 * @return the calculated color at the given intersection
	 */
	private Color calcColor(GeoPoint intersection, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
	}

	/**
	 * Calculates the color at a given point of intersection in the scene. This
	 * method computes the resultant color at a specified geometric point of
	 * intersection by considering the scene's ambient light, the emission of the
	 * geometry at that point, and the diffusive and specular reflections of the
	 * geometry.
	 * 
	 * @param intersection the intersection point and its associated geometry
	 * @param ray          the ray that intersects the geometry
	 * @return the calculated color at the given intersection point
	 */
	private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
		Vector n = intersection.geometry.getNormal(intersection.point);
		Vector v = ray.getDir();
		double nv = alignZero(n.dotProduct(v));
		Color color = intersection.geometry.getEmission();
		if (nv == 0)
			return color;
		Material material = intersection.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if ((nl * nv > 0) && unshaded(intersection, l, n, lightSource)) { // sign(nl) == sign(nv)
				Color iL = lightSource.getIntensity(intersection.point);
				color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));

			}
		}
		return color;
	}

	/**
	 * Calculate the diffusive component of the light reflection from a geometry
	 * 
	 * @param material the material of the geometry
	 * @param nl       the dot product of the normal to the geometry and the vector
	 * @return the diffusive component of the light reflection
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(Math.abs(nl));
	}

	/**
	 * Calculate the specular component of the light reflection from a geometry
	 * 
	 * @param material the material of the geometry
	 * @param n        the normal to the geometry and the point of intersection
	 * @param l        the vector from the light source to the point of intersection
	 * @param nl       the dot product of the normal to the geometry and the vector
	 * @param v        the direction vector of the ray
	 * @return the specular component of the light reflection
	 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
		Vector r = l.subtract(n.scale(2 * nl));
		double minusVR = -alignZero(v.dotProduct(r));
		return minusVR <= 0 ? Double3.ZERO : material.kS.scale(Math.pow(minusVR, material.nShininess));
	}

	/**
	 * Checks if the point isn't shaded by another geometry in the scene
	 * 
	 * @param gp the intersection point
	 * @param l  the vector from the light source to the point of intersection
	 * @param n  the normal to the geometry and the point
	 * @return true if the point isn't shaded by another geometry in the scene,
	 *         false otherwise
	 */
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource light) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point.add(n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA)),
				lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
		return intersections == null || intersections.get(0).geometry.getMaterial().kD == Double3.ZERO;
	}

}

/**
 * solution 1 - unshaded method:
 * 
 * private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource light)
 * { Vector lightDirection = l.scale(-1); // from point to light source Ray
 * lightRay = new Ray(gp.point.add(n.scale(n.dotProduct(lightDirection) > 0 ?
 * DELTA : -DELTA)), lightDirection); List<GeoPoint> intersections =
 * scene.geometries.findGeoIntersections(lightRay); if (intersections == null)
 * return true; double lightDistance = light.getDistance(gp.point); for
 * (GeoPoint geoPoint : intersections) if
 * (alignZero(geoPoint.point.distance(gp.point) - lightDistance) <= 0) return
 * false; return true; }
 **/