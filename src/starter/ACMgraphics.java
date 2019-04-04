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
import acm.graphics.GObject;
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
		double x = player.getGOval().getX();
		if (x < 400 && player.getSpeedX() != 0 && player.getCurrent() == PlayerMovement.LEFT) {
			vX = 4;
		} else if (x > 400 && player.getSpeedX() != 0 && player.getCurrent() == PlayerMovement.RIGHT) {
			vX = -4;
		}
		else {
			vX = 0;
		}
		
		moveMapObstacles(vX);
		moveMapEnemies(vX);
		player.move();
		player.addFriction();
		player.processGravity();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_D) {
			player.setCurrentMove(PlayerMovement.RIGHT);
			if(!detectCollisionObstacle()) {
				player.addForce();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player.setCurrentMove(PlayerMovement.LEFT);
			if(!detectCollisionObstacle()) {
				player.addForce();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			player.setCurrentJump(PlayerJump.JUMP);
			if(!detectCollisionObstacle()) {
				player.addForce();
				player.addFriction();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			program.switchHelpInGame();
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
	
	private boolean obstacleAt(GObject p) {
		double x = p.getX();
		double y = p.getY();
		GObject z = program.getElementAt(x,y);
		for(GRect o: mapObstacles) {
			if(z == o) {
				for(double i = o.getX(); i < o.getWidth()+o.getX(); i++) {
					for(double j = o.getY(); j < o.getHeight()+o.getY(); j++) {
						if(p.contains(i, j)) {
							return true;
						}
					}
				}
				return false;
			}
		}
		return false;
	}
	
	
	private boolean detectCollisionObstacle() {
		Player tempP = player;
		GOval temp = player.getGOval();
		double x = temp.getX();
		double y = temp.getY();
		if(player.getCurrent() == PlayerMovement.RIGHT && player.getJump() == PlayerJump.STAND) {
			x += 1;
			tempP = new Player(x,y);
			temp = tempP.getGOval();
			System.out.print(obstacleAt(temp));
			return obstacleAt(temp);
		}
		else if(player.getCurrent() == PlayerMovement.LEFT && player.getJump() == PlayerJump.STAND) {
			x -= 1;
			tempP = new Player(x,y);
			temp = tempP.getGOval();
			System.out.print(obstacleAt(temp));
			return obstacleAt(temp);
		}
		else if(player.getJump() == PlayerJump.JUMP && player.getCurrent() == PlayerMovement.RIGHT) {
			x += 1;
			y += 1;
			tempP = new Player(x,y);
			temp = tempP.getGOval();
			System.out.print(obstacleAt(temp));
			return obstacleAt(temp);
		}
		else if(player.getJump() == PlayerJump.JUMP && player.getCurrent() == PlayerMovement.LEFT) {
			x -= 1;
			y += 1;
			tempP = new Player(x,y);
			temp = tempP.getGOval();
			System.out.print(obstacleAt(temp));
			return obstacleAt(temp);
		}
		return false;
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
