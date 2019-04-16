package starter;
import acm.graphics.*;
import acm.program.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Formatter;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;

/*
 * This class helps create maps. I still need to fix the obstacles skin but for now
 * this will do. The green GOval saves all of the obstacles in the white area to a
 * text file called "levelCreated.txt" which should be located in the "bin" folder of
 * the COMP55GroupProject. If you want to remove an obstacle then place it in the
 * gray area.
 * 
 * NEW: I added two buttons. Red lets you move left a screen and Blue lets you move 
 * 		one screen right.
 */

public class CreateMap extends GraphicsProgram {
	private static final int PROGRAM_WIDTH = 1200;
	private static final int PROGRAM_HEIGHT = 600;
	private static final double STANDARD_BLOCK_SIZE = PROGRAM_HEIGHT * 0.083;
	private static final double SPACE_BETWEEN_SETTINGS = PROGRAM_WIDTH * 0.005;
	private static final int TOP_SETTINGS_TAB_HEIGHT = (int) (STANDARD_BLOCK_SIZE + ( 2 * SPACE_BETWEEN_SETTINGS) );
	private static final String FILENAME = "levelCreated.txt";
	
	private ArrayList<GObject> mapObstacles = new ArrayList<GObject>();
	private ArrayList<GObject> settings = new ArrayList<GObject>();
	private int screen = 1; // should equal 1 or more
	private GObject toDrag;
	private GObject toClick;
	private int lastX;
	private int lastY;
	private Formatter x;
	private Color moveLeftButtonColor = Color.RED;
	private Color moveRightButtonColor = Color.BLUE;
	private Color saveButtonColor = Color.GREEN;
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT+TOP_SETTINGS_TAB_HEIGHT);
		requestFocus();
	}
	public void run() {
		setupSettings();
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
		// top tab settings
		GRect settingsTab = new GRect(0, 0, 1200, TOP_SETTINGS_TAB_HEIGHT);
		settingsTab.setFilled(true);
		settingsTab.setColor(Color.GRAY);
		add(settingsTab);
		
		//obstacle settings
		GRect o1 = new GRect(SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS, STANDARD_BLOCK_SIZE, STANDARD_BLOCK_SIZE);
		o1.setFilled(true);
		o1.setColor(Color.BLACK);
		add(o1);
		settings.add(o1);
		
		GRect o2 = new GRect( rightPointOf(o1) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS, PROGRAM_WIDTH/4, STANDARD_BLOCK_SIZE);
		o2.setFilled(true);
		o2.setColor(Color.BLACK);
		add(o2);
		settings.add(o2);
		
		GRect o3 = new GRect( rightPointOf(o2) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS, PROGRAM_WIDTH/2, STANDARD_BLOCK_SIZE);
		o3.setFilled(true);
		o3.setColor(Color.BLACK);
		add(o3);
		settings.add(o3);
		
		double rightSpaceLeft = PROGRAM_WIDTH - rightPointOf(o3);
		double buttonSize = ( rightSpaceLeft - (SPACE_BETWEEN_SETTINGS * 4) ) / 3;
		
		//move left button
		GOval moveLB = new GOval(rightPointOf(o3) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS, buttonSize, STANDARD_BLOCK_SIZE);
		moveLB.setFilled(true);
		moveLB.setColor(moveLeftButtonColor);
		add(moveLB);
		
		//move right button
		GOval moveRB = new GOval(rightPointOf(moveLB) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS, buttonSize, STANDARD_BLOCK_SIZE);
		moveRB.setFilled(true);
		moveRB.setColor(moveRightButtonColor);
		add(moveRB);
		
		//save button
		GOval saveButton = new GOval(rightPointOf(moveRB) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS, buttonSize, STANDARD_BLOCK_SIZE);
		saveButton.setFilled(true);
		saveButton.setColor(saveButtonColor);
		add(saveButton);
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
		System.out.println("Mouse clicked.");
		toClick = getElementAt(e.getX(), e.getY());
		mouseClickedSaveButton(e);
		mouseClickedMoveLeftButton(e);
		mouseClickedMoveRightButton(e);
		
	}
	public void mouseClickedSaveButton(MouseEvent e) {
		if(isSaveButton(e)) {
			saveObstaclesToFile();
		}
	}
	public void mouseClickedMoveLeftButton(MouseEvent e) {
		if(isMoveLeftButton(e) && screen > 1) {
			moveObstacles(+1);
			screen--;
		}
	}
	public void mouseClickedMoveRightButton(MouseEvent e) {
		if(isMoveRightButton(e)) {
			moveObstacles(-1);
			screen++;
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
					(int)s.getX() + (PROGRAM_WIDTH * (screen-1)), (int)s.getY()-TOP_SETTINGS_TAB_HEIGHT);
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
				&& toDrag.getColor() == Color.BLACK && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
	}
	public boolean isObstacle(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == Color.BLACK);
	}
	private boolean isObstacleInMap(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == Color.BLACK && e.getY() > TOP_SETTINGS_TAB_HEIGHT);
	}
	private boolean isSaveButton(MouseEvent e) {
		return (toClick != null && toClick instanceof GOval 
				&&  toClick.getColor() == saveButtonColor && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
	}
	private boolean isMoveLeftButton(MouseEvent e) {
		return (toClick != null && toClick instanceof GOval 
				&&  toClick.getColor() == moveLeftButtonColor && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
	}
	private boolean isMoveRightButton(MouseEvent e) {
		return (toClick != null && toClick instanceof GOval 
				&&  toClick.getColor() == moveRightButtonColor && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
	}
	private void moveObstacles(int direction) {
		for(GObject s: mapObstacles) {
			s.move(PROGRAM_WIDTH * direction, 0);
		}
	}
	private double rightPointOf(GObject o) {
		return (o.getX() + o.getWidth());
	}

}




































