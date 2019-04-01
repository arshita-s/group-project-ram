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
	private static double SPEED_DY = .2;
	private static int MAX_GRAVITY = 10;
	private static double MAX_JUMP = 2;
	private static int GROUND = 750;
	private static final int PLAYER_SIZE_Y = 50;
	private double speedX;
	private double speedY;
	private Position startPosition;
	private GOval player;
	private PlayerMovement currentMove;
	private PlayerJump currentJump;

	
	/*
	 * Constructor
	 */
	public Player(int x, int y) {
		startPosition = new Position(x, y);
		player = new GOval(startPosition.getX(), startPosition.getY(), 50, PLAYER_SIZE_Y);
		speedX = 0;
		speedY = 0;
		currentMove = PlayerMovement.STANDING;
		currentJump = PlayerJump.STAND;
	}


	public void addForce() {
		if (currentJump == PlayerJump.JUMP) {
			speedY = Math.max(speedY - SPEED_DY, -MAX_GRAVITY);
		} else if (currentMove == PlayerMovement.RIGHT && speedX < MAX_SPEED) {
			speedX = Math.min(speedX + SPEED_DX, MAX_SPEED);
		} else if (currentMove == PlayerMovement.LEFT && -MAX_SPEED < speedX) {
			speedX = Math.max(speedX - SPEED_DX, -MAX_SPEED);
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
		if (currentJump == PlayerJump.STAND || speedY < -MAX_GRAVITY) {
			if (player.getY() + player.getHeight() <= GROUND - 1) {
				speedY = Math.min(speedY + SPEED_DY / 3, MAX_GRAVITY);
			} else {
				speedY = 0;
				player.setLocation(player.getX(), GROUND - player.getHeight());
			}
		}
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

	public PlayerMovement getCurrent() {
		return currentMove;
	}
	
	public GOval getGOval() {
		return player;
	}

	public Position getPosition() {
		return startPosition;
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
