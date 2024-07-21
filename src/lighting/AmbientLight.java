package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Class representing ambient light in a scene. Ambient light is a global light
 * source that affects all objects equally, without direction. extends Light
 * class
 * 
 * @see Light
 * @author Lior &amp; Asaf
 */
public class AmbientLight extends Light {
	// iA - light intensity according to RGB components (Color)
	// kA - fill light attenuation coefficient (Double3/ Double)

	/** A constant representing no ambient light */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * constructs Ambient light object with a given intensity and attenuation factor
	 * using the constructor from Light class
	 * 
	 * @param iA intensity
	 * @param kA attenuation factor
	 */
	public AmbientLight(Color iA, Double3 kA) {
		super(iA.scale(kA));
	}

	/**
	 * constructs Ambient light object with a given intensity and attenuation factor
	 * using the constructor from Light class
	 * 
	 * @param iA intensity
	 * @param kA attenuation factor
	 */
	public AmbientLight(Color iA, Double kA) {
		super(iA.scale(kA));
	}
}
