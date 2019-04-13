package starter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GPoint;

// Here I will take obstacles and put them on the screen
public class ACMgraphics extends GraphicsPane implements ActionListener, KeyListener {
	private static final String BACKGROUND = "background.png";
	private GImage backGround = new GImage(BACKGROUND, 0, 0);
	//private static final int BOUND = 5;

	private MainApplication program;
	private ArrayList<GImage> mapObstacles;
	private ArrayList<GImage> mapEnemies;
	private Player player;
	private Map level;
	private double vX = 0;
	private GPoint pointNE;
	private GPoint pointSW;
	private GPoint pointNW;
	private GPoint pointSE;
	private GPoint pointN;
	private GPoint pointS;
	private GPoint pointE;
	private GPoint pointW;
	Timer tm = new Timer(10, this);
	GObject collidingO;
	private GLabel lives;
	private GLabel powerups;
	private int lastPressed;
	boolean moving = false;
	
	/*
	 * Background Music: "Our Mountain" by Eric Matyas
	 */
	
	private static final String[] SOUND_FILES = {"Our-Mountain_v003.mp3" };
	public static final String MUSIC_FOLDER = "sounds";

	public ACMgraphics(MainApplication app) {
		super();
		this.program = app;
		level = new Map();
		mapObstacles = new ArrayList<GImage>();
		mapEnemies = new ArrayList<GImage>();
		player = new Player(0, 0);
	}

	public void run(MainApplication program) {
		setupLevel(program);
		tm.start();
	}

	public void updateLives() {
		try {
			program.remove(lives);
		} catch(NullPointerException e) {}
		lives = new GLabel("Lives: " + player.getLives(), 10, 50);
		lives.setFont("Arial-18");
	}

	public void updatePowerUps() {
		try {
			program.remove(powerups);
		} catch(NullPointerException e) {}
		powerups = new GLabel("Power-Ups: " + player.getPowerUps(), 475, 50);
		powerups.setFont("Arial-18");
	}

	public void actionPerformed(ActionEvent e) {
		//if(player.getGOval() != null) {
		//if(!playerAtEnd()) {
		//setCameraSpeed();
		playBackgroundMusic();
		player.setLastPos(new Position(player.getGImage().getX(), player.getGImage().getY()));

		//moveScreen();
		moveMapEnemies(vX);
		player.addFriction();
		player.addForce();
		player.processGravity();
		processObstacleCollision();
		processEnemyCollision();
		player.move();
		
	}

	/*
	private void setCameraSpeed() {
		double x = player.getPosition().getX();
		double pixelsMoved = 0;
		if(x > 500 && x < 600) {
			moving = true;
			vX = 4;
			while(moving) {
				if(pixelsMoved < 500) {
					pixelsMoved++;
					player.stop();
				}
				else {
					vX = 0;
					moving = false;
				}
			}
		}
		
		if (x < 200 && speedX != 0 && player.getCurrent() == PlayerMovement.LEFT) {
			vX = 4;
		} else if (x > 400 && speedX != 0 && player.getCurrent() == PlayerMovement.RIGHT) {
			vX = -4;
=======
	private void moveScreen() {
		if(player.getGImage().getX() + player.getGImage().getWidth() > program.getWidth() - BOUND) {
			player.getGImage().setLocation(BOUND + player.getGImage().getWidth(), player.getGImage().getY());
			moveMapObstacles(-program.getWidth());
			moveMapEnemies(-program.getWidth());
>>>>>>> branch 'master' of https://github.com/comp55-spr19/group-project-ram.git
		}
		if(player.getGImage().getX() < BOUND) {
			player.getGImage().setLocation(program.getWidth() - BOUND - player.getGImage().getWidth(), player.getGImage().getY());
			moveMapObstacles(program.getWidth());
			moveMapEnemies(program.getWidth());
		}
<<<<<<< HEAD
		
=======
>>>>>>> branch 'master' of https://github.com/comp55-spr19/group-project-ram.git
	}
*/
	@Override
	public void keyPressed(KeyEvent e) {
		lastPressed = e.getKeyCode();
		if (lastPressed == KeyEvent.VK_D) {
			player.setCurrentMove(PlayerMovement.RIGHT);
		}
		if (lastPressed == KeyEvent.VK_A) {
			player.setCurrentMove(PlayerMovement.LEFT);
		}
		if (lastPressed == KeyEvent.VK_W && player.getOnGround()) {
			player.setCurrentJump(PlayerJump.JUMP);
		}
		if (lastPressed == KeyEvent.VK_ESCAPE) {
			program.switchToHelp();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.setCurrentJump(PlayerJump.STAND);
		}
		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_A) {
			player.setCurrentMove(PlayerMovement.STANDING);			
		}
	}

	public void setupLevel(MainApplication program) {
		backGround.setSize(program.GAME_WINDOW_WIDTH , program.getHeight());
		program.add(backGround);
		GImage obstacle;
		for(Obstacle obst: level.getObstacleList())
		{
			obstacle = createObstacle(obst);
			mapObstacles.add(obstacle);
			program.add(obstacle);
		}
		GImage enemy;
		for(Enemy enem: level.getEnemyList())
		{
			enemy = createEnemy(enem);
			mapEnemies.add(enemy);
			program.add(enemy);
		}
		player = level.getPlayer();
		program.add(player.getGImage());
		player.setLastPos(player.getOriginalPosition());
		updateLives();
		updatePowerUps();
		program.add(lives);
		program.add(powerups);
	}

	public GImage createEnemy(Enemy e) {
		return e.getSkin();
	}

	public GImage createObstacle(Obstacle obs) {
		return obs.getGImage();
	}

	public void moveMapObstacles(double vX2) {
		for(int i = 0; i < mapObstacles.size(); i++) {
			GImage o = mapObstacles.get(i);
			o.move(vX2, 0);
			level.getObstacleList().get(i).setCurrentPosition(new Position((int) o.getX(), (int) o.getY()));
		}
	}

	private void moveMapEnemies(double vX2) {
		int i = 0;
		for(GObject current: mapEnemies) {
			level.getEnemyList().get(i).move();
			int enemyDirection = level.getEnemyList().get(i).getdX();
			current.move(vX2 + enemyDirection , 0);
			level.getEnemyList().get(i).setCurrentPosition(new Position((int) current.getX(), (int) current.getY()));
			i++;
		}
	}

	//Logic for colliding with obstacles
	private void processObstacleCollision() {
		checkBounds(player.getGImage());
		if(obstacleCollisionX(player.getSpeedX())) {
			player.setSpeedX(0);
		}
		if(obstacleCollisionY(player.getSpeedY())) {
			player.setSpeedY(0);
		}
	}

	//Gets all the points of an object
	private void checkBounds(GObject p) {
		pointNE = new GPoint(p.getX() + p.getWidth(), p.getY());
		pointSW = new GPoint(p.getX(), p.getY() + p.getHeight());
		pointNW = new GPoint(p.getX(), p.getY());
		pointSE = new GPoint(p.getX() + p.getWidth(), p.getY() + p.getHeight());
		pointN = new GPoint(p.getX() + p.getWidth()/2, p.getY());
		pointS = new GPoint(p.getX() + p.getWidth()/2, p.getY() + p.getHeight());
		pointE = new GPoint(p.getX() + p.getWidth(), p.getY() + p.getHeight()/2);
		pointW = new GPoint(p.getX(), p.getY() + p.getHeight()/2);
	}

	//Returns true if player collided with obstacle in x direction
	private boolean obstacleCollisionX(double speed) {
		for(GImage obs: mapObstacles) {
			if(obs == program.getElementAt(pointNE.getX() + speed, pointNE.getY() +1)) {
				collidingO = program.getElementAt(pointNE.getX() + speed, pointNE.getY()+1);
				return true;
			} else if(obs == program.getElementAt(pointNW.getX() + speed, pointNW.getY()+1)) {
				collidingO = program.getElementAt(pointNW.getX() + speed, pointNW.getY()+1);
				return true;
			} else if(obs == program.getElementAt(pointSE.getX() + speed, pointSE.getY()-1)) {
				collidingO = program.getElementAt(pointSE.getX() + speed, pointSE.getY()-1);
				return true;
			} else if(obs == program.getElementAt(pointSW.getX() + speed, pointSW.getY()-1)) {
				collidingO = program.getElementAt(pointSW.getX() + speed, pointSW.getY()-1);
				return true;
			} else if(obs == program.getElementAt(pointE.getX() + speed, pointE.getY()-1)) {
				collidingO = program.getElementAt(pointE.getX() + speed, pointE.getY()-1);
				return true;
			} else if(obs == program.getElementAt(pointW.getX() + speed, pointW.getY()-1)) {
				collidingO = program.getElementAt(pointW.getX() + speed, pointW.getY()-1);
				return true;
			}
		}
		return false;
	}

	//Returns true if player collided with obstacle in y direction
	private boolean obstacleCollisionY(double speed) {
		for(GImage obs: mapObstacles) {
			if(obs == program.getElementAt(pointSE.getX(), pointSE.getY() + speed)) {
				collidingO = program.getElementAt(pointSE.getX(), pointSE.getY() + speed);
				player.setOnGround(true);
				player.getGImage().setLocation(pointNW.getX(), collidingO.getY() - player.getGImage().getHeight());
				return true;
			} else if(obs == program.getElementAt(pointSW.getX(), pointSW.getY() + speed)) {
				collidingO = program.getElementAt(pointSW.getX(), pointSW.getY() + speed);
				player.getGImage().setLocation(pointNW.getX(), collidingO.getY() - player.getGImage().getHeight());
				player.setOnGround(true);
				return true;
			} else if(obs == program.getElementAt(pointNE.getX(), pointNE.getY() + speed)) {
				collidingO = program.getElementAt(pointNE.getX(), pointNE.getY() + speed);
				player.setOnGround(false);
				return true;
			} else if(obs == program.getElementAt(pointNW.getX(), pointNW.getY() + speed)) {
				collidingO = program.getElementAt(pointNW.getX(), pointNW.getY() + speed);
				player.setOnGround(false);
				return true;
			} else if(obs == program.getElementAt(pointN.getX(), pointN.getY() + speed)) {
				collidingO = program.getElementAt(pointN.getX(), pointN.getY() + speed);
				return true;
			} else if(obs == program.getElementAt(pointS.getX(), pointS.getY() + speed)) {
				collidingO = program.getElementAt(pointS.getX(), pointS.getY() + speed);
				return true;
			}
		}
		player.setOnGround(false);
		return false;
	}

	//Logic for colliding with enemies
	private void processEnemyCollision() {
		checkBounds(player.getGImage());
		if(enemyCollisionDeath(player.getSpeedX(), player.getSpeedY())) {
			player.loseHealth(10); //TODO Change this line to check for type of enemy and lose health based on that with separate method
			if(player.getLives() == 0) {
				tm.stop();
				program.switchToMainMenu();
			} 
			else {
				reset();
				tm.start();
			}
		}
		if(enemyBounce(player.getSpeedY())) {
			player.setSpeedY(-1 * player.getJumpSpeed());
			program.remove(collidingO);
			mapEnemies.remove(collidingO);
			
		}
	}

	//Returns true if player collided with enemy from every direction except top 
	private boolean enemyCollisionDeath(double speedX, double speedY) {
		for(GImage enem: mapEnemies) {
			if(enem == program.getElementAt(pointNE.getX() + speedX, pointNE.getY() +1)) {
				collidingO = program.getElementAt(pointNE.getX() + speedX, pointNE.getY()+1);
				return true;
			} else if(enem == program.getElementAt(pointNW.getX() + speedX, pointNW.getY()+1)) {
				collidingO = program.getElementAt(pointNW.getX() + speedX, pointNW.getY()+1);
				return true;
			} else if(enem == program.getElementAt(pointSE.getX() + speedX, pointSE.getY()-1)) {
				collidingO = program.getElementAt(pointSE.getX() + speedX, pointSE.getY()-1);
				return true;
			} else if(enem == program.getElementAt(pointSW.getX() + speedX, pointSW.getY()-1)) {
				collidingO = program.getElementAt(pointSW.getX() + speedX, pointSW.getY()-1);
				return true;
			} else if(enem == program.getElementAt(pointNE.getX(), pointNE.getY() + speedY)) {
				collidingO = program.getElementAt(pointNE.getX(), pointNE.getY() + speedY);
				return true;
			} else if(enem == program.getElementAt(pointNW.getX(), pointNW.getY() + speedY)) {
				collidingO = program.getElementAt(pointNW.getX(), pointNW.getY() + speedY);
				return true;
			}  else if(enem == program.getElementAt(pointE.getX() + speedX, pointE.getY()-1)) {
				collidingO = program.getElementAt(pointE.getX() + speedX, pointE.getY()-1);
				return true;
			} else if(enem == program.getElementAt(pointW.getX() + speedX, pointW.getY()-1)) {
				collidingO = program.getElementAt(pointW.getX() + speedX, pointW.getY()-1);
				return true;
			} else if(enem == program.getElementAt(pointN.getX(), pointN.getY() + speedY)) {
				collidingO = program.getElementAt(pointN.getX(), pointN.getY() + speedY);
				return true;
			}
		}
		return false;
	}

	//Returns true if player collided with enemy from the top
	private boolean enemyBounce(double speedY) {
		for(GImage enem: mapEnemies) {
			if(enem == program.getElementAt(pointSE.getX(), pointSE.getY() + speedY)) {
				collidingO = program.getElementAt(pointSE.getX(), pointSE.getY() + speedY);
				player.getGImage().setLocation(pointNW.getX(), collidingO.getY() - player.getGImage().getHeight());
				return true;
			} else if(enem == program.getElementAt(pointSW.getX(), pointSW.getY() + speedY)) {
				collidingO = program.getElementAt(pointSW.getX(), pointSW.getY() + speedY);
				player.getGImage().setLocation(pointNW.getX(), collidingO.getY() - player.getGImage().getHeight());
				return true;
			} else if(enem == program.getElementAt(pointS.getX(), pointS.getY() + speedY)) {
				collidingO = program.getElementAt(pointS.getX(), pointS.getY() + speedY);
				return true;
			}
		}
		return false;
	}

	//Removes all drawings
	private void clearScreen() {
		program.removeAll();
		program.remove(lives);
		program.remove(powerups);
	}

	public void resetAll() {
		resetArrayLists();
		resetPositions();
		player.resetAll();
	}
	
	//Removes all objects from all arraylists
	private void resetArrayLists() {
		mapObstacles.clear();
		mapEnemies.clear();

	}

	//Resets positions of all objects in the game
	private void resetPositions() {
		for(Obstacle obs: level.getObstacleList()) {
			obs.setCurrentPosition(obs.getSpawnPosition());
		}
		for(Enemy enem: level.getEnemyList()) {
			enem.setCurrentPosition(enem.getSpawnPoint());
		}

	}
	
	//redraw from level text file with less life
	//Used after losing a life. Currently has the player restart the level with one less life
	private void reset() {
		program.removeAll();
		resetArrayLists();
		resetPositions();
		player.reset();
		updateLives();
		updatePowerUps();
		
		setupLevel(program);
	}
	
	public void returnToGame() {
		backGround.setSize(program.GAME_WINDOW_WIDTH , program.getHeight());
		program.add(backGround);
		for(GImage obs: mapObstacles) {
			program.add(obs);
		}
		for(GImage enem: mapEnemies) {
			program.add(enem);
		}
		program.add(player.getGImage());
		program.add(lives);
		program.add(powerups);
		tm.start();
	}

	
	private void playBackgroundMusic() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[0]);
	}
	
	private void stopBackgroundMusic() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, SOUND_FILES[0]);
	}
	
	@Override
	public void showContents() {
		run(program);
	}

	@Override
	public void hideContents() {
		tm.stop();
		clearScreen();
		if(lastPressed != KeyEvent.VK_ESCAPE) {
			resetAll();
		}
		stopBackgroundMusic();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {

	}	
}
