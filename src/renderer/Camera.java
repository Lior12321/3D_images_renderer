package renderer;

import primitives.*;

import static primitives.Util.*;
import static java.lang.Math.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

/**
 * Represents a camera with position and direction vectors. The class
 * responsible to send rays and create the picture, (using writePixel). <br>
 * The class also have the improvements: anti-aliasing (SS) and adaptive super
 * sampling (ASS). <br>
 * The class contain inner static class {@link Builder}
 * 
 * @author Lior &amp; Asaf
 */
public class Camera implements Cloneable {
	/** The position of the camera. */
	private Point location;

	/** The up direction vector of the camera. */
	private Vector vUp = null;

	/** The right direction vector of the camera. */
	private Vector vRight = null;

	/** The to direction vector of the camera. */
	private Vector vTo = null;

	/** The width of the view plane. */
	private double width = 0.0;

	/** The height of the view plane. */
	private double height = 0.0;

	/** The distance from the camera to the view plane. */
	private double distance = 0.0;

	/** The image writer for outputting the image. */
	private ImageWriter imageWriter;

	/** The ray tracer for tracing rays in the scene. */
	private RayTracerBase rayTracer;

	/** samples amount per pixel, more samples - better resolution (but slower) */
	private int numOfSamples = 1;

	/** anti-aliasing flag. If the flag is on anti-aliasing is activated */
	private boolean antiAliasingActive = false;

	/** adaptive super sampling flag. If the flag is up ASS is activated */
	private boolean adaptiveSuperSamplingActive = false;

	/** threads counter */
	private int threadsCount = 0; // -2 auto, -1 range/stream, 0 no threads, 1+ number of threads

	/** Spare threads if trying to use all the cores */
	private final int SPARE_THREADS = 2;

	/** printing progress percentage interval */
	private double printInterval = 0;

	/** default constructor */
	private Camera() {
	}

	// getters:
	/**
	 * Gets the position of the camera.
	 * 
	 * @return the position of the camera.
	 */
	public Point getlocation() {
		return location;
	}

	/**
	 * Gets the up direction vector of the camera.
	 * 
	 * @return the up direction vector of the camera.
	 */
	public Vector getVUp() {
		return vUp;
	}

	/**
	 * Gets the right direction vector of the camera.
	 * 
	 * @return the right direction vector of the camera.
	 */
	public Vector getVRight() {
		return vRight;
	}

	/**
	 * Gets the to direction vector of the camera.
	 * 
	 * @return the to direction vector of the camera.
	 */
	public Vector getVTo() {
		return vTo;
	}

	/**
	 * Gets the width of the view plane.
	 * 
	 * @return the width of the view plane.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Gets the height of the view plane.
	 * 
	 * @return the height of the view plane.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Gets the distance from the camera to the view plane.
	 * 
	 * @return the distance from the camera to the view plane.
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Gets the image writer for the camera.
	 * 
	 * @return the image writer for the camera.
	 */
	public ImageWriter getImageWriter() {
		return imageWriter;
	}

	/**
	 * Returns a new instance of the Builder for creating a Camera object.
	 * 
	 * @return Builder
	 */
	public static Builder getBuilder() {
		return new Builder();
	}

	/**
	 * Constructs ray from camera's location to a the center of a given pixel in the
	 * view plane.
	 * 
	 * @param nX number of columns
	 * @param nY number of rows
	 * @param j  current pixel x index
	 * @param i  current pixel y index
	 * @return resulting Ray
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		Point pIJ = location.add(vTo.scale(distance));

		// Calculate distance on x,y axes to the designated point
		double yI = (((nY - 1) / 2.0) - i) * (height / nY);
		double xJ = -(((nX - 1) / 2.0) - j) * (width / nX);

		// Avoiding creation of zero vector (which is unnecessary anyway)
		if (!isZero(xJ))
			pIJ = pIJ.add(vRight.scale(xJ));
		if (!isZero(yI))
			pIJ = pIJ.add(vUp.scale(yI));
		return new Ray(location, pIJ.subtract(location));
	}

	/**
	 * find the pixel in the view plane
	 * 
	 * @param nX number of columns
	 * @param nY number of rows
	 * @param j  current pixel x index
	 * @param i  current pixel y index
	 * @return resulting Ray
	 */
	public Point findPixel(int nX, int nY, int j, int i) {
		Point pIJ = location.add(vTo.scale(distance));

		// Calculate distance on x,y axes to the designated point
		double yI = (((nY - 1) / 2.0) - i) * (height / nY);
		double xJ = -(((nX - 1) / 2.0) - j) * (width / nX);

		// Avoiding creation of zero vector (which is unnecessary anyway)
		if (!isZero(xJ))
			pIJ = pIJ.add(vRight.scale(xJ));
		if (!isZero(yI))
			pIJ = pIJ.add(vUp.scale(yI));
		return pIJ;
	}

	/**
	 * Renders an image by casting rays through every pixel and writing the
	 * calculated color to each pixel using the image writer.
	 * 
	 * @return the camera instance (this) to allow method chaining
	 * @throws MissingResourceException if the image writer or ray tracer is not set
	 */
	public Camera renderImage() {
		int nY = imageWriter.getNy();
		int nX = imageWriter.getNx();
		Pixel.initialize(nY, nX, printInterval);
		for (int i = 0; i < nY; ++i)
			for (int j = 0; j < nX; j++)
				castRay(nX, nY, j, i);
		return this;
		// throw new UnsupportedOperationException();
	}

	/**
	 * Calculate the pixel color and create the pixel using imageWriter according to
	 * the pixel color. there are three options to calculate the color:* <br>
	 * If none of the flags up then send only one ray to the pixel and calculate the
	 * color there (no improvements) <br>
	 * If the anti aliasing flag up then send send numOfSamples rays to the pixel
	 * and calculate the average color for the pixel. <br>
	 * If the adaptive super sampling flag up then check in recursive way how many
	 * places in the pixel needs to change
	 * 
	 * @param nX the number of columns in the resolution
	 * @param nY the number of rows in the resolution
	 * @param j  the column index of the pixel
	 * @param i  the row index of the pixel
	 */
	private void castRay(int nX, int nY, int j, int i) {
		Color finalColor = Color.BLACK;
		// if none of the improvements are active
		// we can just cast the ray and write the color to the pixel.
		if (!antiAliasingActive && !adaptiveSuperSamplingActive) {
			Ray ray = constructRay(nX, nY, j, i);
			finalColor = rayTracer.traceRay(ray);
			imageWriter.writePixel(j, i, finalColor);
			return;
		}
		// if anti aliasing flag is up then send send numOfSamples rays to the pixel
		if (antiAliasingActive) {
			// Loop over the sub-pixels. we use rectangle target area for the sub-pixels.
			for (int subI = 0; subI < numOfSamples; subI++) {
				for (int subJ = 0; subJ < numOfSamples; subJ++) {
					// Adjust the ray for the sub-pixel.
					// each pixel is divided to numOfSamples^2 sub-pixels and we calculate the color
					// for each sub-pixel.
					Ray ray = constructRay(nX * numOfSamples, nY * numOfSamples, j * numOfSamples + subJ,
							i * numOfSamples + subI);
					finalColor = finalColor.add(rayTracer.traceRay(ray));
				}
			}
			finalColor = finalColor.reduce(numOfSamples * numOfSamples);
		}
		// if ASS flag is up active the recursive function to check if need to split the
		// pixel to more sub-pixels
		if (adaptiveSuperSamplingActive) {
			// calculate the color for each sub-pixel
			// the level calculate by the formula: numOfSamples = (2^level) -1.
			// to get the level value we need to do samples -1 and then log2 on the result
			finalColor = castRaysRecursive(nX, nY, j, i, (int) (log(numOfSamples - 1) / log(2)));
		}
		// write the final color to the pixel
		imageWriter.writePixel(j, i, finalColor);
	}

	/**
	 * Draws a grid on the image with specified intervals and color. The method adds
	 * horizontal and vertical lines on the image at the given intervals
	 * 
	 * @param interval the interval between the grid lines.
	 * @param color    the color of the grid lines.
	 * @return the camera instance (this) to allow method chaining
	 * @throws MissingResourceException if there is no image to write on.
	 */
	public Camera printGrid(int interval, Color color) {
		int nY = imageWriter.getNy();
		int nX = imageWriter.getNx();

		// Draw horizontal grid lines
		for (int i = 0; i < nY; i += interval)
			for (int j = 0; j < nX; j += 1)
				imageWriter.writePixel(i, j, color);

		// Draw vertical grid lines
		for (int i = 0; i < nY; i += 1)
			for (int j = 0; j < nX; j += interval)
				imageWriter.writePixel(i, j, color);
		return this;
	}

	/**
	 * Writes pixels to final image to the ImageWriter (using delegating)
	 * 
	 * @throws MissingResourceException if the image writer is not initialized.
	 */
	public void writeToImage() {
		imageWriter.writeToImage();
	}

	/**
	 * Method to calculate all the four corners of a pixel. <br>
	 * insert the points in the order: (-x, +y), (+x, +y), (+x, -y), (-x, -y)
	 * 
	 * @param side   the size of the pixel side
	 * @param center the center of the pixel
	 * @return a list of the four corners of the pixel
	 */
	public List<Point> findCorners(double side, Point center) {
		double centerToSide = side / 2;
		List<Point> points = new LinkedList<>();

		// calculate the vectors for the corners
		Vector posRight = vRight.scale(centerToSide);
		Vector negRight = vRight.scale(-centerToSide);
		Vector posUp = vUp.scale(centerToSide);
		Vector negUp = vUp.scale(-centerToSide);

		points.add(center.add(negRight).add(posUp)); // (-x, +y)
		points.add(center.add(posRight).add(posUp)); // (+x, +y)
		points.add(center.add(posRight).add(negUp)); // (+x, -y)
		points.add(center.add(negRight).add(negUp)); // (-x, -y)
		return points;
	}

	/**
	 * The starting method of the ASS
	 *
	 * @param nX    the number of columns in the resolution
	 * @param nY    the number of rows in the resolution
	 * @param j     for casting ray in that column
	 * @param i     for casting ray in that row
	 * @param level the level of recursion
	 * @return the color of the cell
	 */
	private Color castRaysRecursive(int nX, int nY, int j, int i, int level) {
		Point pIJ = findPixel(nX, nY, j, i);
		double pixelSide = min(height / nY, width / nX) / 2;
		List<Point> pointList = findCorners(pixelSide, pIJ);

		Map<Vector, Color> dictionary = new HashMap<>();
		Point p0 = pointList.getFirst();
		Point p1 = pointList.get(1);
		Point p2 = pointList.get(2);
		Point p3 = pointList.getLast();
		Vector v0 = p0.subtract(location);
		Color color0 = rayTracer.traceRay(new Ray(location, v0));
		dictionary.put(v0, color0);
		Vector v1 = p1.subtract(location);
		Color color1 = rayTracer.traceRay(new Ray(location, v1));
		dictionary.put(v1, color1);
		Vector v2 = p2.subtract(location);
		Color color2 = rayTracer.traceRay(new Ray(location, v2));
		dictionary.put(v2, color2);
		Vector v3 = p3.subtract(location);
		Color color3 = rayTracer.traceRay(new Ray(location, v3));
		dictionary.put(v3, color3);

		if ((color0.equals(color1) && color1.equals(color2) && color3.equals(color0)) || level <= 0)
			return color0.add(color1, color2, color3).reduce(4);
		dictionary = castRaysRecursive(pixelSide, pIJ.newMiddle(p0), level - 1, dictionary);
		dictionary = castRaysRecursive(pixelSide, pIJ.newMiddle(p1), level - 1, dictionary);
		dictionary = castRaysRecursive(pixelSide, pIJ.newMiddle(p2), level - 1, dictionary);
		dictionary = castRaysRecursive(pixelSide, pIJ.newMiddle(p3), level - 1, dictionary);
		Color sumAll = Color.BLACK;
		for (Map.Entry<Vector, Color> vectorColorEntry : dictionary.entrySet())
			sumAll = sumAll.add(vectorColorEntry.getValue());
		return sumAll.reduce(dictionary.size());
	}

	/**
	 * Recursive method for the ASS
	 *
	 * @param side       the size of the new pixel side
	 * @param center     the center of the new pixel
	 * @param level      the level of the recursion
	 * @param dictionary to avoid duplicate calculations
	 * @return the dictionary
	 */
	private Map<Vector, Color> castRaysRecursive(double side, Point center, int level, Map<Vector, Color> dictionary) {
		List<Point> pointList = findCorners(side, center);

		Point p0 = pointList.getFirst();
		Point p1 = pointList.get(1);
		Point p2 = pointList.get(2);
		Point p3 = pointList.getLast();

		Vector v0 = p0.subtract(location);
		Vector v1 = p1.subtract(location);
		Vector v2 = p2.subtract(location);
		Vector v3 = p3.subtract(location);
		Color color0 = dictionary.get(v0), color1 = dictionary.get(v1), color2 = dictionary.get(v2),
				color3 = dictionary.get(v3);

		if (color0 == null) {
			color0 = rayTracer.traceRay(new Ray(location, v0));
			dictionary.put(v0, color0);
		}
		if (color1 == null) {
			color1 = rayTracer.traceRay(new Ray(location, v1));
			dictionary.put(v1, color1);
		}
		if (color2 == null) {
			color2 = rayTracer.traceRay(new Ray(location, v2));
			dictionary.put(v2, color2);
		}
		if (color3 == null) {
			color3 = rayTracer.traceRay(new Ray(location, v3));
			dictionary.put(v3, color3);
		}
		double pixelSide = side / 2;
		if (!(color0.equals(color1) || !color1.equals(color2) || !color3.equals(color0)) && level > 0) {
			dictionary = castRaysRecursive(pixelSide, center.newMiddle(p0), level - 1, dictionary);
			dictionary = castRaysRecursive(pixelSide, center.newMiddle(p1), level - 1, dictionary);
			dictionary = castRaysRecursive(pixelSide, center.newMiddle(p2), level - 1, dictionary);
			dictionary = castRaysRecursive(pixelSide, center.newMiddle(p3), level - 1, dictionary);
		}
		return dictionary;
	}

	/**
	 * Builder inner class for constructing a Camera object.
	 * 
	 * @author Lior &amp; Asaf
	 */
	public static class Builder {
		/** constructing the camera object. */
		private final Camera camera = new Camera();

		/**
		 * Sets the location of the camera in the space.
		 * 
		 * @param p the position of the camera.
		 * @return the Builder instance.
		 */
		public Builder setLocation(Point p) {
			this.camera.location = p;
			return this;
		}

		/**
		 * Sets the direction vectors of the camera.
		 * 
		 * @param vTo the to direction vector of the camera.
		 * @param vUp the up direction vector of the camera.
		 * @return the Builder instance.
		 * @throws IllegalArgumentException if the vectors are not perpendicular.
		 */
		public Builder setDirection(Vector vTo, Vector vUp) {
			if (!isZero(vUp.dotProduct(vTo)))
				throw new IllegalArgumentException("ERROR: the vectors needed to be perpendicular");
			this.camera.vTo = vTo.normalize();
			this.camera.vUp = vUp.normalize();
			return this;
		}

		/**
		 * Sets the view plane size.
		 * 
		 * @param width  the width of the view plane.
		 * @param height the height of the view plane.
		 * @return the Builder instance.
		 * @throws IllegalArgumentException if width or height is not positive.
		 */
		public Builder setVpSize(double width, double height) {
			if (alignZero(width) <= 0 || alignZero(height) <= 0)
				throw new IllegalArgumentException("ERROR: width and height must be positive");
			this.camera.width = width;
			this.camera.height = height;
			return this;
		}

		/**
		 * Sets the distance from the camera to the view plane.
		 * 
		 * @param distance the distance to the view plane.
		 * @return the Builder instance.
		 * @throws IllegalArgumentException if distance is not positive.
		 */
		public Builder setVpDistance(double distance) {
			if (alignZero(distance) <= 0)
				throw new IllegalArgumentException("ERROR: distance must be positive");
			this.camera.distance = distance;
			return this;
		}

		/**
		 * Sets the image writer for the camera.
		 * 
		 * @param imageWriter the image writer.
		 * @return the Builder instance.
		 */
		public Builder setImageWriter(ImageWriter imageWriter) {
			this.camera.imageWriter = imageWriter;
			return this;
		}

		/**
		 * Sets the ray tracer for the camera.
		 * 
		 * @param rayTracer the ray tracer.
		 * @return the Builder instance.
		 */
		public Builder setRayTracer(RayTracerBase rayTracer) {
			this.camera.rayTracer = rayTracer;
			return this;
		}

		/**
		 * Sets the number of samples per pixel
		 * 
		 * @param numOfSamples the number of samples for each pixel
		 * @return the Builder instance.
		 */
		public Builder setNumOfSamples(int numOfSamples) {
			this.camera.numOfSamples = numOfSamples;
			return this;
		}

		/**
		 * Sets the anti-aliasing flag
		 * 
		 * @param antiAliasingActive the anti-aliasing flag
		 * @return the Builder instance.
		 */
		public Builder setAntiAliasingActive(boolean antiAliasingActive) {
			this.camera.antiAliasingActive = antiAliasingActive;
			return this;
		}

		/**
		 * Sets the adaptive super sampling flag
		 * 
		 * @param adaptiveSuperSamplingActive the adaptive super sampling flag
		 * @return the Builder instance.
		 */
		public Builder setAdaptiveSuperSamplingActive(boolean adaptiveSuperSamplingActive) {
			this.camera.adaptiveSuperSamplingActive = adaptiveSuperSamplingActive;
			return this;
		}

		/**
         * sets the multi-threading
         *
         * @param threads the number of threads the user inputed
         * @return the Builder instance.
         */
		public Builder setMultithreading(int threads) {
			if (threads < -2)
				throw new IllegalArgumentException("Multithreading must be -2 or higher");
			if (threads >= -1)
				camera.threadsCount = threads;
			else { // == -2
				int cores = Runtime.getRuntime().availableProcessors() - camera.SPARE_THREADS;
				camera.threadsCount = cores <= 2 ? 1 : cores;
			}
			return this;
		}

		/**
		 * Sets the printing progress percentage interval
		 * @param interval the interval for printing the progress percentage
		 * @return the Builder instance.
		 */
		public Builder setDebugPrint(double interval) {
			camera.printInterval = interval;
			return this;
		}

		/**
		 * Builds and returns the Camera object.
		 * 
		 * @return the built Camera object (clone).
		 * @throws MissingResourceException if any required parameter is missing.
		 * @throws IllegalArgumentException if any parameter value is invalid.
		 */
		public Camera build() {
			String missingRender = "Missing render data";
			String builder = "builder";
			String wrongPlaneValues = "the plane parameters nust be positive";

			// View Plane data checks
			if (isZero(camera.height))
				throw new MissingResourceException(missingRender, builder, "height = 0");
			if (isZero(camera.width))
				throw new MissingResourceException(missingRender, builder, "width = 0");
			if (isZero(camera.distance))
				throw new MissingResourceException(missingRender, builder, "distance = 0");
			if (camera.height < 0 || camera.width < 0 || camera.distance < 0)
				throw new IllegalArgumentException(wrongPlaneValues);

			// Camera location and direction data checks
			if (camera.location == null)
				throw new MissingResourceException(missingRender, builder, "camera location not defined");
			if (camera.vTo == null)
				throw new MissingResourceException(missingRender, builder, "to direction vector not defined");
			if (camera.vUp == null)
				throw new MissingResourceException(missingRender, builder, "up direction vector not defined");
			if (!isZero(camera.vUp.dotProduct(camera.vTo)))
				throw new MissingResourceException(missingRender, builder, "to and up directions are not orthogonal");
			this.camera.vRight = this.camera.vTo.crossProduct(this.camera.vUp).normalize();

			// we must have at least 1 sample for pixel - the pixel itself
			if (camera.numOfSamples < 1)
				throw new IllegalArgumentException(wrongPlaneValues);
			// if we have both anti-aliasing and adaptive super sampling
			if (camera.antiAliasingActive && camera.adaptiveSuperSamplingActive)
				throw new IllegalArgumentException("Can't have both anti-aliasing and adaptive super sampling");

			// Helper objects checks
			if (camera.imageWriter == null)
				throw new MissingResourceException(missingRender, builder, "imageWriter not defined");
			if (camera.rayTracer == null)
				throw new MissingResourceException(missingRender, builder, "rayTracer not defined");
			// Exceptions for threads already thrown in the set function (there is default value)
			
			// we don't really need to do try because we checks it all, but the compiler
			// doesn't give us to do it without try-catch form
			try {
				return (Camera) camera.clone();
			} catch (CloneNotSupportedException e) {
				throw new IllegalArgumentException("Can't create clone of camera");
			}
		}
	}

}
