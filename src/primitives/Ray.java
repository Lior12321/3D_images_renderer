package primitives;

/**
 * Represents a ray in 3D space.
 * A ray is defined by a starting point (head) and a direction vector.
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
     * @param head 		the starting point of the ray
     * @param direction the direction vector of the ray
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
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
}
