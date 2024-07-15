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
	 * Test method for creating an image with a grid pattern. The method creates an
	 * image with a yellow background and red grid.
	 */

	/** final value of the resolution - width (x axis) */
	private final int x = 800;
	/** final value of the resolution - height (y axis) */
	private final int y = 500;

	/**
	 * Test method for {@link renderer.ImageWriter#writeToImage()}.
	 */
	@Test
	void writeToImageTest() {
		ImageWriter imageWriter = new ImageWriter("createTestPicture", x + 1, y + 1);
		Color background = new Color(255, 255, 0);
		Color grid = new Color(255, 0, 0);

		// Separately colors each pixel based on the ray tracer's findings
		for (int i = 0; i <= y; i++) {
			for (int j = 0; j <= x; j++) {
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
