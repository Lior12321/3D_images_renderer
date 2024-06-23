package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

import java.util.MissingResourceException;

/**
 * Represents a camera with position and direction vectors.
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
		double yI = -(((nY - 1) / 2.0) - i) * (height / nY);
		double xJ = (((nX - 1) / 2.0) - j) * (width / nX);

		// Avoiding creation of zero vector (which is unnecessary anyway)
		if (!isZero(xJ))
			pIJ = pIJ.add(vRight.scale(xJ));
		if (!isZero(yI))
			pIJ = pIJ.add(vUp.scale(yI));
		return new Ray(location, pIJ.subtract(location));
	}

	public void renderImage() {
		/**
		 * TODO:add implement
		 */
		throw new UnsupportedOperationException();
	}

	/**
	 * Draws a grid on the image with specified intervals and color. The method adds
	 * horizontal and vertical lines on the image at the given intervals
	 * 
	 * @param interval the interval between the grid lines.
	 * @param color    the color of the grid lines.
	 * @throws MissingResourceException if there is no image to write on.
	 */
	public void printGrid(int interval, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException("There isn't image to write on", getClass().getName(), "");
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
	}

	/**
	 * Builder class for constructing a Camera object.
	 * 
	 * @author Lior &amp; Asaf
	 */
	public static class Builder {
		/** constructing the camera object. */
		private final Camera camera = new Camera();

		/**
		 * Gets the camera object.
		 * 
		 * @return the camera object.
		 */
		public Camera getCamera() {
			return camera;
		}

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
			if (isZero(alignZero(camera.height)))
				throw new MissingResourceException(missingRender, builder, "height = 0");
			if (isZero(alignZero(camera.width)))
				throw new MissingResourceException(missingRender, builder, "width = 0");
			if (isZero(alignZero(camera.distance)))
				throw new MissingResourceException(missingRender, builder, "distance = 0");
			if (camera.location == null)
				throw new MissingResourceException(missingRender, builder, "camera location not defined");
			if (camera.vTo == null)
				throw new MissingResourceException(missingRender, builder, "to direction vector not defined");
			if (camera.vUp == null)
				throw new MissingResourceException(missingRender, builder, "up direction vector not defined");
			if (camera.height < 0 || camera.width < 0 || camera.distance < 0)
				throw new IllegalArgumentException(wrongPlaneValues);
			if (camera.imageWriter == null)
				throw new MissingResourceException(missingRender, builder, "imageWriter not defined");
			if (camera.rayTracer == null)
				throw new MissingResourceException(missingRender, builder, "rayTracer not defined");
			this.camera.vRight = this.camera.vTo.crossProduct(this.camera.vUp).normalize();

			try {
				return (Camera) camera.clone();
			} catch (CloneNotSupportedException e) {
				throw new IllegalArgumentException("Can't create clone of camera");
			}

		}

	}

}
