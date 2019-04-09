package starter;

import java.awt.Color;

import acm.graphics.GOval;

public class Player {
	/* 
	 * For what is below, I suggest not having them "final"
	 * because of how we will be changing them depending 
	 * the power-up used.
	*/
	private static double MAX_SPEED = 2;
	private static double SPEED_DX = .4;
	private static double SPEED_DY = .4;
	private static int MAX_GRAVITY = 10;
	private static double MAX_JUMP = 5;
	private static int GROUND = 750;
	private static final int PLAYER_SIZE_Y = 50;
	private double speedX;
	private double speedY;
	private boolean jumped;
	private Position currentPosition;
	private GOval player;
	private PlayerMovement currentMove;
	private PlayerJump currentJump;
	private Position lastPos;
	private int lives;
	private int health;
	private int powerUps;

	
	/*
	 * Constructor
	 */
	public Player(double x, double y) {
		currentPosition = new Position(x, y);
		player = new GOval(currentPosition.getX(), currentPosition.getY(), 50, PLAYER_SIZE_Y);
		speedX = 0;
		speedY = 0;
		jumped = false;
		currentMove = PlayerMovement.STANDING;
		currentJump = PlayerJump.STAND;
		lives = 3;
		health = 30;
		powerUps = 0;
	}

	public int getLives() {
		return lives;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setLives(int l) {
		lives = 3;
	}
	
	public void setHealth(int h) {
		health = h;
	}
	
	public int getPowerUps() {
		return powerUps;
	}
	
	public void setPowerUps(int p) {
		powerUps = p;
	}
	
	public void incrementLives() {
		lives++;
	}
	
	public void decrementLives() {
		lives--;
	}
	
	public Position getLastPos() {
		return lastPos;
	}
	
	public void setLastPos(Position p) {
		lastPos = p;
	}
	
	public void setPosition(Position p) {
		currentPosition = p;
	}
	
	public void addForce() {
		if(jumped) {
			currentJump = PlayerJump.STAND;
		}
		if (currentJump == PlayerJump.JUMP && !jumped) {
			speedY = speedY - SPEED_DY;
			if(speedY <= -MAX_JUMP) {
				jumped = true;
			}
			lastPos = new Position(player.getX(), player.getY());
		}
		if (currentMove == PlayerMovement.RIGHT && speedX < MAX_SPEED) {
			speedX = Math.min(speedX + SPEED_DX, MAX_SPEED);
			lastPos = new Position(player.getX(), player.getY());
		} else if (currentMove == PlayerMovement.LEFT && -MAX_SPEED < speedX) {
			speedX = Math.max(speedX - SPEED_DX, -MAX_SPEED);
			lastPos = new Position(player.getX(), player.getY());
		}
	}
		
	public void addFriction() {
		if (currentMove == PlayerMovement.STANDING && speedX < 0) {
			speedX = Math.min(0, speedX + SPEED_DX / 2);
		} else if (currentMove == PlayerMovement.STANDING && 0 < speedX) {
			speedX = Math.max(0, speedX - SPEED_DX / 2);
		}
	}

	public void processGravity() {
		
		if (currentJump == PlayerJump.STAND || speedY < -MAX_JUMP) {
			if (player.getY() + player.getHeight() <= GROUND - 1) {
				speedY = Math.min(speedY + SPEED_DY / 3, MAX_GRAVITY);
			} else {
				speedY = 0;
				player.setLocation(player.getX(), GROUND - player.getHeight());
				jumped = false;
			}
		
		}
		
		/*if (currentJump == PlayerJump.STAND || speedY < -MAX_GRAVITY) {
			if (player.getY() + player.getHeight() <= GROUND - 1) {
				speedY = Math.min(speedY + SPEED_DY / 3, MAX_GRAVITY);
			} else {
				speedY = 0;
				player.setLocation(player.getX(), GROUND - player.getHeight());
				jumped = false;
			}
		}*/
		/*
		 * if(speedY < MAX_GRAVITY) { speedY -= SPEED_DY; } else if(speedY >
		 * MAX_GRAVITY) { speedY = MAX_GRAVITY; }
		 */
	}

	public void processFalling() {
		if (player.getY() < GROUND) {
			speedY += SPEED_DY;
		}
	}

	public void processImage() {

	}
	
	/*
	 * Setters and Getters below.
	 */
	public PlayerJump getJump() {
		return currentJump;
	}
	
	public PlayerMovement getCurrent() {
		return currentMove;
	}
	
	public GOval getGOval() {
		return player;
	}

	public Position getPosition() {
		return currentPosition;
	}

	public void setCurrentMove(PlayerMovement current) {
		this.currentMove = current;
	}

	public void setCurrentJump(PlayerJump current) {
		this.currentJump = current;
	}

	public void move() {
		player.move(speedX, speedY);
	}

	public void setSpeedX(int sX) {
		speedX = sX;
	}
	
	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedY(int sY) {
		speedY = sY;
	}
	
	public double getSpeedY() {
		return speedY;
	}
}
