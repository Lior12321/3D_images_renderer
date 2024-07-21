package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class representing a directional light in a scene. A directional light is a
 * light source that affects all objects equally, with a specific direction.
 * 
 * @see Light
 * @author Lior &amp; Asaf
 */
public class DirectionalLight extends Light implements LightSource {

	/** The direction of the light */
	final private Vector direction;

	/**
	 * Constructor for DirectionalLight class
	 * 
	 * @param intensity the intensity of the light
	 * @param direction the direction of the light
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction;
	}

	@Override
	public Color getIntensity(Point p) {
		return intensity;
	}

	@Override
	public Vector getL(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

}
