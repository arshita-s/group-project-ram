package starter;

import java.awt.Color;

import acm.graphics.GOval;

public class Enemy {
	private int health; // Health that enemy starts with
	private Size size; // Enemies size
	private Position spawnPoint; //Where enemy spawns
	private Position currentPosition; //Where enemy currently is
	private int damage; // Damage that enemy gives to player
	private int movesWithin;
	private boolean isJumping; 
	private boolean canJump;
	
	private GOval enemy;
	private int dX = 1;
	
	
	/*
	 * Constructor
	 */
	public Enemy(int h, int d, Size s, Position p, int movesW) {
		this.setHealth(h);
		this.setDamage(d);
		this.setSize(s);
		this.movesWithin = movesW;
		this.setSpawnPoint(p);
		this.setCurrentPosition(p);
		this.setJumping(false);
		this.setCanJump(false);
		enemy = new GOval(p.getX(), p.getY(), s.getWidth(), s.getHeight());
	}
	/*
	 * Enemy's movements
	 */
	public void move() {
		if (currentPosition.getX() + dX > (spawnPoint.getX() + movesWithin) || currentPosition.getX() + dX < (spawnPoint.getX() + movesWithin)) {
			dX = dX * -1;
		}
		enemy.move(currentPosition.getX() + dX, currentPosition.getY());
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
		return spawnPoint;
	}

	public void setSpawnPoint(Position sP) {
		this.spawnPoint = sP;
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

}
