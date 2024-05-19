package geometries;

import primitives.Vector;
import primitives.Point;

public class Tube extends RadialGeometry {
    private static Ray axis;

    /**
     * constructor
     * @param ray
     */
    public Tube(Ray ray) {
        this.axis = ray;
    }

    public Vector getNormal(Point p) {
        return null; // will return NULL for now
    }
}