package starter;

import java.awt.Color;

import acm.graphics.GImage;

public class Enemy {
	private static final double DX = .6;
	private int spawnHealth;
	private int health; // Health that enemy starts with
	private Size size; // Enemies size
	private Position spawnPosition; //Where enemy spawns
	private Position currentPosition; //Where enemy currently is
	private int damage; // Damage that enemy gives to player
	private int movesWithin;
	private boolean isJumping; 
	private boolean canJump;
	
	private static final String DIRECTORY = System.getProperty("user.dir") + "/EnemyAnimations/";
	private static final String skin = DIRECTORY + "enemy_walk_left_1.png";
	private GImage enemy;
	private double dX;
	private String[] animationWalkRight;
	private double animationWalkRightFrame = 0;
	private String[] animationWalkLeft;
	private double animationWalkLeftFrame = 0;
	private double animationSpeed = DX;
	private int walkFrames = 6;
	
	/*
	 * Constructor
	 */
	public Enemy(int h, int d, Size s, Position p, int movesW) {
		this.setDamage(d);
		this.setSize(s);
		this.movesWithin = movesW;
		this.setSpawnPoint(p);
		this.setCurrentPosition(p);
		this.setJumping(false);
		this.setCanJump(false);
		this.setdX(DX);
		
		setWalkingAnimation();
		enemy = new GImage(skin, p.getX(), p.getY());
		enemy.setSize(s.getWidth(), s.getHeight());
	}
	/*
	 * Animations
	 */
	private void setWalkingAnimation() {
		int totalImages = walkFrames;
		animationWalkRight = new String[totalImages];	
		animationWalkLeft = new String[totalImages];	
		for (int i = 0; i < totalImages; i++) {
			animationWalkRight[i] = DIRECTORY + "enemy_walk_right_" + Integer.toString(i+1) + ".png";
		}
		for (int i = 0; i < totalImages; i++) {
			animationWalkLeft[i] = DIRECTORY + "enemy_walk_left_" + Integer.toString(i+1) + ".png";
		}
	}
	public void EnemyWalkRightNextFrame() {
		if(dX > 0) {
			animationWalkRightFrame+=animationSpeed/8;
			enemy.setImage(animationWalkRight[(int)(animationWalkRightFrame%walkFrames)]);
			enemy.setSize(enemy.getWidth(), size.getHeight());
		}
	}
	public void EnemyWalkLeftNextFrame() {
		if(dX < 0) {
			animationWalkLeftFrame+=animationSpeed/8;
			enemy.setImage(animationWalkLeft[(int)(animationWalkLeftFrame%walkFrames)]);
			enemy.setSize(enemy.getWidth(), size.getHeight());
		}
	}
	public void enemyAnimation() {
		EnemyWalkLeftNextFrame();
		EnemyWalkRightNextFrame();
	}
	/*
	 * Enemy's movements
	 */ 
	public void move() {
		Position newPos = spawnPosition;
		/*if(spawnPosition.getX() > 800 && currentPosition.getX() < 800) {
			newPos.setX(spawnPosition.getX() - 800);
		}*/
		if (currentPosition.getX() + dX > (newPos.getX() + movesWithin) || currentPosition.getX() + dX < (newPos.getX() - movesWithin)) {
			setdX(getdX() * -1);
		}
		enemy.move(getdX(), 0);
		enemyAnimation();
	}
	
	public void resetPosition() {
		enemy.setLocation(spawnPosition.getX(), spawnPosition.getY());
		setCurrentPosition(spawnPosition);
	}
	
	/*
	 * Setters and Getters below
	 */
	
	public int getHealth() {
		return health;
	}

	public int getSpawnHealth() {
		return spawnHealth;
	}
	public void setSpawnHealth(int spawnHealth) {
		this.spawnHealth = spawnHealth;
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
	public double getdX() {
		return dX;
	}
	public void setdX(double d) {
		this.dX = d;
	}
	public GImage getSkin() {
		return enemy;
	}
	
}
