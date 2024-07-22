package scene;

import java.util.List;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

/**
 * Class representing a scene with a name, background color, ambient light, and
 * geometries.
 * 
 * @author Lior &amp; Asaf
 */
public class Scene {

	/** The scene name */
	public String name;

	/** The scene background color. Initialized to black */
	public Color background = Color.BLACK;

	/** The ambient light of the scene. Initialized to NONE */
	public AmbientLight ambientLight = AmbientLight.NONE;

	/** 3D model of the scene geometries forms. Initialized to empty model */
	public Geometries geometries = new Geometries();

	/** The lights in the scene */
	public List<LightSource> lights = new java.util.LinkedList<>();

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

	/**
	 * Adds a light to the scene.
	 * 
	 * @param light the light to add
	 * @return the Scene object itself
	 */
	public Scene setLights(LightSource light) {
		lights.add(light);
		return this;
	}
}
