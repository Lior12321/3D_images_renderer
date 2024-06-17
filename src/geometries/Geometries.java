package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Geometries implements Intersectable {

	/*
	 * list of all the geometries bodies
	 */
	private final List<Intersectable> geometries = new LinkedList<Intersectable>();

	/**
	 * default contractor for Geometries class
	 */
	public Geometries() {
	}

	/**
	 * parametric contractor to add all the geometries bodies to the geometries list
	 * 
	 * @param geometries
	 */
	public Geometries(Intersectable... geometries) {
		this.add(geometries);
	}

	/**
	 * Adds multiple geometries to the collection.
	 * 
	 * @param geometries
	 */
	public void add(Intersectable... geometries) {
		this.geometries.addAll(List.of(geometries));
	}

	@Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersectionList = null;
        for (Intersectable geo : geometries) {
            List<Point> intersections = geo.findIntersections(ray);
            if (intersections != null) {
                if (intersectionList == null) {
                    intersectionList = new LinkedList<>();
                }
                intersectionList.addAll(intersections);
            }
        }
        return intersectionList;
    }
}
