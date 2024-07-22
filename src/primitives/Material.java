package primitives;

/**
 * Class representing a material with diffuse and specular reflection
 * coefficients and shininess.
 * 
 * @author Lior &amp Asaf
 */
public class Material {
	/** The diffuse reflection coefficient of the material */
	public Double3 kD = Double3.ZERO;

	/** The specular reflection coefficient of the material */
	public Double3 kS = Double3.ZERO;

	/** The shininess of the material */
	public int nShininess = 0;

	// setters:

	/**
	 * Sets the diffuse reflection coefficient of the material.
	 * 
	 * @param kD (Double3) the diffuse reflection coefficient
	 * @return the Material object itself
	 */
	public Material setKd(Double3 kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * Sets the diffuse reflection coefficient of the material.
	 * 
	 * @param kD (Double) the diffuse reflection coefficient
	 * @return the Material object itself
	 */
	public Material setKd(double kD) {
		this.kD = new Double3(kD);
		return this;
	}

	/**
	 * Sets the specular reflection coefficient of the material.
	 * 
	 * @param kS (Double3) the specular reflection coefficient
	 * @return the Material object itself
	 */
	public Material setKs(Double3 kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * Sets the specular reflection coefficient of the material.
	 * 
	 * @param kS (Double) the specular reflection coefficient
	 * @return the Material object itself
	 */
	public Material setKs(double kS) {
		this.kS = new Double3(kS);
		return this;
	}

	/**
	 * Sets the shininess of the material.
	 * 
	 * @param nShininess the shininess
	 * @return the Material object itself
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

}
