package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry abstract class represents geometric shapes in a 3D space. Any
 * class that extends this abstract class should be able to provide a normal
 * vector to the shape at a given point.
 * 
 * @author Lior &amp; Asaf
 */
public abstract class Geometry extends Intersectable {

	/** The emission color of the geometry (black is the default color). */
	protected Color emission = Color.BLACK;

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
	 * Sets the geometry's emission color.
	 * 
	 * @param c the emission color to set
	 * @return the current geometry instance (for method chaining)
	 */
	public Geometry setEmission(Color c) {
		emission = c;
		return this;
	}
}
