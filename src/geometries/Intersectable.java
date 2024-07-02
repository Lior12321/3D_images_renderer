package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The Intersectable abstract class defines the intersections that may have
 * between geometries forms.
 * 
 * @author Lior &amp; Asaf
 */
public abstract class Intersectable {
	/**
	 * finds intersections between a given ray and objects
	 * 
	 * @param ray a given ray
	 * @return a list of points of intersections
	 */
	public abstract List<Point> findIntersections(Ray ray);

	/**
	 * GeoPoint is a static inner class that associates a Geometry object with a
	 * specific point in 3D space.
	 * <p>
	 * This class is used to represent an intersection point on a geometry along
	 * with the geometry itself.
	 * 
	 * @see Geometry
	 * @see Point
	 */
	public static class GeoPoint {

		/** The geometry associated with the point. */
		public Geometry geometry;

		/** The specific point on the geometry. */
		public Point point;

		public GeoPoint(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			return (obj instanceof GeoPoint other) && this.geometry.equals(other.geometry)
					&& this.point.equals(other.point);
		}

		@Override
		public String toString() {
			return "" + geometry.toString() + ", p0: " + point.toString();
		}

	}
}