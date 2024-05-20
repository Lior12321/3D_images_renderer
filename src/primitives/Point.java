package primitives;

/**
 * The Point class represents a point in three-dimensional space.
 * @author Lior &amp; Asaf
 */
public class Point {
    protected Double3 xyz;
	public static final Point ZERO = new Point(0, 0, 0);


    /**
     * Constructs a new Point with the specified coordinates.
     * @param xyz The coordinates of the point.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Constructs a new Point with the specified coordinates.
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
    public Point(double x, double y, double z) {
          this.xyz=new Double3(x,y,z);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
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
     * @return A string representation of this Point.
     */
    @Override
    public String toString() {
        return xyz.toString();
    }

    /**
     * Adds a vector to this point and returns the result as a new point.
     * @param vec The vector to add.
     * @return A new point which is the result of adding the vector to this point.
     */
    public Point add(primitives.Vector vec) {
        double x, y, z;
        x = xyz.d1 + vec.xyz.d1;
        y = xyz.d2 + vec.xyz.d2;
        z = xyz.d3 + vec.xyz.d3;
        Double3 newD = new Double3(x, y, z);
        Point newP = new Point(newD);
        return newP;
    }

    /**
     * Subtracts another point from this point and returns the result as a new vector.
     * @param P The point to subtract.
     * @return A new vector which is the result of subtracting the given point from this point.
     */
    public primitives.Vector subtract(Point P) {
        double x, y, z;
        x = xyz.d1 - P.xyz.d1;
        y = xyz.d2 - P.xyz.d2;
        z = xyz.d3 - P.xyz.d3;
        Double3 newV = new Double3(x, y, z);
        primitives.Vector vec = new primitives.Vector(newV);
        return vec;
    }

    /**
     * Computes the squared distance between this point and another point.
     * @param P The other point.
     * @return The squared distance between this point and the other point.
     */
    public double distanceSquared(Point P) {
        double x, y, z;
        x = (xyz.d1 - P.xyz.d1) * (xyz.d1 - P.xyz.d1);
        y = (xyz.d2 - P.xyz.d2) * (xyz.d2 - P.xyz.d2);
        z = (xyz.d3 - P.xyz.d3) * (xyz.d3 - P.xyz.d3);
        return x + y + z;
    }

    /**
     * Computes the distance between this point and another point.
     * @param P The other point.
     * @return The distance between this point and the other point.
     */
    public double distance(Point P) {
        return Math.sqrt(this.distanceSquared(P));
    }
}
