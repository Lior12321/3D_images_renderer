package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract base class for ray tracers. A ray tracer is responsible for tracing
 * rays in a given scene and determining the color seen along each ray.
 * 
 * @author Lior &amp; Asaf
 */
public abstract class RayTracerBase {
	/**
     * The scene to be rendered.
     */
	protected Scene scene;
	
	/**
     * Constructs a RayTracerBase with a given scene.
     *
     * @param scene the scene to be rendered
     */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}
	
	/**
     * Traces a ray in the scene and determines the color seen along the ray.
     *
     * @param ray the ray to be traced
     * @return the color seen along the ray
     */
	public abstract Color traceRay(Ray ray);
}
