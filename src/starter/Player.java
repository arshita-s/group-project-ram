 package starter;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import acm.graphics.GOval;

public class Player implements KeyListener {
	/* 
	 * For what is below, I suggest not having them "final"
	 * because of how we will be changing them depending 
	 * the power-up used.
	*/
	private static final double MAX_SPEED = 2;
	private static final double SPEED_DX = .4;
	private static final double SPEED_DY = .2;
	private static final int MAX_GRAVITY = 10;
	private static final double MAX_JUMP = -2;
	private static final int GROUND = 650;
	private double speedX;
	private boolean jumpStatus;
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
		else if(e.getKeyCode() == KeyEvent.VK_W) {
			current = PlayerMovement.JUMP;
			addForce();
		}
		player.move(speedX, speedY);
	}

	public GOval getGOval() {
		return player;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(current != PlayerMovement.JUMP) {
			addFriction();
		}
		else if(current == PlayerMovement.JUMP){
			while(speedY > 0) {
				processFalling();
			}
		}
	}
	
	public PlayerMovement getCurrent() {
		return current;
	}
	
	public void addForce() {
		if(current == PlayerMovement.JUMP) {
			if(speedY > MAX_JUMP) {
				jumpStatus = true;
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
	
	public double getSpeedY() {
		return speedY;
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
			if(!jumpStatus) {
				processFalling();
				player.setLocation(player.getX(), startPosition.getY());
			}
		}
		player.move(speedX, speedY);
	}
	
	public void processGravity() {
		while(jumpStatus) {
			speedY -= SPEED_DY;
			if(speedY < MAX_JUMP) {
				speedY = MAX_JUMP;
				jumpStatus = false;
			}
			System.out.println("While launching: " + speedY);
			player.move(speedX, speedY);
		}
	}
	
	public void processFalling() {
		speedY = -speedY;
		while(!jumpStatus && speedY > 0) {
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

	@Override
	public void keyTyped(KeyEvent e) {
		
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
