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
	private double vX = 0;
	private PlayerMovement lastPressed;
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
		// adding obstacles to map
		GRect obstacle;
		for (Obstacle obst : level.getList()) {
			obstacle = createObstacle(obst);
			mapObstacles.add(obstacle);
			program.add(obstacle);
		}
		player = level.getPlayer();
		program.add(player.getGOval());
	}

	public void moveMapObstacles(double hMove) {
		for (GRect current : mapObstacles) {
			current.move(hMove, 0);
		}
	}

	// change parameter to object once Object has been made
	public GRect createObstacle(Position p, Size s, Velocity v) {
		GRect objRec = new GRect(p.getX(), p.getY(), s.getWidth(), s.getHeight());
		objRec.setFillColor(Color.BLACK);
		objRec.setFilled(true);
		return objRec;
	}

	public GRect createObstacle(Obstacle obs) {
		GRect rec = new GRect(obs.getPosition().getX(), obs.getPosition().getY(), obs.getSize().getWidth(),
				obs.getSize().getHeight());
		rec.setFillColor(Color.BLACK);
		rec.setFilled(true);
		return rec;
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
		player.move();
		player.addFriction();
		player.processGravity();
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
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		player.setCurrentMove(PlayerMovement.STANDING);
		player.setCurrentJump(PlayerJump.STAND);
	}

}
