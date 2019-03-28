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
/*	
	public void setupLevel() {
		//creating map
		map = new GCanvas();
<<<<<<< HEAD
=======
		map.setVisible(true);
>>>>>>> branch 'master' of https://github.com/comp55-spr19/group-project-ram.git
		map.setLocation(0, 0);
		
		//adding obstacles to map
		GRect obstacle;
		
		obstacle = createObstacle(new Position(80,50), new Size(50, 15), new Velocity(0,0));
		map.add(obstacle);
		obstacle = createObstacle(new Position(0,0), new Size(50, 15), new Velocity(0,0));
		map.add(obstacle);
		
		//revalidating map
		add(map);
		map.revalidate();
		
		System.out.println("Map coordinate: x" + map.getX() + " y" + map.getY());
	}
		/* ERROR:
		 * for some reason "map"'s position for the x coordinate is not 0. why?
		 */ 
	
	
	public void run(MainApplication program) {
		setupLevel(program);
		tm.start();
		while(!playerAtEnd()) {
			//TODO all the player processing stuff like
			// the constant moving at 0
			player.move(0, 0);
			
		}
	}
	

	private boolean playerAtEnd() {
		//TODO check to see if player has finished the level.
		return true;
	}

	public void actionPerformed(ActionEvent e) {
		//TODO Also check to see if its the start/end of the map or not.
		if(player.getGOval().getX() < 150 ) {
			vX = 1;
		} else if(player.getGOval().getX() > 650) {
			vX = -1;
		} else {
			vX = 0;
		}
		moveMapObstacles(vX);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.setCurrent(PlayerMovement.RIGHT);
			player.addForce();
			//vX = -1;
			lastPressed = KeyEvent.VK_D;
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			player.setCurrent(PlayerMovement.LEFT);
			player.addForce();
			//vX = 1;
			lastPressed = KeyEvent.VK_A;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.setCurrent(PlayerMovement.JUMP);
			player.addForce();
			lastPressed = KeyEvent.VK_SPACE;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == lastPressed) {
			//vX = 0;
			lastPressed = 99999;
		}
	}
}
