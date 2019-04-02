package starter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.GOval;
import acm.graphics.GRect;

// Here I will take obstacles and put them on the screen
public class ACMgraphics extends GraphicsPane implements ActionListener, KeyListener {

	private MainApplication program;
	private ArrayList<GRect> mapObstacles;
	private ArrayList<GOval> mapEnemies;
	private Player player;
	private Map level;
	private double vX = 0;
	Timer tm = new Timer(10, this);

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

	public void actionPerformed(ActionEvent e) {
		if (player.getGOval().getX() < 150) {
			vX = 3;
		} else if (player.getGOval().getX() > 650) {
			vX = -3;
		} else {
			vX = 0;
		}
		moveMapObstacles(vX);
		moveMapEnemies(vX);
		player.move();
		player.addFriction();
		player.processGravity();
		System.out.println(player.getSpeedY());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) {
			player.setCurrentMove(PlayerMovement.RIGHT);
			player.addForce();
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player.setCurrentMove(PlayerMovement.LEFT);
			player.addForce();
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			player.setCurrentJump(PlayerJump.JUMP);
			player.addForce();
			player.addFriction();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			//program.switchHelpInGame();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		player.setCurrentMove(PlayerMovement.STANDING);
		player.setCurrentJump(PlayerJump.STAND);
		player.addFriction();
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
		for(GRect current: mapObstacles) {
			current.move(vX2, 0);
			level.getObstacleList().get(i).setCurrentPosition(new Position((int) current.getX(), (int) current.getY()));
			i++;
		}
	}
	
	private void moveMapEnemies(double vX2) {
		int i = 0;
		for(GOval current: mapEnemies) {
			level.getEnemyList().get(i).move();
			int enemyDirection = level.getEnemyList().get(i).getdX();
			current.move(vX2 + enemyDirection , 0);
			level.getEnemyList().get(i).setCurrentPosition(new Position((int) current.getX(), (int) current.getY()));
			i++;
		}
	}
	
	private boolean obstacleAt(Position p) {
		int x = p.getX();
		int y = p.getY();
		for(Obstacle o: level.getObstacleList()) {
			Position oP = o.getCurrentPosition();
			if (isBetween(oP.getX(), x, oP.getX() + o.getSize().getWidth()) &&
					isBetween(oP.getY(), y, oP.getY() + o.getSize().getHeight())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean enemyAt(Position p) {
		int x = p.getX();
		int y = p.getY();
		for(Enemy e: level.getEnemyList()) {
			Position eP = e.getCurrentPosition();
			if (isBetween(eP.getX(), x , eP.getX() + e.getSize().getWidth()) && 
					isBetween(eP.getY(), y , eP.getY() + e.getSize().getHeight())) {
				return true;
			}
		}
		return false;
	}
	
	// returns true if between two numbers
	private boolean isBetween(int numA, int numToCompare, int numB) {
		return ((numA < numToCompare && numToCompare < numB) || (numB < numToCompare && numToCompare < numA));
	}

	public void next() {
		while(!playerAtEnd()) {
			//TODO all the player processing stuff like
			// the constant moving at 0
			//player.move();
			
		}
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
