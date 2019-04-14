package starter;

import acm.graphics.GImage;

public class Player {
	/* 
	 * For what is below, I suggest not having them "final"
	 * because of how we will be changing them depending 
	 * the power-up used.
	 */
	private static double MAX_SPEED = 4;
	private static double SPEED_DX = .4;
	private static double SPEED_DY = .605;
	private static int MAX_GRAVITY = 10;
	private static double JUMP = 7;
	private static final int PLAYER_SIZE_X = 30;
	private static final int PLAYER_SIZE_Y = 30;
	private static final String skin = "player.png";
	private double speedX;
	private double speedY;
	private boolean onGround;
	private Position originalPosition;
	private Position currentPosition;
	private GImage player;
	private PlayerMovement currentMove;
	private PlayerJump currentJump;
	private Position lastPos;
	private int lives;
	private int health;
	private int powerUps;
	private boolean lostLife;

	/*
	 * Constructor
	 */
	public Player(double x, double y) {
		currentPosition = new Position(x, y);
		originalPosition = new Position(x, y);
		player = new GImage(skin, currentPosition.getX(), currentPosition.getY());
		player.setSize(PLAYER_SIZE_X, PLAYER_SIZE_Y);
		speedX = 0;
		speedY = 0;
		//onGround = true;
		currentMove = PlayerMovement.STANDING;
		currentJump = PlayerJump.STAND;
		lives = 3;
		lostLife = false;
		health = 120;
		powerUps = 0;
	}

	public int getLives() {
		return lives;
	}

	public int getHealth() {
		return health;
	}

	public void setLives(int l) {
		lives = l;
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

	public void setCurrentPosition(Position p) {
		currentPosition = p;
	}
	
	//Resets all characteristics of player
	public void resetAll() {
		player = new GImage(skin, currentPosition.getX(), currentPosition.getY());
		player.setSize(PLAYER_SIZE_X, PLAYER_SIZE_Y);
		setCurrentPosition(originalPosition);
		setSpeedX(0);
		setSpeedY(0);
		setCurrentMove(PlayerMovement.STANDING);
		setCurrentJump(PlayerJump.STAND);
		setHealth(30);
		calculateLives();
		setPowerUps(0);
	}
	
	//Resets all characteristics except health
	//Used after player loses a life
	public void reset() {
		player = new GImage(skin, currentPosition.getX(), currentPosition.getY());
		player.setSize(PLAYER_SIZE_X, PLAYER_SIZE_Y);
		setCurrentPosition(originalPosition);
		setSpeedX(0);
		setSpeedY(0);
		setCurrentMove(PlayerMovement.STANDING);
		setCurrentJump(PlayerJump.STAND);
		calculateLives();
		setPowerUps(0);
	}

	private void calculateLives() {
		lostLife = lostALife((int)(getHealth()/30));
		setLives((int)(getHealth()/30));
	}

	public boolean lostALife(int after) {
		if(after < lives) {
			return true;
		}
		return false;
	}
	
	public void addForce() {
		if (currentJump == PlayerJump.JUMP && onGround) {
			speedY -= JUMP;
			onGround = false;
			lastPos = new Position(player.getX(), player.getY());
		}
		if(Math.abs(speedX) < SPEED_DX / 2) {
			speedX = 0;
		}
		if (currentMove == PlayerMovement.RIGHT) {
			speedX = Math.min(speedX + SPEED_DX, MAX_SPEED);
			lastPos = new Position(player.getX(), player.getY());
		}
		if (currentMove == PlayerMovement.LEFT) {
			speedX = Math.max(speedX - SPEED_DX, -MAX_SPEED);
		}
	}
	
	public boolean isLifeLost() {
		return lostLife;
	}
	
	public void setLifeLost(boolean b) {
		lostLife = b;
	}
	
	public void loseHealth(int health) {
		setHealth(getHealth() - health);
		calculateLives();
	}

	public void addFriction() {
		speedX *= .9;
	}

	public void processGravity() {
		if (onGround) {
			speedY = 0;
		} else {
			speedY = Math.min(speedY + SPEED_DY / 3, MAX_GRAVITY);
		}
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

	public GImage getGImage() {
		return player;
	}

	public Position getPosition() {
		return currentPosition;
	}
	
	public Position getOriginalPosition() {
		return originalPosition;
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

	public void setSpeedX(double sX) {
		speedX = sX;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setSpeedY(double d) {
		speedY = d;
	}

	public double getSpeedY() {
		return speedY;
	}
	
	public boolean getOnGround() {
		return onGround;
	}
	
	public void setOnGround(boolean og) {
		onGround = og;
	}
	
	public double getJumpSpeed() {
		return JUMP;
	}
}
