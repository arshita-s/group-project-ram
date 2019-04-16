package starter;

import acm.graphics.GImage;

public class Player {
	/* 
	 * For what is below, I suggest not having them "final"
	 * because of how we will be changing them depending 
	 * the power-up used.
	 */
	private static double MAX_SPEED = 4;
	private static double SPEED_DX = .2;
	private static double SPEED_DY = .605;
	private static int MAX_GRAVITY = 10;
	private static double JUMP = 7;
	private static final int PLAYER_SIZE_X = 50;
	private static final int PLAYER_SIZE_Y = 50;
	private static final int STARTING_HEALTH = 110;
	private static final int LIFE_VARIABLE = 30;
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
	
	private static final String skin = "player_standing_right.png";
	private String[] animationWalkRight;
	private double animationWalkRightFrame = 0;
	private String[] animationWalkLeft;
	private double animationWalkLeftFrame = 0;
	private double animationSpeed = SPEED_DX;
	private PlayerMovement lastXDirection = PlayerMovement.RIGHT;

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
		currentMove = PlayerMovement.STANDING;
		currentJump = PlayerJump.STAND;
		lives = STARTING_HEALTH/LIFE_VARIABLE;
		lostLife = false;
		health = STARTING_HEALTH;
		powerUps = 0;
		setWalkingAnimation();
	}
	private void setWalkingAnimation() {
		int totalImages = 8;
		animationWalkRight = new String[totalImages];	
		animationWalkLeft = new String[totalImages];	
		for (int i = 0; i < totalImages; i++) {
			animationWalkRight[i] = "player_walk_right_" + Integer.toString(i+1) + ".png";
		}
		for (int i = 0; i < totalImages; i++) {
			animationWalkLeft[i] = "player_walk_left_" + Integer.toString(i+1) + ".png";
		}
	}
	public int getLives() {
		return lives;
	}
//player.setImage(animationWalkRight[frame]);
	public int getHealth() {
		return health;
	}
	public void playerAnimation() {
		//System.out.println("speedX: " + speedX + "\nspeedY: " + speedY);
		playerWalkLeftNextFrame();
		playerWalkRightNextFrame();
		playerStandingAnimation();
		playerJumpAnimation();
	}
	public void playerWalkRightNextFrame() {
		if(speedX > 0) {
			lastXDirection = PlayerMovement.RIGHT;
			animationWalkRightFrame+=animationSpeed;
			//System.out.println("Right: " + animationWalkRight[(int)(animationWalkRightFrame%8)]);
			player.setImage(animationWalkRight[(int)(animationWalkRightFrame%8)]);
			player.setSize(player.getWidth(), PLAYER_SIZE_Y);
		}
	}
	public void playerWalkLeftNextFrame() {
		if(speedX < 0) {
			lastXDirection = PlayerMovement.LEFT;
			animationWalkLeftFrame+=animationSpeed;
			//System.out.println("Left: " + animationWalkLeft[(int)(animationWalkLeftFrame%8)]);
			player.setImage(animationWalkLeft[(int)(animationWalkLeftFrame%8)]);
			player.setSize(player.getWidth(), PLAYER_SIZE_Y);
		}
	}
	public void playerStandingAnimation() {
		if(speedX == 0) {
			if(lastXDirection == PlayerMovement.RIGHT) player.setImage("player_standing_right.png");
			else player.setImage("player_standing_left.png");
			player.setSize(player.getWidth(), PLAYER_SIZE_Y);
		}
	}
	public void playerJumpAnimation() {
		if(lastXDirection == PlayerMovement.RIGHT) {
			if(speedY < 0) {
				player.setImage("player_jump_right_1.png");
				player.setSize(player.getWidth(), PLAYER_SIZE_Y);
			}
			if(speedY > 0) {
				player.setImage("player_jump_right_2.png");
				player.setSize(player.getWidth(), PLAYER_SIZE_Y);
			}			
		}
		if(lastXDirection == PlayerMovement.LEFT) {
			if(speedY < 0) {
				player.setImage("player_jump_left_1.png");
				player.setSize(player.getWidth(), PLAYER_SIZE_Y);
			}
			if(speedY > 0) {
				player.setImage("player_jump_left_2.png");
				player.setSize(player.getWidth(), PLAYER_SIZE_Y);
			}	
		}
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
		setHealth(STARTING_HEALTH);
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
		lostLife = lostALife(getHealth()/30);
		setLives((int)(getHealth()/30));
	}

	public boolean lostALife(int after) {
		return after < lives && after < STARTING_HEALTH / LIFE_VARIABLE;
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
