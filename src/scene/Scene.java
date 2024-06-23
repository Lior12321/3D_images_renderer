package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * Class representing a scene with a name, background color, ambient light, and
 * geometries.
 * 
 * @author Lior &amp; Asaf
 */
public class Scene {

	/**
	 * the scene name
	 */
	public String name;

	/**
	 * the scene background color. Initialized to black
	 */
	public Color background = Color.BLACK;

	/**
	 * the ambient light of the scene. Initialized to NONE
	 */
	public AmbientLight ambientLight = AmbientLight.NONE;

	/**
	 * 3D model of the scene geometries forms. Initialized to empty model
	 */
	public Geometries geometries = new Geometries();

	/**
	 * Constructs a new Scene with the given name.
	 * 
	 * @param name the name of the scene
	 */
	public Scene(String name) {
		this.name = name;
	}

	// setters:

	/**
	 * Sets the background color of the scene.
	 * 
	 * @param background the background color
	 * @return the Scene object itself
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * Sets the ambient light of the scene.
	 * 
	 * @param ambientLight the ambient light
	 * @return the Scene object itself
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * Sets the geometries of the scene.
	 * 
	 * @param geometries the geometries
	 * @return the Scene object itself
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}
}
