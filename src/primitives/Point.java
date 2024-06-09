package primitives;

/**
 * The Point class represents a point in three-dimensional space.
 * 
 * @author Lior &amp; Asaf
 */
public class Point {
	/**
	 * The coordinates of the point in 3D space. This is represented by a Double3
	 * object, which contains the x, y, z coordinates.
	 */
	protected final Double3 xyz;

	/**
	 * A constant representing the origin point (0, 0, 0) in 3D space.
	 */
	public static final Point ZERO = new Point(0, 0, 0);

	/**
	 * Constructs a new Point with the specified coordinates.
	 * 
	 * @param xyz The coordinates of the point.
	 */
	public Point(Double3 xyz) {
		this.xyz = xyz;
	}

	/**
	 * Constructs a new Point with the specified coordinates.
	 * 
	 * @param x The x-coordinate of the point.
	 * @param y The y-coordinate of the point.
	 * @param z The z-coordinate of the point.
	 */
	public Point(double x, double y, double z) {
		this.xyz = new Double3(x, y, z);
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param obj The reference object with which to compare.
	 * @return true if this object is the same as the obj argument; false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Point other) && this.xyz.equals(other.xyz);
	}

	/**
	 * Returns a string representation of this Point.
	 * 
	 * @return A string representation of this Point.
	 */
	@Override
	public String toString() {
		return "" + xyz;
	}

	/**
	 * Adds a vector to this point and returns the result as a new point.
	 * 
	 * @param vec The vector to add.
	 * @return A new point which is the result of adding the vector to this point.
	 */
	public Point add(primitives.Vector vec) {
		return new Point( //
				xyz.d1 + vec.xyz.d1, //
				xyz.d2 + vec.xyz.d2, //
				xyz.d3 + vec.xyz.d3);
	}

	/**
	 * Subtracts another point from this point and returns the result as a new
	 * vector.
	 * 
	 * @param P The point to subtract.
	 * @return A new vector which is the result of subtracting the given point from
	 *         this point.
	 */
	public Vector subtract(Point p) {
		return new Vector(xyz.d1 - p.xyz.d1, xyz.d2 - p.xyz.d2, xyz.d3 - p.xyz.d3);
	}

	/**
	 * Computes the squared distance between this point and another point.
	 * 
	 * @param P The other point.
	 * @return The squared distance between this point and the other point.
	 */
	public double distanceSquared(Point p) {
		double dx = xyz.d1 - p.xyz.d1;
		double dy = xyz.d2 - p.xyz.d2;
		double dz = xyz.d3 - p.xyz.d3;
		return dx * dx + dy * dy + dz * dz;
	}

	/**
	 * Computes the distance between this point and another point.
	 * 
	 * @param P The other point.
	 * @return The distance between this point and the other point.
	 */
	public double distance(Point p) {
		return Math.sqrt(this.distanceSquared(p));
	}

	// getters:
	/**
	 * Returns the X coordinate.
	 *
	 * @return the X coordinate as a double.
	 */
	public double getX() {
		return xyz.d1;
	}

	/**
	 * Returns the Y coordinate.
	 *
	 * @return the Y coordinate as a double.
	 */
	public double getY() {
		return xyz.d2;
	}

	/**
	 * Returns the Z coordinate.
	 *
	 * @return the Z coordinate as a double.
	 */
	public double getZ() {
		return xyz.d3;
	}
}
