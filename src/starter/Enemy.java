package starter;

import java.awt.Color;

import acm.graphics.GImage;

public class Enemy {
	private int health; // Health that enemy starts with
	private Size size; // Enemies size
	private Position spawnPosition; //Where enemy spawns
	private Position currentPosition; //Where enemy currently is
	private int damage; // Damage that enemy gives to player
	private int movesWithin;
	private boolean isJumping; 
	private boolean canJump;
	
	private GImage enemy;
	private final String skin = "enemy.png";
	private int dX;
	
	
	/*
	 * Constructor
	 */
	public Enemy(int h, int d, Size s, Position p, int movesW) {
		this.setHealth(h);
		this.setDamage(d);
		this.setSize(s);
		this.movesWithin = movesW;
		this.setSpawnPoint(p);
		this.setCurrentPosition(new Position(p.getX(), p.getY()));
		this.setJumping(false);
		this.setCanJump(false);
		this.setdX(1);
		enemy = new GImage(skin, p.getX(), p.getY());
		enemy.setSize(s.getWidth(), s.getHeight());
	}
	/*
	 * Enemy's movements
	 */ 
	public void move() {
		if (currentPosition.getX() + dX > (spawnPosition.getX() + movesWithin) || currentPosition.getX() + dX < (spawnPosition.getX() - movesWithin)) {
			setdX(getdX() * -1);
		}
		currentPosition.setX(currentPosition.getX() + getdX());
		
	} 
	
	/*
	 * Setters and Getters below
	 */
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public Position getSpawnPoint() {
		return spawnPosition;
	}

	public void setSpawnPoint(Position sP) {
		this.spawnPosition = sP;
	}

	public boolean isCanJump() {
		return canJump;
	}

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getMovesWithin() {
		return movesWithin;
	}

	public void setMovesWithin(int movesWithin) {
		this.movesWithin = movesWithin;
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}
	public int getdX() {
		return dX;
	}
	public void setdX(int dX) {
		this.dX = dX;
	}
	public GImage getSkin() {
		return enemy;
	}
}
