package starter;
import java.awt.Color;

import acm.graphics.GImage;

public class Obstacle {
	private Size size;
	private boolean movement;
	private Position spawnPosition;
	private Position currentPosition;
	private Velocity velocity;
	private boolean instantDeath;
	private GImage obstacle;
	private final String[] skin = {"stoneTexture1x1.png", "stoneTexture1x6.png", "stoneTexture1x12.png", "brickGrass1x1.png", "brickGrass1x6.png", "brickGrass1x12.png"};
	
	public Obstacle(int width, int length, boolean move, int x, int y, int horizontal, int vertical, boolean death) {
		setSize(new Size(width, length));
		setMovement(move); 
		setSpawnPosition(new Position(x, y));
		setCurrentPosition(new Position(x, y));
		setVelocity(new Velocity(horizontal, vertical));
		setInstantDeath(death);
		setGImage();
	}

	public boolean isInstantDeath() {
		return instantDeath;
	}

	public void setInstantDeath(boolean instantDeath) {
		this.instantDeath = instantDeath;
	}

	public Velocity getVelocity() {
		return velocity;
	}

	public void setVelocity(Velocity velocity) {
		this.velocity = velocity;
	}

	public boolean isMovement() {
		return movement;
	}

	public void setMovement(boolean movement) {
		this.movement = movement;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}

	public Position getSpawnPosition() {
		return spawnPosition;
	}

	public void setSpawnPosition(Position spawnPosition) {
		this.spawnPosition = spawnPosition;
	}
	public void setGImage() {
		int i = 0 ;
		if (getSize().getWidth() <= 600) i = 2;
		if (getSize().getWidth() <= 300) i = 1;
		if (getSize().getWidth() <= 50) i = 0;
		
			
		obstacle = new GImage(skin[i+0], spawnPosition.getX(), spawnPosition.getY());
		obstacle.setSize(size.getWidth(), size.getHeight());
	}
	public GImage getGImage() {
		return obstacle;
	}
}
