package primitives;

public class Ray {
	private Point head;
	private Vector direction;
	
	public Ray(Point head, Vector direction) {
		this.head = head;
		this.direction = direction;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
            return true;
		return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
		
	}
	
	
	@Override
    public String toString() {
		return "starting point " + head + " with the direction of" + direction;
		//return  "starting point " + head.toString() + " with the direction of" + direction.toString();
	}
	}
