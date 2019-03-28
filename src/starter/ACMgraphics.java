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
	
	public GRect createObstacle(Obstacle obs) {
		//getting pixel measurements/coordinates
		Position p = convertSpaceToXY(obs.getPosition().getX(), obs.getPosition().getY());
		Position s = convertSpaceToXY(obs.getSize().getWidth(), obs.getSize().getHeight());
		GRect rec = new GRect(p.getX(), p.getY(), obs.getSize().getWidth() * PIXELS_IN_BLOCK, obs.getSize().getHeight() * PIXELS_IN_BLOCK);
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
	
	/*@Override
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
			player.setCurrent(PlayerMovement.JUMPSTANDING);
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
<<<<<<< HEAD
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
=======
	*/
>>>>>>> branch 'master' of https://github.com/comp55-spr19/group-project-ram.git
}























