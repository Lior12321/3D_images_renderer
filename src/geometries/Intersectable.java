package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * The Intersectable interface defines the intersections that may have between
 * geometries forms.
 * 
 * @author Lior &amp; Asaf
 */
public interface Intersectable {
	/**
	 * finds intersections between a given ray and objects
	 * 
	 * @param ray a given ray
	 * @return a list of points of intersections
	 */
	List<Point> findIntersections(Ray ray);
}
