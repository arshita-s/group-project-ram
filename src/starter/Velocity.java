package starter;

public class Velocity {
	private int horizontal;
	private int vertical;
	
	public static void main(String[] args) {
		
		System.out.println("  Velocity class\n -------------");
		Velocity one = new Velocity(-1, 6);
		Velocity two = new Velocity(4, 2);
		two.setHorizontal(two.getHorizontal()+1);
		two.setVertical(two.getVertical()+2);
		System.out.println("one Horizontal: " + one.getHorizontal() + ", Vertical: " + one.getVertical());
		System.out.println("two Horizontal: " + two.getVertical() + ", Vertical: "  + two.getVertical());
		
	}
	
	public Velocity(int horizontal, int vertical) {
		this.horizontal = horizontal;
		this.vertical = vertical;
	}
	public int getHorizontal() {
		return horizontal;
	}
	public void setHorizontal(int horizontal) {
		this.horizontal = horizontal;
	}
	public int getVertical() {
		return vertical;
	}
	public void setVertical(int vertical) {
		this.vertical = vertical;
	}
	
}
