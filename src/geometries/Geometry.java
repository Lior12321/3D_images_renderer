package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Abstract class representing a geometry in a scene. A geometry is a shape that
 * can be intersected by a ray in the scene. geometry shape has a emission color
 * and material kind.
 * 
 * @author Lior &amp; Asaf
 */
public abstract class Geometry extends Intersectable {

	/** The emission color of the geometry (black is the default color). */
	protected Color emission = Color.BLACK;

	/** The material of the geometry. */
	private Material material = new Material();

	/**
	 * Returns the normal vector to the geometry shape at the specified point.
	 * 
	 * @param point a point on the geometry where the normal is to be calculated
	 * @return the normal vector at the specified point
	 */
	public abstract Vector getNormal(Point point);

	/**
	 * Returns the emission color of the geometry.
	 * 
	 * @return the emission color
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * Returns the material of the geometry.
	 * 
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Sets the geometry's emission color.
	 * 
	 * @param c the emission color to set
	 * @return the current geometry instance (for method chaining)
	 */
	public Geometry setEmission(Color c) {
		emission = c;
		return this;
	}

	/**
	 * Sets the geometry's material.
	 * 
	 * @param material the material to set
	 * @return the current geometry instance (for method chaining)
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}
}
