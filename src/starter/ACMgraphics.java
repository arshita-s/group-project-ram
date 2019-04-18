package starter;

import java.awt.Color;
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
import acm.util.SoundClip;

// Here I will take obstacles and put them on the screen
public class ACMgraphics extends GraphicsPane implements ActionListener, KeyListener {
	private static final String BACKGROUND = "background.png";
	private GImage backGround = new GImage(BACKGROUND, 0, 0);
	private static final int BOUND = 5;
	private static final int STARTING_SCORE = 2000;
	private static final int LAST_LEVEL = 1;
	
	private int levelNumber;
	private long score;
	private MainApplication program;
	private ArrayList<GObject> mapObstacles;
	private GImage[] mapMasks;
	private GImage[] mapEnemies;
	private Player player;
	private Map level;
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
	private int lastPressed;
	boolean moving = false;

	/*
	 * Background Music: "Our Mountain" by Eric Matyas
	 * 					 "Fantasy Game Background" by Eric Matyas
	 */

	private static final String[] SOUND_FILES = {"Our-Mountain_v003.mp3", "enemy_sound.wav", "death_sound.wav", "damage_sound.wav"};
	public static final String MUSIC_FOLDER = "sounds";

	public ACMgraphics(MainApplication app) {
		super();
		this.program = app;
		levelNumber = 1;
		level = new Map();
		mapObstacles = new ArrayList<GObject>();
		mapEnemies = new GImage[level.getEnemyList().size()];
		mapMasks = new GImage[level.getMaskList().size()];
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
		lives.setColor(new Color(255,255,255));
	}

	public void actionPerformed(ActionEvent e) {
		player.setLastPos(new Position(player.getGImage().getX(), player.getGImage().getY()));
		moveScreen();
		moveMapEnemies(0);
		player.addFriction();
		player.addForce();
		player.processGravity();
		processObstacleCollision();
		if(playerAtEnd()) {
			program.switchToScore((int)score);
		}
		processEnemyCollision();
		//processMaskCollision();
		player.move();
		player.playerAnimation();
		playBackgroundMusic();
		pitDeath();
	}

	private void pitDeath() {
		if(player.getGImage().getY() + player.getGImage().getHeight() > program.WINDOW_HEIGHT) {
			player.loseHealth(30);
			checkForDeath();
		}
		
	}

	private boolean playerAtEnd() {
		for(GObject obj: mapObstacles) {
			if(obstacleCollisionX(player.getSpeedX(), obj) && !obj.isVisible()) {
				score = STARTING_SCORE - (System.currentTimeMillis() - score)/100;
				return true;
			}
		}
		return false;
	}

	private void moveScreen() {
		if(player.getGImage().getX() + player.getGImage().getWidth() > program.getWidth() - BOUND) {
			player.getGImage().setLocation(BOUND + player.getGImage().getWidth(), player.getGImage().getY());
			moveMapObstacles(-program.getWidth());
			moveMapEnemies(-program.getWidth());
		}
		if(player.getGImage().getX() < BOUND) {
			player.getGImage().setLocation(program.getWidth() - BOUND - player.getGImage().getWidth(), player.getGImage().getY());
			moveMapObstacles(program.getWidth());
			moveMapEnemies(program.getWidth());
		}
	}

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
		level.readFromFile(levelNumber);
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
		int i = 0;
		for(Enemy enem: level.getEnemyList())
		{
			enemy = createEnemy(enem);
			mapEnemies[i] = enemy;
			program.add(enemy);
			i++;
		}
		
		/* COMMENTED OUT BECAUSE NO IMAGE FILE YET
		GImage mask;
		i = 0;
		for(Mask m: level.getMaskList()) {
			mask = createMask(mask);
			mapMasks[i] = mask;
			program.add(mask);
			i++;
		}
		*/
		
		player = level.getPlayer();
		program.add(player.getGImage());
		player.setLastPos(player.getOriginalPosition());
		updateLives();
		program.add(lives);
		if(player.getLives() == 3) {
			score = System.currentTimeMillis();
		}
	}

	
	public GImage createMask(Mask p) {
		return p.getSkin();
	}
	public GImage createEnemy(Enemy e) {
		return e.getSkin();
	}

	public GImage createObstacle(Obstacle obs) {
		return obs.getGImage();
	}

	public void moveMapObstacles(double distance) {
		for(int i = 0; i < mapObstacles.size(); i++) {
			GObject o = mapObstacles.get(i);
			o.move(distance, 0);
			level.getObstacleList().get(i).setCurrentPosition(new Position((int) o.getX(), (int) o.getY()));
		}
	}

	private void moveMapEnemies(double distance) {
		for(int i = 0; i < mapEnemies.length; i++) {
			if(mapEnemies[i] != null) {
				level.getEnemyList().get(i).move();
				mapEnemies[i].move(level.getEnemyList().get(i).getdX() + distance, 0);
				level.getEnemyList().get(i).setCurrentPosition(new Position((int) mapEnemies[i].getX(), (int) mapEnemies[i].getY()));
			}
		}
	}

	//Logic for colliding with obstacles
	private void processObstacleCollision() {
		checkBounds(player.getGImage());
		for(GObject obj: mapObstacles) {
			if(!obj.isVisible()) break;
			if(obstacleCollisionX(player.getSpeedX(), obj)) {
				player.setSpeedX(0);
			}
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
	private boolean obstacleCollisionX(double speed, GObject obs) {
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

		return false;
	}

	//Returns true if player collided with obstacle in y direction
	private boolean obstacleCollisionY(double speed) {
		for(GObject obs: mapObstacles) {
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
		SoundClip fx;
		if(enemyCollisionDeath(player.getSpeedX(), player.getSpeedY())) {
			player.loseHealth(30); 
			checkForDeath();
		}
		if(enemyBounce(player.getSpeedY())) {
			fx = new SoundClip("sounds/" + SOUND_FILES[1]);
			fx.setVolume(1);
			fx.play();
			player.setSpeedY(-1 * player.getJumpSpeed());
			program.remove(collidingO);
			for(int i = 0; i < mapEnemies.length; i++) {
				if (mapEnemies[i] == collidingO) {
					mapEnemies[i] = null;
				}
			}

		}
	}

	private void checkForDeath() {
		SoundClip fx;
		if(player.getLives() == 0) {
			tm.stop();
			program.switchToMainMenu();
		} 
		else if(!player.isLifeLost()) {
			fx = new SoundClip(MUSIC_FOLDER +"/" + SOUND_FILES[3]); //one hit
			fx.setVolume(1);
			fx.play();
			player.setSpeedX(-2*player.getSpeedX());
		}
		else {
			tm.stop();
			player.playerDieAnimation();
			fx = new SoundClip(MUSIC_FOLDER +"/" + SOUND_FILES[2]); //death
			fx.setVolume(1);
			fx.play();
			reset();
			tm.start();
		}
	}

	private void processMaskCollision() {
		if(maskCollision(player.getSpeedX(), player.getSpeedY())) {
			//TODO SWITCH ANIMATION IMAGES OF THE PLAYER
			program.remove(collidingO);
		}
	}
	
	private boolean maskCollision(double speedX, double speedY) {
		for(GObject mask: mapMasks) {
			if(mask != null) {
				if(mask == program.getElementAt(pointNE.getX() + speedX, pointNE.getY() +1)) {
					collidingO = program.getElementAt(pointNE.getX() + speedX, pointNE.getY()+1);
					return true;
				} else if(mask == program.getElementAt(pointNW.getX() + speedX, pointNW.getY()+1)) {
					collidingO = program.getElementAt(pointNW.getX() + speedX, pointNW.getY()+1);
					return true;
				} else if(mask == program.getElementAt(pointSE.getX() + speedX, pointSE.getY()-1)) {
					collidingO = program.getElementAt(pointSE.getX() + speedX, pointSE.getY()-1);
					return true;
				} else if(mask == program.getElementAt(pointSW.getX() + speedX, pointSW.getY()-1)) {
					collidingO = program.getElementAt(pointSW.getX() + speedX, pointSW.getY()-1);
					return true;
				} else if(mask == program.getElementAt(pointNE.getX(), pointNE.getY() + speedY)) {
					collidingO = program.getElementAt(pointNE.getX(), pointNE.getY() + speedY);
					return true;
				} else if(mask == program.getElementAt(pointNW.getX(), pointNW.getY() + speedY)) {
					collidingO = program.getElementAt(pointNW.getX(), pointNW.getY() + speedY);
					return true;
				}  else if(mask == program.getElementAt(pointE.getX() + speedX, pointE.getY()-1)) {
					collidingO = program.getElementAt(pointE.getX() + speedX, pointE.getY()-1);
					return true;
				} else if(mask == program.getElementAt(pointW.getX() + speedX, pointW.getY()-1)) {
					collidingO = program.getElementAt(pointW.getX() + speedX, pointW.getY()-1);
					return true;
				} else if(mask == program.getElementAt(pointN.getX(), pointN.getY() + speedY)) {
					collidingO = program.getElementAt(pointN.getX(), pointN.getY() + speedY);
					return true;
				} else if(mask == program.getElementAt(pointSE.getX(), pointSE.getY() + speedY)) {
					collidingO = program.getElementAt(pointSE.getX(), pointSE.getY() + speedY);
					player.getGImage().setLocation(pointNW.getX(), collidingO.getY() - player.getGImage().getHeight());
					return true;
				} else if(mask == program.getElementAt(pointSW.getX(), pointSW.getY() + speedY)) {
					collidingO = program.getElementAt(pointSW.getX(), pointSW.getY() + speedY);
					player.getGImage().setLocation(pointNW.getX(), collidingO.getY() - player.getGImage().getHeight());
					return true;
				} else if(mask == program.getElementAt(pointS.getX(), pointS.getY() + speedY)) {
					collidingO = program.getElementAt(pointS.getX(), pointS.getY() + speedY);
					return true;
				}
			}
		}
		return false;
	}
	
	//Returns true if player collided with enemy from every direction except top 
	private boolean enemyCollisionDeath(double speedX, double speedY) {
		for(GObject enem: mapEnemies) {
			if(enem != null) {
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
		}
		return false;
	}

	//Returns true if player collided with enemy from the top
	private boolean enemyBounce(double speedY) {
		for(GObject enem: mapEnemies) {
			if(enem != null) {
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
		}
		return false;
	}

	//Removes all drawings
	private void clearScreen() {
		program.removeAll();
		program.remove(lives);
	}

	public void resetAll() {
		resetArrayLists();
		resetPositions();
		player.resetAll();
	}

	//Removes all objects from all arraylists
	private void resetArrayLists() {
		mapObstacles.clear();
		mapEnemies = new GImage[level.getEnemyList().size()];

	}

	//Resets positions of all objects in the game
	private void resetPositions() {
		for(Obstacle obs: level.getObstacleList()) {
			obs.resetLocation();
		}
		for(Enemy enem: level.getEnemyList()) {
			enem.resetPosition();
			//enem.setCurrentPosition(enem.getSpawnPoint());
		}

	}
	
	public void nextLevel() {
		
	}

	//redraw from level text file with less life
	//Used after losing a life. Currently has the player restart the level with one less life
	private void reset() {
		program.removeAll();
		resetArrayLists();
		resetPositions();
		player.reset();
		updateLives();

		setupLevel(program);
	}

	//adds images to game
	public void returnToGame() {
		backGround.setSize(program.GAME_WINDOW_WIDTH , program.getHeight());
		program.add(backGround);
		for(GObject obs: mapObstacles) {
			program.add(obs);
		}
		for(GObject enem: mapEnemies) {
			if(enem != null) {
				program.add(enem);
			}
		}
		program.add(player.getGImage());
		program.add(lives);
		tm.start();
	}

	public boolean isLastLevel() {
		return levelNumber == LAST_LEVEL;
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
		playBackgroundMusic();
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
}
