package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * The Intersectable abstract class defines the intersections that may have
 * between geometries forms.
 * 
 * @author Lior &amp; Asaf
 */
public abstract class Intersectable {
	/**
	 * finds all the intersections between a given ray and objects
	 * 
	 * @param ray a given ray
	 * @return a list of points of intersections
	 */
	public final List<Point> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * find intersections of the given ray with the shapes. using the helper
	 * function
	 * 
	 * @param ray the given ray parameter to find the intersections with
	 * @return list of all the intersections points of the ray with the shape.
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
	}

	/**
	 * find intersections of the given ray with the shapes. returns only the
	 * intersections that are closer than the given maximum
	 * 
	 * @param ray         the given ray parameter to find the intersections with
	 * @param maxDistance the maximum distance to find the intersections
	 * @return list of all the intersections points of the ray with the shape.
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
		return findGeoIntersectionsHelper(ray, maxDistance);
	}

	/**
	 * find intersections of the given ray with the shapes. returns only the
	 * intersections that are closer than the given maximum
	 * 
	 * @param ray         the given ray parameter
	 * @param maxDistance the maximum distance to find the intersections
	 * @return list of all the intersections points of the ray with the shape.
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

	/**
	 * GeoPoint is a static inner class that associates a Geometry object with a
	 * specific point in 3D space. This class is used to represent an intersection
	 * point on a geometry along with the geometry itself.
	 * 
	 * @see Geometry
	 * @see Point
	 */
	public static class GeoPoint {

		/** The geometry associated with the point. */
		public Geometry geometry;

		/** The specific point on the geometry. */
		public Point point;

		/**
		 * Parametric constructor for GeoPoint
		 * 
		 * @param geometry given geometry
		 * @param point    given point
		 */
		public GeoPoint(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			return (obj instanceof GeoPoint other) && this.geometry == other.geometry && this.point.equals(other.point);
		}

		@Override
		public String toString() {
			return "" + geometry + ", p0: " + point;
		}

	}
}