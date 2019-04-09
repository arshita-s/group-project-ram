package starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;
import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

// Here I will take obstacles and put them on the screen
public class ACMgraphics extends GraphicsPane implements ActionListener, KeyListener {

	private MainApplication program;
	private ArrayList<GRect> mapObstacles;
	private ArrayList<GOval> mapEnemies;
	private Player player;
	private Map level;
	private double vX = 0;
	private GPoint pointNE;
	private GPoint pointSW;
	private GPoint pointNW;
	private GPoint pointSE;
	private double left;
	private double right;
	private double up;
	private double down;
	Timer tm = new Timer(10, this);
	GObject collidingO;
	private GLabel lives;
	private GLabel powerups;

	public ACMgraphics(MainApplication app) {
		super();
		this.program = app;
		level = new Map();
		mapObstacles = new ArrayList<GRect>();
		mapEnemies = new ArrayList<GOval>();
	}

	public void run(MainApplication program) {
		setupLevel(program);
		tm.start();
	}

	
	public void updateLives() {
		lives = new GLabel("Lives: " + player.getLives(), 10, 50);
		lives.setFont("Arial-18");
	}
	
	public void updatePowerUps() {
		powerups = new GLabel("Power-Ups: " + player.getPowerUps(), 475, 50);
		powerups.setFont("Arial-18");
	}
	
	public void actionPerformed(ActionEvent e) {
		double x = player.getGOval().getX();
		/*if (x < 200 && player.getSpeedX() != 0 && player.getCurrent() == PlayerMovement.LEFT) {
			vX = 4;
		} else if (x > 400 && player.getSpeedX() != 0 && player.getCurrent() == PlayerMovement.RIGHT) {
			vX = -4;
		}
		else {
			vX = 0;
		}*/
		player.setLastPos(new Position(player.getGOval().getX(), player.getGOval().getY()));

		program.add(lives);
		program.add(powerups);
		moveMapObstacles(vX);
		moveMapEnemies(vX);
		player.addFriction();
		player.addForce();
		player.processGravity();
		processObstacleCollision();
		player.move();
		//System.out.println(player.speedX());
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_D) {
			player.setCurrentMove(PlayerMovement.RIGHT);
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			player.setCurrentMove(PlayerMovement.LEFT);
		}
		if (e.getKeyCode() == KeyEvent.VK_W && player.getOnGround()) {
			player.setCurrentJump(PlayerJump.JUMP);
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			program.switchHelpInGame();
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
		//adding obstacles to map
		GRect obstacle;
		for(Obstacle obst: level.getObstacleList())
		{
			obstacle = createObstacle(obst);
			mapObstacles.add(obstacle);
			program.add(obstacle);
		}
		GOval enemy;
		for(Enemy enem: level.getEnemyList())
		{
			enemy = createEnemy(enem);
			mapEnemies.add(enemy);
			program.add(enemy);
		}
		player = level.getPlayer();
		program.add(player.getGOval());
		player.setLastPos(player.getPosition());
		lives = new GLabel("Lives: " + player.getLives(), 10, 50);
		lives.setFont("Arial-18");
		powerups = new GLabel("Power-Ups: " + player.getPowerUps(), 475, 50);
		powerups.setFont("Arial-18");
	}

	public GOval createEnemy(Enemy e) {
		GOval objOva = new GOval(e.getCurrentPosition().getX(), e.getCurrentPosition().getY(), e.getSize().getWidth(), e.getSize().getHeight()); 
		objOva.setFillColor(Color.RED);
		objOva.setFilled(true);
		return objOva;
	}

	public GRect createObstacle(Obstacle obs) {
		GRect rec = new GRect(obs.getSpawnPosition().getX(), obs.getSpawnPosition().getY(), obs.getSize().getWidth(), obs.getSize().getHeight());
		rec.setFillColor(Color.BLACK);
		rec.setFilled(true);
		return rec;
	}

	public void moveMapObstacles(double vX2) {
		int i = 0;
		for(GObject current: mapObstacles) {
			current.move(vX2, 0);
			level.getObstacleList().get(i).setCurrentPosition(new Position((int) current.getX(), (int) current.getY()));
			i++;
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

	private void processObstacleCollision() {
		checkBounds(player.getGOval());
		if(obstacleCollisionX(player.getSpeedX())) {
			player.setSpeedX(0);
		}
		if(obstacleCollisionY(player.getSpeedY())) {
			player.setSpeedY(0);
		}
	}
	
	private void checkBounds(GObject p) {
		GRectangle b = p.getBounds();
		pointNE = new GPoint(p.getX() + p.getWidth(), p.getY());
		pointSW = new GPoint(p.getX(), p.getY() + p.getHeight());
		pointNW = new GPoint(p.getX(), p.getY());
		pointSE = new GPoint(p.getX() + p.getWidth(), p.getY() + p.getHeight());
	}

	private boolean obstacleCollisionX(double speed) {
		for(GRect obs: mapObstacles) {
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
			}
		}
		return false;
	}
	private boolean obstacleCollisionY(double speed) {
		for(GRect obs: mapObstacles) {
			if(obs == program.getElementAt(pointSE.getX(), pointSE.getY() + speed)) {
				collidingO = program.getElementAt(pointSE.getX(), pointSE.getY() + speed);
				player.setOnGround(true);
				player.getGOval().setLocation(pointNW.getX(), collidingO.getY() - player.getGOval().getHeight());
				return true;
			} else if(obs == program.getElementAt(pointSW.getX(), pointSW.getY() + speed)) {
				collidingO = program.getElementAt(pointSW.getX(), pointSW.getY() + speed);
				player.getGOval().setLocation(pointNW.getX(), collidingO.getY() - player.getGOval().getHeight());
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
			}
		}
		player.setOnGround(false);
		return false;
	}
	
	private boolean playerAtEnd() {
		//TODO check to see if player has finished the level.
		return false;
	}

	@Override
	public void showContents() {
		run(program);
	}

	@Override
	public void hideContents() {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}	
}
