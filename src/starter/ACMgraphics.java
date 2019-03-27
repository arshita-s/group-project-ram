package starter;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.Timer;
// Here I will take obstacles and put them on the screen
public class ACMgraphics extends GraphicsProgram implements ActionListener, KeyListener {
	public static final int PROGRAM_HEIGHT = 700;
	public static final int PROGRAM_WIDTH = 700;
	private ArrayList<GRect> mapObstacles;
	private Map level;
	private int vX = 0;
	private int lastPressed = 0;
	Timer tm = new Timer(10, this);
	
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
		this.addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	public void setupLevel() {
		
		level = new Map();
		//adding obstacles to map
		mapObstacles = new ArrayList<GRect>();
		GRect obstacle;
		
		for(Obstacle obst: level.getList())
		{
			obstacle = createObstacle(obst);
			mapObstacles.add(obstacle);
			add(obstacle);
		}
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
	
	public void run() {
		setupLevel();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		tm.start();
	}
	public void actionPerformed(ActionEvent e) {
		moveMapObstacles(vX);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			vX = -1;
			lastPressed = KeyEvent.VK_D;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			vX = 1;
			lastPressed = KeyEvent.VK_A;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == lastPressed) {
			vX = 0;
			lastPressed = 99999;
		}
	}
}
