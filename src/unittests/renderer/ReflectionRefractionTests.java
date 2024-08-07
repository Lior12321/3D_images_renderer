/**
 * 
 */
package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.Cylinder;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	/** Scene for the tests */
	private final Scene scene = new Scene("Test scene");
	/** Camera builder for the tests with triangles */
	private final Camera.Builder cameraBuilder = Camera.getBuilder().setDirection(Vector.Z, Vector.Y)
			.setRayTracer(new SimpleRayTracer(scene));

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheres() {
		scene.geometries.add(
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add(new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
				.setKl(0.0004).setKq(0.0000006));

		cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(150, 150)
				.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)).build().renderImage().writeToImage();
	}

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheresOnMirrors() {
		scene.geometries.add(
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)).setMaterial(
						new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(new Double3(0.5, 0, 0))),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000))
						.setEmission(new Color(20, 20, 20)).setMaterial(new Material().setKr(1d)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)).setEmission(new Color(20, 20, 20))
						.setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));
		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
				.setKl(0.00001).setKq(0.000005));

		cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000).setVpSize(2500, 2500)
				.setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500)).build().renderImage()
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		scene.geometries.add(
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)).setKl(4E-5)
				.setKq(2E-7));

		cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000).setVpSize(200, 200)
				.setImageWriter(new ImageWriter("refractionShadow", 600, 600)).build().renderImage().writeToImage();
	}

	/**
	 * Produce a picture of a two larvas
	 */
	@Test
	public void ourPictureTest() {
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
		cameraBuilder.setLocation(new Point(0, 0, 1300)).setVpDistance(1000).setVpSize(200, 200)
				.setRayTracer(new SimpleRayTracer(scene));
		cameraBuilder.setImageWriter(new ImageWriter("ourPictureTest", 800, 800)).build().renderImage().writeToImage();
	}
}
