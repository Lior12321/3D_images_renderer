package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Class representing ambient light in a scene. Ambient light is a global light
 * source that affects all objects equally, without direction.
 * 
 * @author Lior &amp; Asaf
 */
public class AmbientLight {

	// iA - light intensity according to RGB components (Color)
	// kA - fill light attenuation coefficient (Double3/ Double)

	/**
	 * The intensity of the ambient light according to RGB components.
	 */
	private final Color intensity;

	/**
	 * A constant representing no ambient light.
	 */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * constructs Ambient light object with a given intensity and attenuation factor
	 * 
	 * @param iA intensity
	 * @param kA attenuation factor
	 */
	public AmbientLight(Color iA, Double3 kA) {
		intensity = iA.scale(kA);
	}

	/**
	 * constructs Ambient light object with a given intensity and attenuation factor
	 * 
	 * @param iA intensity
	 * @param kA attenuation factor
	 */
	public AmbientLight(Color iA, Double kA) {
		intensity = iA.scale(kA);
	}

	/**
	 * returns the intensity of the light
	 * 
	 * @return intensity the intensity of the light
	 */
	public Color getIntensity() {
		return intensity;
	}

}
