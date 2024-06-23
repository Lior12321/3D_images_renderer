package unittests.renderer;

import org.junit.jupiter.api.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 * Unit test for render.ImageWriter class
 * 
 * @author Lior &amp; Asaf
 */
class ImageWriterTest {

	/**
	 * Test method for TODO: add javadoc
	 */
	@Test
	void makeImage() {
		ImageWriter imageWriter = new ImageWriter("createTestPicture", 801, 501);
		Color background = new Color(255, 255, 0);
		Color grid = new Color(255, 0, 0);

		// Separately colors each pixel based on the ray tracer's findings
		for (int i = 0; i <= 500; i++) {
			for (int j = 0; j <= 800; j++) {
				// every square is 50*50
				if (i % 50 == 0 || j % 50 == 0)
					imageWriter.writePixel(j, i, grid);
				else
					imageWriter.writePixel(j, i, background);
			}
		}
		imageWriter.writeToImage();

	}
}
