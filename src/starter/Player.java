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
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			
		}
	}
	
	public void addForce() {
		
	}
	
	public void addFriction() {
		
	}
}
