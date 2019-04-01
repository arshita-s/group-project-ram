package starter;

public class Enemy {
	private int health; // Health that enemy starts with
	private Size size; // Enemies size
	private Position position; //Where enemy spawns
	private Position currentPosition; //Where enemy currently is
	private int damage; // Damage that enemy gives to player
	private int movesWithin;
	private boolean isJumping; 
	private boolean canJump;
	
	private int dX = 1;
	
	
	/*
	 * Constructor
	 */
	public Enemy(int h, int d, Size s, Position p, int movesW) {
		this.setHealth(h);
		this.setDamage(d);
		this.setSize(s);
		this.movesWithin = movesW;
		this.setPosition(p);
		this.setJumping(false);
		this.setCanJump(false);
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
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

}
