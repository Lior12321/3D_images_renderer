package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents geometric shapes in a 3D space. Any class
 * that implements this interface should be able to provide a normal vector to
 * the shape at given point.
 * 
 * @author Lior &amp; Asaf
 */
public interface Geometry {

	/**
	 * Returns the normal vector to the geometry at the specified point.
	 * 
	 * @param point a point on the geometry where the normal is to be calculated
	 * @return the normal vector at the specified point
	 */
	Vector getNormal(Point point);
}
