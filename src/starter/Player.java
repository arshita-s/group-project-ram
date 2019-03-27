package starter;

import java.awt.Color;
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
	private GOval player;
	private PlayerMovement current;
	
	public Player() {
		player = new GOval(2, 2);
		while(true) {
			player.move(0, 0);
		}
	}
	
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
	}
	
	public void keyReleased(KeyEvent e) {
		
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
		if(speedY == GROUND || speedY < MAX_JUMP) {
			speedY += SPEED_DY;
		}
	}
	
	public void processFalling() {
		
	}
	
	public void processImage() {
		
	}
}
