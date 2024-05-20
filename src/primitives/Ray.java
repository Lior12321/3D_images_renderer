package primitives;

/**
 * class for a ray
 */
public class Ray {
	private Point head; // the head of the ray
	private Vector direction; // the direction of the ray

	/**
	 * constructor with parameters
	 * @param head the head of the new ray
	 * @param direction the direction of the new ray
	 */
	public Ray(Point head, Vector direction) {
		this.head = head;
		this.direction = direction;
	}

	/**
	 * check if two rays are equal
	 * @param obj the ray we check if it's equal to the ray which called the function
	 * @return true/false determined by the equality of the two rays
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
	}

	/**
	 * giving a string which represents the ray
	 * @return a string which represents the ray
	 */
	@Override
	public String toString() {
		return head + "->" + direction;
	}
}
