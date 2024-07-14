package geometries;

import java.util.LinkedList;
import java.util.List;
import primitives.Ray;

/**
 * A composite class representing a collection of geometric objects. This class
 * implements the Intersectable interface, allowing it to find intersections
 * with a ray.
 * 
 * @author Lior &amp; Asaf
 */
public class Geometries extends Intersectable {

	/**
	 * list of all the geometries bodies
	 */
	private final List<Intersectable> geometries = new LinkedList<>();

	/**
	 * default contractor for Geometries class
	 */
	public Geometries() {
	}

	/**
	 * parametric contractor to add all the geometries bodies to the geometries list
	 * 
	 * @param geometries an array of Intersectable objects to be added to the
	 *                   geometries list.
	 */
	public Geometries(Intersectable... geometries) {
		this.add(geometries);
	}

	/**
	 * Adds multiple geometries to the collection.
	 * 
	 * @param geometries an array of Intersectable objects to be added to the
	 *                   geometries list.
	 */
	public void add(Intersectable... geometries) {
		this.geometries.addAll(List.of(geometries));
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> intersectionList = null;
		for (Intersectable geo : geometries) {
			var intersections = geo.findGeoIntersections(ray);
			if (intersections != null) {
				if (intersectionList == null)
					intersectionList = new LinkedList<>(intersections);
				else
					intersectionList.addAll(intersections);
			}
		}
		return intersectionList;
	}
}
