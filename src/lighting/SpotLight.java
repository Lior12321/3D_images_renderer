package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class representing a spot light in a scene. A spot light is a light source
 * that emits light from a single point in a specific direction.
 * 
 * @see PointLight
 * @author Lior &amp; Asaf
 */
public class SpotLight extends PointLight {

	/** The direction of the light */
	final private Vector direction;

	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction;
	}

	/**
	 * set the kC value
	 * 
	 * @param kC the constant attenuation coefficient
	 * @return this SpotLight object
	 */
	public SpotLight setKC(double kC) {
		return (SpotLight) super.setKC(kC);
	}

	/**
	 * set the kL value
	 * 
	 * @param kL the linear attenuation coefficient
	 * @return this SpotLight object
	 */
	public SpotLight setKL(double kL) {
		return (SpotLight) super.setKL(kL);
	}

	/**
	 * set the kQ value
	 * 
	 * @param kQ the quadratic attenuation coefficient
	 * @return this SpotLight object
	 */
	public SpotLight setKQ(double kQ) {
		return (SpotLight) super.setKQ(kQ);
	}

	@Override
	public Color getIntensity(Point p) {
		var dirL = direction.dotProduct(getL(p));
		return dirL <= 0 ? Color.BLACK : super.getIntensity(p).scale(dirL);
	}

	@Override
	public Vector getL(Point p) {
		return super.getL(p);
	}

}
