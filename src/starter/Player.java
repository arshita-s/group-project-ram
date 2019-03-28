package starter;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import acm.graphics.GOval;

public class Player {
	private static final int MAX_SPEED = 5;
	private static final double SPEED_DX = .4;
	private static final double SPEED_DY = .2;
	private static final int MAX_GRAVITY = 10;
	private static final int MAX_JUMP = 5;
	private static final int GROUND = 650;
	private int speedX;
	private int speedY;
	private Position startPosition;
	private GOval player;
	private PlayerMovement current;
	
	public Player(int x, int y) {
		startPosition = new Position(x, y);
		player = new GOval(startPosition.getX(), startPosition.getY(), 50, 50);
		speedX = 0;
		speedY = 0;
	}
	
	public GOval getGOval() {
		return player;
	}

	public void addForce() {
		if(current == PlayerMovement.JUMPSTANDING) {
			processGravity();
			processFalling();
		}
		else {
			if(current == PlayerMovement.RIGHT) {
				speedX += SPEED_DX;
			}
			else if(current == PlayerMovement.LEFT) {
				speedX -= SPEED_DX;
			}
		}
	}
	
	public void addFriction() {
		if(speedX > 0) {
			speedX -= SPEED_DX;
		}
		else if(speedX < 0) {
			speedX = 0;
		}
	}
	
	public void processGravity() {
		if(speedY < MAX_JUMP) {
			speedY -= SPEED_DY;
		}
	}
	
	public void processFalling() {
		while(speedY != GROUND) {
			speedY += SPEED_DY;
		}
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
