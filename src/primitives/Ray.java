package primitives;

/**
 * Represents a ray in 3D space. A ray is defined by a starting point (head) and
 * a direction vector.
 * 
 * @author Lior &amp; Asaf
 */
public class Ray {

	/**
	 * The starting point of the ray.
	 */
	private final Point head;

	/**
	 * The direction vector of the ray.
	 */
	protected final Vector direction;

	/**
	 * Constructs a new Ray with the specified head and direction.
	 *
	 * @param head      the starting point of the ray
	 * @param direction the direction vector of the ray
	 */
	public Ray(Point head, Vector direction) {
		this.head = head;
		this.direction = direction.normalize();
	}

	// getters:

	/**
	 * Returns the direction vector of the ray.
	 * 
	 * @return the direction vector of the ray.
	 */
	public Vector getDir() {
		return direction;
	}

	/**
	 * Returns the starting point of the ray.
	 *
	 * @return the starting point of the ray
	 */
	public Point getHead() {
		return head;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
	}

	@Override
	public String toString() {
		return head + "->" + direction;
	}

	/**
	 * get Point in specific distance in the direction of the ray
	 * 
	 * @param t scalar. the distance to the new Point
	 * @return the wanted point
	 */
	public Point getPoint(double t) {
		return head.add(direction.scale(t));
	}
}
