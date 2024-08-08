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

	/**
	 * Test method for {@link renderer.ImageWriter#writeToImage()}.
	 */
	@Test
	void writeToImageTest() {
		final int x = 800;
		final int y = 500;
		final int step = 50;
		final ImageWriter imageWriter = new ImageWriter("createTestPicture", x + 1, y + 1);
		final Color background = new Color(255, 255, 0);
		final Color grid = new Color(255, 0, 0);

		// Separately colors each pixel based on the ray tracer's findings
		for (int i = 0; i <= y; i++)
			for (int j = 0; j <= x; j++)
				// every square is 50*50
				imageWriter.writePixel(j, i, i % step == 0 || j % step == 0 ? grid : background);

		imageWriter.writeToImage();

	}
}
