package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;

import static primitives.Util.*;
import static java.lang.Math.*;

import java.util.List;

import primitives.*;
import scene.Scene;

/**
 * This class extends RayTracerBase and provides a basic implementation for
 * tracing rays in a scene.
 * 
 * @author Lior &amp; Asaf
 */
public class SimpleRayTracer extends RayTracerBase {
	/** using to move the point, to not hide itself */
	private static final double DELTA = 0.1;
	/** how much transparency rays will be in the image **/
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	/** the limit to the color before it will be black **/
	private static final double MIN_CALC_COLOR_K = 0.001;
	/** the initial attenuation coefficient (mostly for convenience) **/
	private static final Double3 INITIAL_K = Double3.ONE;

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
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
	}

	/**
	 * Find the closest intersection point of a ray with the geometries in the
	 * scene.
	 * 
	 * @param ray the ray to find the intersection point with
	 * @return the closest intersection point of the ray with the geometries.
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
	}

	/**
	 * Calculates the color at a given point of intersection in the scene. send to
	 * recursive calcColor method with level 1 and initial k.
	 * 
	 * @param gp  the GeoPoint representing the intersection point and its
	 *            associated geometry
	 * @param ray the ray that intersects the geometry
	 * @return the calculated color at the given intersection
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
	}

	/**
	 * Calculates the color at a given point of intersection in the scene.
	 * 
	 * @param gp    the GeoPoint representing the intersection point and its
	 *              associated geometry
	 * @param ray   the ray that intersects the geometry
	 * @param level the level of the recursion
	 * @param k     The stopping condition of the attenuation coefficient
	 * @return the calculated color at the given intersection
	 */
	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
		Vector n = gp.geometry.getNormal(gp.point);
		Vector v = ray.getDir();
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return Color.BLACK;
		Color color = calcLocalEffects(gp, n, v, nv, k);
		return 1 == level ? color : color.add(calcGlobalEffects(gp, n, v, nv, level, k));
	}

	/**
	 * Calculates the color at a given point of intersection in the scene. This
	 * method computes the resultant color at a specified geometric point of
	 * intersection by considering the scene's ambient light, the emission of the
	 * geometry at that point, and the diffusive and specular reflections of the
	 * geometry.
	 * 
	 * @param intersection the intersection point and its associated geometry
	 * @param n            the normal to the geometry at the intersection point
	 * @param v            the direction of the ray that intersects the geometry
	 * @param nv           the dot product of the normal to the geometry and the ray
	 *                     direction
	 * @param k            the stopping condition of the attenuation coefficient
	 * @return the calculated color at the given intersection point
	 */
	private Color calcLocalEffects(GeoPoint intersection, Vector n, Vector v, double nv, Double3 k) {
		Color color = intersection.geometry.getEmission();
		Material material = intersection.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sign(nv)
				Double3 ktr = transparency(intersection, lightSource, l, n, nl);
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
					Color iL = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
				}
			}
		}
		return color;
	}

	/**
	 * calculates the global effects of the light reflection from a geometry
	 * 
	 * @param gp    the point on the geometry
	 * @param n     the normal to the geometry at the intersection point
	 * @param v     the direction of the ray that intersects the geometry
	 * @param nv    the dot product of the normal to the geometry and the ray
	 *              direction
	 * @param level the level of the recursion
	 * @param k     the stopping condition of the attenuation coefficient
	 * @return the global effects of the light that reflect from the geometry
	 */
	private Color calcGlobalEffects(GeoPoint gp, Vector n, Vector v, double nv, int level, Double3 k) {
		Material material = gp.geometry.getMaterial();
		return calcGlobalEffect(constructRefractedRay(gp, v, n), material.kT, level, k)
				.add(calcGlobalEffect(constructReflectedRay(gp, v, n, nv), material.kR, level, k));
	}

	/**
	 * 
	 * calculates the global effect of the light reflection from a geometry
	 * 
	 * @param ray   the ray that intersects the geometry
	 * @param kx    the attenuation coefficient
	 * @param level the level of the recursion
	 * @param k     the stopping condition of the attenuation coefficient
	 * @return the global effect of the light reflection from a geometry
	 */
	private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
		Double3 kkx = kx.product(k.equals(new Double3(MIN_CALC_COLOR_K)) ? Double3.ONE : k);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
	}

	/**
	 * constructs the refracted ray
	 * 
	 * @param gp     the point on the geometry
	 * @param dir    the direction of the ray
	 * @param normal the normal to the geometry
	 * @return the refracted ray
	 */
	private Ray constructRefractedRay(GeoPoint gp, Vector dir, Vector normal) {
		return new Ray(gp.point, dir, normal);
	}

	/**
	 * constructs the reflected ray
	 * 
	 * @param gp        the point on the geometry
	 * @param dir       the direction of the ray
	 * @param normal    the normal to the geometry
	 * @param dirNormal the dot product of the normal to the geometry and the
	 *                  incoming ray direction
	 * @return the reflected ray
	 */
	private Ray constructReflectedRay(GeoPoint gp, Vector dir, Vector normal, double dirNormal) {
		// Vector normalVector = gp.geometry.getNormal(gp.point);
		// Vector direction = dir.subtract(normal.scale(2 * dir.dotProduct(normal)));
		// Vector n = normalVector.scale(2 *
		// dir.dotProduct(normalVector)).subtract(dir);
		return new Ray(gp.point, dir.mirror(normal, dirNormal), normal);
	}

	/**
	 * Calculate the diffusive component of the light reflection from a geometry
	 * 
	 * @param material the material of the geometry
	 * @param nl       the dot product of the normal to the geometry and the vector
	 * @return the diffusive light reflection
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(abs(nl));
	}

	/**
	 * Calculate the specular component of the light reflection from a geometry
	 * 
	 * @param material     the material of the geometry
	 * @param normal       the normal to the geometry and the point of intersection
	 * @param l            the vector from the light source to the point of
	 *                     intersection
	 * @param nl           the dot product of the normal to the geometry and the
	 *                     vector
	 * @param rayDirection the direction of the ray
	 * @return the specular component of the light reflection
	 */
	private Double3 calcSpecular(Material material, Vector normal, Vector l, double nl, Vector rayDirection) {
		double vr = rayDirection.dotProduct(l.mirror(normal, nl));
		return (alignZero(vr) >= 0) ? Double3.ZERO : material.kS.scale(pow(-vr, material.nShininess));
	}

	/**
	 * Checks if the point isn't shaded by another geometry in the scene
	 * 
	 * @deprecated Please use method
	 *             {@link SimpleRayTracer#transparency(GeoPoint, LightSource, Vector, Vector, double)}
	 *             instead
	 * 
	 * @param gp     the intersection point
	 * @param l      the vector from the light source to the point of intersection
	 * @param normal the normal to the geometry and the point
	 * @param light  the light source
	 * @param nl     the dot product of the normal to the geometry and the vector
	 * @return true if the point isn't shaded by another geometry in the scene,
	 *         false otherwise
	 */
	@SuppressWarnings("unused")
	@Deprecated(forRemoval = true)
	private boolean unshaded(GeoPoint gp, Vector l, Vector normal, LightSource light, double nl) {
		Vector lightDirection = l.scale(-1); // from point to light source
		Ray lightRay = new Ray(gp.point.add(normal.scale(nl < 0 ? DELTA : -DELTA)), lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
		if (intersections == null)
			return true;

		for (GeoPoint point : intersections)
			if (!point.geometry.getMaterial().kT.equals(Double3.ZERO))
				return false;
		return true;
	}

	/**
	 * calculates the transparency coefficient of a point on a geometry
	 * 
	 * @param gp     the point of intersection
	 * @param light  the light source
	 * @param l      the direction vector of the light source
	 * @param normal the normal to the geometry and the point
	 * @param nv     the dot product of the normal to the geometry and the vector
	 * @return the refracted ray
	 */
	private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector normal, double nv) {
		// Vector lightDirection = l.scale(-1);
		// Ray lightRay = new Ray(gp.point.add(normal.scale(nv < 0 ? DELTA : -DELTA)),
		// lightDirection);
		Ray lightRay = new Ray(gp.point, l.scale(-1), normal);

		var intersections = scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
		Double3 ktr = Double3.ONE;
		if (intersections == null)
			return ktr;

		for (GeoPoint point : intersections)
			ktr = ktr.product(point.geometry.getMaterial().kT);
		return ktr;
	}
}
