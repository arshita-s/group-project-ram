package starter;

import acm.graphics.GOval;

public class Player {
	private static final double MAX_SPEED = 4;
	private static final double SPEED_DX = .4;
	private static final double SPEED_DY = .2;
	private static final int MAX_GRAVITY = 10;
	private static final int MAX_JUMP = 50;
	private static final int GROUND = 650;
	private double speedX;
	private double speedY;
	private Position startPosition;
	private GOval player;
	private PlayerMovement current = PlayerMovement.STANDING;
	
	public Player(int x, int y) {
		startPosition = new Position(x, y);
		player = new GOval(startPosition.getX(), startPosition.getY(), 50, 50);
		speedX = 0;
		speedY = 0;
	}

	public GOval getGOval() {
		return player;
	}
	
	public PlayerMovement getCurrent() {
		return current;
	}
	
	public void addForce() {
		if(current == PlayerMovement.JUMP) {
			processGravity();
		} else 	if(current == PlayerMovement.RIGHT && speedX < MAX_SPEED) {
				speedX = Math.min(speedX + SPEED_DX, MAX_SPEED);
		} else if(current == PlayerMovement.LEFT && -MAX_SPEED < speedX) {
				speedX = Math.max(speedX - SPEED_DX, -MAX_SPEED);
		}
	}
	
	public double getSpeedY() {
		return speedY;
	}
	
	public double getSpeedX() {
		return speedX;
	}
	
	public void addFriction() {
		if(current == PlayerMovement.STANDING && speedX < 0) {
			speedX = Math.min(0, speedX + SPEED_DX / 2);
		} else if(current == PlayerMovement.STANDING && 0 < speedX) {
			speedX = Math.max(0, speedX - SPEED_DX / 2);
		}
			/*
		if(current == PlayerMovement.RIGHT) {
			while(speedX > 0) {
				speedX -= (SPEED_DX);
			}
			if(speedX < 0) {
				speedX = 0;
			}
		} else if(current == PlayerMovement.LEFT) {
			while(speedX < 0) {
				speedX += (SPEED_DX);
			}
			if(speedX > 0) {
				speedX = 0;
			}
		}
			 */
		//player.move(speedX, speedY);
	}
	
	public void processGravity() {
		if(speedY < MAX_JUMP) {
			speedY -= SPEED_DY;
		}
		else if(speedY > MAX_JUMP) {
			speedY = MAX_JUMP;
		}
	}
	
	public void processFalling() {
		if(player.getY() < GROUND ) {
			speedY += SPEED_DY;
		}
		player.move(speedX, speedY);
	}
	
	public void processImage() {
		
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
}
