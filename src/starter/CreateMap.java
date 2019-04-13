package starter;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Formatter;

import javax.swing.Timer;
import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GImage;

/*
 * This class helps create maps. I still need to fix the obstacles skin but for now
 * this will do. The green GOval saves all of the obstacles in the white area to a
 * text file called "levelCreated.txt" which should be located in the "bin" folder of
 * the COMP55GroupProject. If you want to remove an obstacle then place it in the
 * gray area. If you want to make obstacles for a certain screen then just change
 * line 39 to that screen.
 */

public class CreateMap extends GraphicsProgram {
	private static final int PROGRAM_WIDTH = 1200;
	private static final int PROGRAM_HEIGHT = 600;
	private static final int SETTINGS_HEIGHT = 200;
	private static final int SCREEN = 1; // should equal 1 or more
	private static final String FILENAME = "levelCreated.txt";
	
	private ArrayList<GObject> mapObstacles = new ArrayList<GObject>();
	private ArrayList<GObject> settings = new ArrayList<GObject>();
	private GObject toDrag;
	private GObject toClick;
	private int lastX;
	private int lastY;
	private Formatter x;
	
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT+SETTINGS_HEIGHT);
		requestFocus();
	}
	public void run() {
		setupSettings();
		add(new GLabel("Hello World", 300, 300));
		addMouseListeners();
	}
	private void removeObstacle(GObject o) {
		mapObstacles.remove(o);
		remove(o);
	}
	private void addObstacle(GObject o) {
		mapObstacles.add(o);
		add(o);
	}
	private void setupSettings() {
		//tab
		GRect settingsTab = new GRect(0, 0, 1200, 200);
		settingsTab.setFilled(true);
		settingsTab.setColor(Color.GRAY);
		add(settingsTab);
		//obstacle settings
		GRect s1 = new GRect(10, 10, 50, 50);
		s1.setFilled(true);
		s1.setColor(Color.BLACK);
		add(s1);
		settings.add(s1);
		
		GRect s2 = new GRect(70, 10, 550, 50);
		s2.setFilled(true);
		s2.setColor(Color.BLACK);
		add(s2);
		settings.add(s2);
		
		//save button
		int spaceFromSide = 200;
		GOval button = new GOval(PROGRAM_WIDTH - 100, 0, spaceFromSide/2, spaceFromSide/2);
		button.setFilled(true);
		button.setColor(Color.GREEN);
		add(button);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		lastX = e.getX();
		lastY = e.getY();
		mousePressedObstacle(e);
	}	
	public void mousePressedObstacle(MouseEvent e) {
		toDrag = getElementAt(e.getX(), e.getY());
		if (isObstacleInSettings(e)) {
			duplicateToDrag();
		}
	}
	/*
	 * 
	 * 
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseDraggedObstacle(e);
	}
	public void mouseDraggedObstacle(MouseEvent e) {
		if (isObstacle(e)) {
			toDrag.move((e.getX()-lastX), (e.getY()-lastY));
			lastX = e.getX();
			lastY = e.getY();
		}
	}
	/*
	 * 
	 * 
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseReleasedObstacle(e);
	}
	public void mouseReleasedObstacle(MouseEvent e) {
		if (isObstacleInSettings(e)) {
			removeObstacle(toDrag);
		}
		if (isObstacleInMap(e)) {
			removeObstacle(toDrag);
			add(toDrag);
			addObstacle(toDrag);
		}
		toDrag = null;
	}
	/*
	 * 
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClickedSaveButton(e);
	}
	public void mouseClickedSaveButton(MouseEvent e) {
		System.out.println("Mouse clicked.");
		toClick = getElementAt(e.getX(), e.getY());
		if(isSaveButton(e)) {
			saveObstaclesToFile();
		}
	}
	
	private void saveObstaclesToFile() {
		System.out.println("Save Button Clicked!");
		// Open file
		try {
			x = new Formatter(FILENAME);
		}
		catch(Exception e) {
			System.out.println("Error with textfile.");
		}
		// Write obstacle to file
		for(GObject s: mapObstacles) {
			x.format("Obstacle %d %d false %d %d 0 0 false\n", 
					(int)s.getWidth(),  (int)s.getHeight(), 
					(int)s.getX() + (PROGRAM_WIDTH * (SCREEN-1)), (int)s.getY()-SETTINGS_HEIGHT);
		}
		// Close file
		x.close();
	}
	private void duplicateToDrag() {
		GRect d = new GRect(toDrag.getX(), toDrag.getY(), toDrag.getWidth(), toDrag.getHeight());
		d.setFilled(true);
		d.setColor(Color.BLACK);
		add(d);
	}
	public GObject getObjectSettingFromXY(int x, int y) {
		for(GObject s: settings) {
			if (s.getX() >= x && x <= s.getX()+s.getWidth()
					&& s.getY() >= y && y <= s.getY()+s.getHeight()) return s;
		}
		return null;
	}
	public boolean isObstacleInSettings(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == Color.BLACK && e.getY() <= SETTINGS_HEIGHT);
	}
	public boolean isObstacle(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == Color.BLACK);
	}
	private boolean isObstacleInMap(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == Color.BLACK && e.getY() > SETTINGS_HEIGHT);
	}
	private boolean isSaveButton(MouseEvent e) {
		return (toClick != null && toClick instanceof GOval 
				&&  toClick.getColor() == Color.GREEN && e.getY() <= SETTINGS_HEIGHT);
	}

}