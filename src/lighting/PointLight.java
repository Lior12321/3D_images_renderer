package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

	/** The position of the light */
	protected Point position;
	/** The constant attenuation coefficient */
	private double kC = 1.0;
	/** The linear attenuation coefficient */
	private double kL = 0.0;
	/** The quadratic attenuation coefficient */
	private double kQ = 0.0;

	/**
	 * Constructor for PointLight class
	 * 
	 * @param intensity the intensity of the light
	 * @param position  the position of the light
	 */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
	}

	/**
	 * set the kC value
	 * 
	 * @param kC the constant attenuation coefficient
	 * @return this PointLight object
	 */
	public PointLight setKC(double kC) {
		this.kC = kC;
		return this;
	}

	/**
	 * set the kL value
	 * 
	 * @param kL the linear attenuation coefficient
	 * @return this PointLight object
	 */
	public PointLight setKL(double kL) {
		this.kL = kL;
		return this;
	}

	/**
	 * set the kQ value
	 * 
	 * @param kQ the quadratic attenuation coefficient
	 * @return this PointLight object
	 */
	public PointLight setKQ(double kQ) {
		this.kQ = kQ;
		return this;
	}

	@Override
	public Color getIntensity(Point p) {
		double dSquared = p.distanceSquared(position);
		return intensity.scale(1 / (kC + kL * Math.sqrt(dSquared) + kQ * dSquared));
	}

	@Override
	public Vector getL(Point p) {
		return this.position.subtract(p).normalize();
	}
}
