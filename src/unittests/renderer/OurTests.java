package unittests.renderer;

import renderer.*;
import scene.Scene;
import static java.awt.Color.WHITE;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Tests for all of our pictures (for MP1 &amp; MP2)
 * 
 * @author Lior &amp; Asaf
 */
class OurTests {
	/** Scene for the tests */
	private final Scene scene = new Scene("Test scene");
	/** Camera builder for the tests with triangles */
	private final Camera.Builder cameraBuilder = Camera.getBuilder().setDirection(Vector.MINUS_Z, Vector.Y)
			.setRayTracer(new SimpleRayTracer(scene));

	/** Produce a picture of a two larvas. */
	@Test
	public void larvasPictureTest() {
		scene.geometries.add(
				// first larva body - five spheres
				// #1
				new Sphere(new Point(30, -20, -50), 15d).setEmission(new Color(0, 80, 0))
						.setMaterial(new Material().setKd(0.15).setKs(0.25).setShininess(10)),
				// #2
				new Sphere(new Point(20, 0, -50), 15d).setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKs(0.15).setShininess(10)),
				// #3
				new Sphere(new Point(0, 15, -50), 15d).setEmission(new Color(0, 120, 0))
						.setMaterial(new Material().setKs(0.15).setShininess(10)),
				// #4
				new Sphere(new Point(-25, 15, -50), 15d).setEmission(new Color(0, 140, 0))
						.setMaterial(new Material().setKs(0.15).setShininess(10)),
				// #5
				new Sphere(new Point(-50, 10, -50), 15d).setEmission(new Color(0, 160, 0))
						.setMaterial(new Material().setKs(0.15).setShininess(10)),

				// first larva tentacles - two triangles
				new Triangle(new Point(41, -24.5, -40), new Point(35, -27.5, -41), new Point(35, -23, -25))
						.setEmission(Color.BLACK).setMaterial(new Material().setKs(0.8).setKt(0.5).setShininess(60)),
				new Triangle(new Point(31, -30, -41), new Point(25, -31, -40), new Point(27, -26, -25))
						.setEmission(Color.BLACK).setMaterial(new Material().setKs(0.8).setKt(0.5).setShininess(60)),

				// second larva body - five spheres
				// #1
				new Sphere(new Point(0, -40, -59), 6d).setEmission(new Color(0, 80, 0))
						.setMaterial(new Material().setKd(0.05).setKs(0.25).setShininess(5)),
				// #2
				new Sphere(new Point(-2, -30, -59), 6d).setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.2).setShininess(5)),
				// #3
				new Sphere(new Point(-8, -23, -59), 6d).setEmission(new Color(0, 120, 0))
						.setMaterial(new Material().setKd(0.2).setShininess(5)),
				// #4
				new Sphere(new Point(-15, -18, -59), 6d).setEmission(new Color(0, 140, 0))
						.setMaterial(new Material().setKd(0.2).setShininess(5)),
				// #5
				new Sphere(new Point(-24, -18, -59), 6d).setEmission(new Color(0, 160, 0))
						.setMaterial(new Material().setKd(0.2).setShininess(5)),

				// second larva tentacles - two triangles
				new Triangle(new Point(3, -42, -55), new Point(1.5, -42, -54), new Point(2, -40, -49))
						.setEmission(Color.BLACK).setMaterial(new Material().setKs(0.8).setShininess(60)),
				new Triangle(new Point(-1.5, -42, -54), new Point(-3, -42, -55), new Point(-2, -40, -49))
						.setEmission(Color.BLACK).setMaterial(new Material().setKs(0.8).setShininess(60)),

				// surface
				new Triangle(new Point(-100, -100, -65), new Point(100, -100, -65), new Point(100, 40, -65))
						.setEmission(new Color(0, 50, 0))
						.setMaterial(new Material().setKs(0.8).setKt(0.5).setShininess(60)),
				new Triangle(new Point(-100, -100, -65), new Point(-100, 40, -65), new Point(100, 40, -65))
						.setEmission(new Color(0, 50, 0))
						.setMaterial(new Material().setKs(0.8).setKt(0.5).setShininess(60)),

				// mirror to reflect the larvas
				new Triangle(new Point(-100, 40, -65), new Point(100, 40, -65), new Point(0, 140, 250))
						.setMaterial(new Material().setKr(0.9)).setEmission(new Color(20, 20, 20)));

		// Adding lights
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.10));
		// The "sun"
		scene.lights.add(new DirectionalLight(new Color(450, 450, 0), new Vector(-1, -1, -7.5)));
		// first larva spot
		scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(40, -40, 55), new Vector(1, 1, -20))
				.setKl(4E-10).setKq(2E-50));
		// second larva spot
		scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(0, -50, -15), new Vector(1, -1, -20))
				.setKl(4E-10).setKq(2E-50));
		// the "fake sun" on the left top corner, in the last squad of the larva (in
		// case we need to use all the lights)
		scene.lights.add(new PointLight(new Color(450, 450, 0), new Point(-57, 10, 30)));

		// Set the camera
		cameraBuilder.setLocation(new Point(0, 0, 1300)) //
				.setVpDistance(1000).setVpSize(200, 200) //
				.setRayTracer(new SimpleRayTracer(scene)) //
				.setImageWriter(new ImageWriter("larvaPictureTest", 1200, 1200)) //
				.setNumOfSamples(3) //
				.build() //
				.renderImage() //
				.writeToImage();
	}

}
