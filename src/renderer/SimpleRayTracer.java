package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;

import static primitives.Util.*;

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
	 * @return the calculated color at the given intersection point
	 */
	private Color calcColor(GeoPoint intersection, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
	}

	/**
	 * Calculates the color at a given point of intersection in the scene. This
	 * helper method computes the resultant color at a specified geometric point of
	 * intersection by considering the scene's ambient light and the emission of the
	 * geometry at that point.
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
			if (nl * nv > 0) { // sign(nl) == sign(nv)
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
		nl = Math.abs(nl); //for checking
		return material.kD.scale(nl);
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
		return material.kD.scale(Math.pow(Math.max(0, -v.dotProduct(l.subtract(n.scale(nl * 2)))), material.nShininess));
	}

}
