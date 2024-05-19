package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry {
    private static Point center;

    /**
     * constructor
     * @param _center
     * @param _radius
     */
    public Sphere(Point _center, double _radius) {
        this.center = _center;
        this.radius = _radius;
    }

    @Override
    public String toString() {
        return "Sphere{" + "center=" + center + ", radius=" + radius + '}';
    }

    @Override
    public static Vector getNormal(Point p) {
        return null;
    }
}