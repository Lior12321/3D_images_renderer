package lighting;

import primitives.Color;

/**
 * Abstract class representing a light source in a scene
 */
abstract class Light {
	/** The intensity of the light */
	protected Color intensity;

	/**
	 * Constructor for Light class
	 * 
	 * @param intensity the intensity of the light
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * Getter for the intensity of the light
	 * 
	 * @return the intensity of the light
	 */
	public Color getIntensity() {
		return intensity;
	}

}
