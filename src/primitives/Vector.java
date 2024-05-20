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
		Double3 newD = new Double3(x, y, z);
		if (newD.equals(Double3.ZERO))
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
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return super.toString();
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
		Vector newVec = new Vector(xyz.d1 + vec.xyz.d1, xyz.d2 + vec.xyz.d2, xyz.d3 + vec.xyz.d3);
		return newVec;
	}

	/**
	 * multiply this vector by a scalar factor.
	 * 
	 * @param num The scalar factor by which to scale the vector.
	 * @return A new vector which is the result of scaling this vector by the given
	 *         factor.
	 */
	public Vector scale(double num) {
		Vector newVec = new Vector(xyz.d1 * num, xyz.d2 * num, xyz.d3 * num);
		return newVec;
	}

	/**
	 * Calculate the dot product of this vector with another vector.
	 * 
	 * @param vec The vector to calculate the dot product with.
	 * @return A new vector which is the result of the dot product operation.
	 */
	public double dotProduct(Vector vec) {
		return (double)(xyz.d1 * vec.xyz.d1 + xyz.d2 * vec.xyz.d2 + xyz.d3 * vec.xyz.d3);
	}

	/**
	 * Calculate the cross product of this vector with another vector.
	 * 
	 * @param vec The vector to compute the cross product with.
	 * @return A new vector which is the result of the cross product operation.
	 */
	public Vector crossProduct(Vector vec) {
		double x, y, z;
		x = xyz.d2 * vec.xyz.d3 - xyz.d3 * vec.xyz.d2;
		y = xyz.d3 * vec.xyz.d1 - xyz.d1 * vec.xyz.d3;
		z = xyz.d1 * vec.xyz.d2 - xyz.d2 * vec.xyz.d1;
		return new Vector(x, y, z);
	}

	/**
	 * Returns a new vector that is the normalized form of this vector.
	 * 
	 * @return A new vector that has the same direction as this vector but with a
	 *         length of 1.
	 */
	public Vector normalize() {
		double size = this.length();
		return new Vector(xyz.d1 / size, xyz.d2 / size, xyz.d3 / size);
	}
}
