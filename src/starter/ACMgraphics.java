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
	public static final int MAX_BLOCK_HEIGHT = 10;
	public static final int PROGRAM_HEIGHT = 700; 
	public static final int PROGRAM_WIDTH = 1000;
	private static final int PIXELS_IN_BLOCK = PROGRAM_HEIGHT/MAX_BLOCK_HEIGHT;
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
	
	public GRect createObstacle(Obstacle obs) {
		//getting pixel measurements/coordinates
		Position p = convertSpaceToXY(obs.getPosition().getX(), obs.getPosition().getY());
		Position s = convertSpaceToXY(obs.getSize().getWidth(), obs.getSize().getHeight());
		GRect rec = new GRect(p.getX(), p.getY(), obs.getSize().getWidth() * PIXELS_IN_BLOCK, obs.getSize().getHeight() * PIXELS_IN_BLOCK);
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
	//ConvertPixelToStandardBlockSize
	public int convertPTSBS(int pixels) {
		if(pixels%PIXELS_IN_BLOCK != 0) return 0;
		int blocks = pixels / PIXELS_IN_BLOCK;
		return blocks;
	}
	//ConvertStandardBlockSizeToPixel
	public int convertSBSTP(int block) {
		int pixels = block * PIXELS_IN_BLOCK;
		return pixels;
	}
	private Position convertXYToSpace(int x, int y) {
		int row = (int) (y / PIXELS_IN_BLOCK);  
		int col = (int) (x / PIXELS_IN_BLOCK);
		if (row >=  PIXELS_IN_BLOCK) row = PIXELS_IN_BLOCK - 1;
		if (col >= PIXELS_IN_BLOCK) col = PIXELS_IN_BLOCK - 1;
		return new Position(row, col);
	}
	private Position convertSpaceToXY(int x, int y) {
		return new Position(x *  PIXELS_IN_BLOCK - PIXELS_IN_BLOCK, y * PIXELS_IN_BLOCK - PIXELS_IN_BLOCK);
	}
}























