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

	/**
	 * Constructor for SpotLight class
	 * using the constructor from the PointLight
	 * 
	 * @param intensity the intensity of the light
	 * @param position the position of the light
	 * @param direction the direction of the light
	 */
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
	public SpotLight setKc(double kC) {
		return (SpotLight) super.setKc(kC);
	}

	/**
	 * set the kL value
	 * 
	 * @param kL the linear attenuation coefficient
	 * @return this SpotLight object
	 */
	public SpotLight setKl(double kL) {
		return (SpotLight) super.setKl(kL);
	}

	/**
	 * set the kQ value
	 * 
	 * @param kQ the quadratic attenuation coefficient
	 * @return this SpotLight object
	 */
	public SpotLight setKq(double kQ) {
		return (SpotLight) super.setKq(kQ);
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
