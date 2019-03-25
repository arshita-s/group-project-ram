package starter;

public class Obstacle {
	Size size;
	boolean movement;
	Position position;
	Velocity velocity;
	boolean instantDeath;
	
	public Obstacle(int width, int length, boolean move, int x, int y, int horizontal, int vertical, boolean death) {
		size = new Size(width, length);
		movement = move;
		position = new Position(x, y);
		velocity = new Velocity(horizontal, vertical);
		instantDeath = death;
	}
}
