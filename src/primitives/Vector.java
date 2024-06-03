package primitives;

/**
 * The Vector class represents a vector in 3D space, defined by its coordinates.
 * It extends the Point class and adds vector-specific operations such as vector
 * addition, scaling, dot product, cross product, and normalization.
 * 
 * @author Lior &amp; Asaf
 */
public class Vector extends Point {

	/**
	 * Constructs a new Vector with the specified coordinates.
	 *
	 * @param x The x-coordinate of the vector.
	 * @param y The y-coordinate of the vector.
	 * @param z The z-coordinate of the vector.
	 * @throws IllegalArgumentException if the input vector is a zero vector.
	 */
	public Vector(double x, double y, double z) {
		super(x, y, z);
		if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("You enterd a zero vector!");
	}

	/**
	 * Constructs a new Vector from a Double3 object.
	 *
	 * @param newD The Double3 object representing the vector.
	 * @throws IllegalArgumentException if the input vector is a zero vector.
	 */
	public Vector(Double3 newD) {
		super(newD);
		if (newD.equals(Double3.ZERO))
			throw new IllegalArgumentException("You enterd a zero vector!");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Vector other) && this.xyz.equals(other.xyz);
	}

	/**
	 * giving a string which represents the vector
	 * 
	 * @return a string which represents the vector
	 */
	@Override
	public String toString() {
		return "vec" + super.toString();
	}

	/**
	 * Calculates the squared length of this vector.
	 * 
	 * @return The squared length of this vector.
	 */
	public double lengthSquared() {
		return (xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3);
	}

	/**
	 * Calculates the length of this vector.
	 * 
	 * @return The length of this vector.
	 */
	public double length() {
		return Math.sqrt(this.lengthSquared());
	}

	/**
	 * Adds another vector to this vector and returns the result as a new vector.
	 * 
	 * @param vec The vector to be added to this vector.
	 * @return A new vector which is the result of adding the given vector to this
	 *         vector.
	 */
	public Vector add(Vector vec) {
		return new Vector(xyz.d1 + vec.xyz.d1, xyz.d2 + vec.xyz.d2, xyz.d3 + vec.xyz.d3);
	}

	/**
	 * multiply this vector by a scalar factor.
	 * 
	 * @param num The scalar factor by which to scale the vector.
	 * @return A new vector which is the result of scaling this vector by the given
	 *         factor.
	 */
	public Vector scale(double num) {
		return new Vector(xyz.d1 * num, xyz.d2 * num, xyz.d3 * num);
	}

	/**
	 * Calculate the dot product of this vector with another vector.
	 * 
	 * @param vec The vector to calculate the dot product with.
	 * @return A new vector which is the result of the dot product operation.
	 */
	public double dotProduct(Vector vec) {
		return (xyz.d1 * vec.xyz.d1 + xyz.d2 * vec.xyz.d2 + xyz.d3 * vec.xyz.d3);
	}

	/**
	 * Calculate the cross product of this vector with another vector.
	 * 
	 * @param vec The vector to compute the cross product with.
	 * @return A new vector which is the result of the cross product operation.
	 */
	public Vector crossProduct(Vector vec) {
		return new Vector(//
				xyz.d2 * vec.xyz.d3 - xyz.d3 * vec.xyz.d2, //
				xyz.d3 * vec.xyz.d1 - xyz.d1 * vec.xyz.d3, //
				xyz.d1 * vec.xyz.d2 - xyz.d2 * vec.xyz.d1);
	}

	/**
	 * Returns a new vector that is the normalized form of this vector.
	 * 
	 * @return A new vector that has the same direction as this vector but with a
	 *         length of 1.
	 */
	public Vector normalize() {
		return scale(1 / length());
	}
}
