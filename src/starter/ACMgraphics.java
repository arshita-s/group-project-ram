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
	private Player player;
	private Map level;
	private int vX = 0;
	private int lastPressed = 0;
	Timer tm = new Timer(10, this);
	
	public ACMgraphics(MainApplication app) {
		super();
		this.program = app;
		level = new Map();
		mapObstacles = new ArrayList<GRect>();
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
	
	public void setupLevel(MainApplication program) {
		//adding obstacles to map
		GRect obstacle;
		for(Obstacle obst: level.getList())
		{
			obstacle = createObstacle(obst);
			mapObstacles.add(obstacle);
			program.add(obstacle);
		}
		player = level.getPlayer();
		program.add(player.getGOval());
	}
	
	public void moveMapObstacles(int hMove) {
		for(GRect current: mapObstacles) {
			current.move(hMove, 0);
		}
	}

	//change parameter to object once Object has been made
	public GRect createObstacle(Position p, Size s, Velocity v) {
		GRect objRec = new GRect(p.getX(), p.getY(), s.getWidth(), s.getHeight()); 
		objRec.setFillColor(Color.BLACK);
		objRec.setFilled(true);
		return objRec;
	}
	
	public GRect createObstacle(Obstacle obs) {
		GRect rec = new GRect(obs.getPosition().getX(), obs.getPosition().getY(), obs.getSize().getWidth(), obs.getSize().getHeight());
		rec.setFillColor(Color.BLACK);
		rec.setFilled(true);
		return rec;
	}
	
	public void run(MainApplication program) {
		setupLevel(program);
		tm.start();
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

	public void actionPerformed(ActionEvent e) {
		if(player.getGOval().getX() < 150 ) {
			vX = 1;
		} else if(player.getGOval().getX() > 650) {
			vX = -1;
		} else {
			vX = 0;
		}
		moveMapObstacles(vX);
		player.move();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.setCurrent(PlayerMovement.RIGHT);
			player.addForce();
			lastPressed = KeyEvent.VK_D;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player.setCurrent(PlayerMovement.LEFT);
			player.addForce();
			lastPressed = KeyEvent.VK_A;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			player.setCurrent(PlayerMovement.JUMP);
			player.addForce();
			player.addFriction();
			lastPressed = KeyEvent.VK_W;
		}
		player.move();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		/*if(e.getKeyCode() == lastPressed) {
			lastPressed = 99999;
		}
		*/
		player.addFriction();
	}
	
}
