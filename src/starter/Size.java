package starter;

public class Size {
	private int width;
	private int height;
	
	public static void main(String[] args) {
		
		System.out.println("  Size class\n -------------");
		Size one = new Size(3, 4);
		Size two = new Size(1, 6);
		two.setWidth(two.getWidth()+1);
		two.setHeight(two.getHeight()-1);
		System.out.println("one w: " + one.getWidth() + ", h: " + one.getHeight());
		System.out.println("two w: " + two.getHeight() + ", h: "  + two.getHeight());
		
	}
	
	public Size(int width, int height) {
		if(width < 0) width *= -1;
		if(width == 0) width = 1;
		if(height < 0) height *= -1;
		if(height == 0) height *= 1;
		this.width = width;
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		if(width < 0) width *= -1;
		if(width == 0) width = 1;
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		if(height < 0) height *= -1;
		if(height == 0) height *= 1;
		this.height = height;
	}
}
