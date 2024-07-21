package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Interface representing a light source in a scene
 * 
 * @author Lior &amp; Asaf
 */
public interface LightSource {
	/**
	 * Returns the intensity of the light at a given point
	 * 
	 * @param p the point at which to calculate the intensity
	 * @return the intensity of the light at the given point
	 */
	public Color getIntensity(Point p);

	/**
	 * Returns the vector from the light source to a given point
	 * 
	 * @param p the point at which to calculate the vector
	 * @return the vector from the light source to the given point
	 */
	public Vector getL(Point p);
}
