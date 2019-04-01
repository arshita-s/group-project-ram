 package starter;

import acm.graphics.GOval;

public class Player{
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
	private static int GROUND = 650;
	private double speedX;
	private double speedY;
	private Position startPosition;
	private GOval player;
	private PlayerMovement current = PlayerMovement.STANDING;
	
	/*
	 * Constructor
	 */
	public Player(int x, int y) {
		startPosition = new Position(x, y);
		player = new GOval(startPosition.getX(), startPosition.getY(), 50, 50);
		speedX = 0;
		speedY = 0;
	}

	/*
	 * Physics below
	 */
	public void addForce() {
		if(current == PlayerMovement.JUMP) {
			if(speedY > MAX_JUMP) {
				processGravity();
			}	
		}
		else {
			if(speedX < MAX_SPEED) {
				if(current == PlayerMovement.RIGHT) {
					speedX += SPEED_DX;
				}
				else if(current == PlayerMovement.LEFT) {
					speedX -= SPEED_DX;
				}
			}
			else if(speedX > MAX_SPEED) {
				speedX = MAX_SPEED;
			}
		}
	}
	
	public void addFriction() {
		if(current == PlayerMovement.RIGHT) {
			while(speedX > 0) {
				speedX -= (SPEED_DX);
			}
			if(speedX < 0) {
				speedX = 0;
			}
		}
		if(current == PlayerMovement.LEFT) {
			while(speedX < 0) {
				speedX += (SPEED_DX);
			}
			if(speedX > 0) {
				speedX = 0;
			}
		}
		if(current == PlayerMovement.JUMP) {
			if(current != PlayerMovement.JUMP) {
				processFalling();
				player.setLocation(player.getX(), startPosition.getY());
			}
		}
		player.move(speedX, speedY);
	}
	
	public void processGravity() {
		while(current == PlayerMovement.JUMP) {
			speedY -= SPEED_DY;
			if(speedY < MAX_JUMP) {
				speedY = MAX_JUMP;
				current = PlayerMovement.STANDING;
			}
			System.out.println("While launching: " + speedY);
			player.move(speedX, speedY);
		}
	}
	
	public void processFalling() {
		speedY = -speedY;
		while(current != PlayerMovement.STANDING && speedY > 0) {
			speedY -= SPEED_DY;
			if(speedY <= 0) {
				speedY = 0;
			}
			System.out.println("While falling: after addition" + speedY);
			player.setLocation(player.getX(), speedY);
		}
	}
	
	public void processImage() {
		
	}

	
	/*
	 * Setters and Getters below.
	 */

	public PlayerMovement getCurrent() {
		return current;
	}
	
	public GOval getGOval() {
		return player;
	}

	public Position getPosition() {
		return startPosition;
	}
	
	public void setCurrent(PlayerMovement current) {
		this.current = current;
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
