package starter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import acm.graphics.GOval;

public class Player implements KeyListener {
	private static final int MAX_SPEED = 5;
	private static final double SPEED_DX = .4;
	private static final double SPEED_DY = .2;
	private static final int MAX_GRAVITY = 10;
	private static final int MAX_JUMP = 5;
	private static final int GROUND = 650;
	private int speedX;
	private int speedY;
	private GOval player;
	private PlayerMovement current;
	
	public Player() {
		player = new GOval(50, 625, 50, 50);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A) {
			current = PlayerMovement.LEFT;
			addForce();
		}
		else if(e.getKeyCode() == KeyEvent.VK_D) {
			current = PlayerMovement.RIGHT;
			addForce();
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			current = PlayerMovement.JUMP;
			addForce();
		}
		player.move(speedX, speedY);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(current != PlayerMovement.JUMP) {
			speedX = 0;
		}
		else {
			speedY = 0;
		}
	}
	
	public void addForce() {
		if(current == PlayerMovement.JUMP) {
			processGravity();
			processFalling();
		}
		else {
			if(speedX < MAX_SPEED) {
				speedX += SPEED_DX;
			}
			else if(speedX > MAX_SPEED) {
				speedX = MAX_SPEED;
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
	
	//This is temporary/ for testing's sake
	public GOval getPlayerObj() {
		return player;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
