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
	private boolean visible;
	public Obstacle(int width, int length, boolean move, int x, int y, int horizontal, int vertical, boolean death, String skin, boolean visible) {
		//System.out.format("width: %d length: %d  x: %d y: %d hori: %d vert: %d  skin: |%s|\n", width, length, x, y, horizontal, vertical, skin);
		setSize(new Size(width, length));
		setMovement(move); 
		setSpawnPosition(new Position(x, y));
		setCurrentPosition(new Position(x, y));
		setVelocity(new Velocity(horizontal, vertical));
		setInstantDeath(death);
		setVisible(visible);
		obstacle = new GImage(skin, spawnPosition.getX(), spawnPosition.getY());
		obstacle.setSize(size.getWidth(), size.getHeight());
		obstacle.setVisible(visible);
	}
	
	public void resetLocation() {
		obstacle.setLocation(spawnPosition.getX(), spawnPosition.getY());
		setCurrentPosition(spawnPosition);
	}

	public boolean visible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
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
	
	public GImage getGImage() {
		return obstacle;
	}
}
