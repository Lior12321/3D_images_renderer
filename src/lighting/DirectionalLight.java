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
	private final Vector direction;

	/**
	 * Constructor for DirectionalLight class using the constructor from the Light
	 * class
	 * 
	 * @param intensity the intensity of the light
	 * @param direction the direction of the light
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		return intensity;
	}

	@Override
	public Vector getL(Point p) {
		return direction;
	}

	@Override
	public double getDistance(Point point) {
		return Double.POSITIVE_INFINITY;
	}

}
