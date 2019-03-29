package starter;

import acm.graphics.GOval;

public class Player {
	private static final double MAX_SPEED = 4;
	private static final double SPEED_DX = .4;
	private static final double SPEED_DY = 1;
	private static final int MAX_GRAVITY = 3;
	private static final int GROUND = 750;
	private static final int PLAYER_SIZE_Y = 50;
	private double speedX;
	private double speedY;
	private Position startPosition;
	private GOval player;
	private PlayerMovement currentMove;
	private PlayerJump currentJump;

	public Player(int x, int y) {
		startPosition = new Position(x, y);
		player = new GOval(startPosition.getX(), startPosition.getY(), 50, PLAYER_SIZE_Y);
		speedX = 0;
		speedY = 0;
		currentMove = PlayerMovement.STANDING;
		currentJump = PlayerJump.STAND;
	}

	public GOval getGOval() {
		return player;
	}

	public PlayerMovement getCurrentMove() {
		return currentMove;
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

	public double getSpeedY() {
		return speedY;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void addFriction() {
		if (currentMove == PlayerMovement.STANDING && speedX < 0) {
			speedX = Math.min(0, speedX + SPEED_DX / 2);
		} else if (currentMove == PlayerMovement.STANDING && 0 < speedX) {
			speedX = Math.max(0, speedX - SPEED_DX / 2);
		}
	}

	public void processGravity() {
		if (currentJump == PlayerJump.STAND || speedY == -MAX_GRAVITY) {
			if (player.getY() + player.getHeight() <= GROUND) {
				speedY += SPEED_DY;
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
}
