package geometries;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents geometric shapes in a 3D space. Any class
 * that implements this interface should be able to provide a normal vector to
 * the shape at given points.
 * 
 * @author Lior &amp; Asaf
 */
public interface Geometry {
    
    /**
     * Returns the normal vector to the geometry at the specified points.
     * 
     * @param p one or zero points on the geometry where the normal is to be calculated
     * @return the normal vector at the specified points
     */
	public abstract Vector getNormal();
	public abstract Vector getNormal(Point p);
	public abstract Vector getNormal(Point... p);
}
