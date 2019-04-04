package starter;

public class Position {
	private double x;
	private double y;
	
	public static void main(String[] args) {
		
		System.out.println("  Position class\n -------------");
		Position one = new Position(-1, 0);
		Position two = new Position(4, 2);
		two.setX(two.getX()+1);
		two.setY(two.getY()+2);
		System.out.println("one x: " + one.getX() + ", y: " + one.getY());
		System.out.println("two x: " + two.getX() + ", y: "  + two.getY());
		
	}
	
	public Position(double x, double y) {
		if(x < 0) x *= -1;
		if(x == 0) x = 1;
		if(y < 0) y *= -1;
		if(y == 0) y = 1;
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		if(x < 0) x *= -1;
		if(x == 0) x = 1;
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		if(y < 0) y *= -1;
		if(y == 0) y = 1;
		this.y = y;
	}

}
