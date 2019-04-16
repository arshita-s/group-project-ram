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
	private static final int PROGRAM_WIDTH = 800;
	private static final int PROGRAM_HEIGHT = 600;
	private static final double STANDARD_BLOCK_SIZE = PROGRAM_HEIGHT * 0.083;
	private static final double SPACE_BETWEEN_SETTINGS = PROGRAM_WIDTH * 0.005;
	private static final int TOP_SETTINGS_TAB_HEIGHT = (int) (STANDARD_BLOCK_SIZE + ( 2 * SPACE_BETWEEN_SETTINGS) ) * 2;
	private static final String FILENAME = "levelCreated.txt";
	
	private ArrayList<GObject> mapObstacles = new ArrayList<GObject>();
	private ArrayList<GObject> mapPlayer = new ArrayList<GObject>();
	private ArrayList<GObject> mapEnemies = new ArrayList<GObject>();
	private ArrayList<GObject> settings = new ArrayList<GObject>();
	private int screen = 1; // should equal 1 or more
	private GObject toDrag;
	private GObject toClick;
	private int lastX;
	private int lastY;
	private Formatter x;
	private Color obstacleColor = Color.BLACK;
	private Color playerColor = Color.YELLOW;
	private Color enemyColor = Color.RED;
	private Color moveLeftButtonColor = Color.ORANGE;
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

	private void setupSettings() {
		// top tab settings
		GRect settingsTab = new GRect(0, 0, PROGRAM_WIDTH, TOP_SETTINGS_TAB_HEIGHT);
		settingsTab.setFilled(true);
		settingsTab.setColor(Color.GRAY);
		add(settingsTab);
		
		//obstacle settings
		GRect o1 = new GRect(SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS, STANDARD_BLOCK_SIZE, STANDARD_BLOCK_SIZE);
		o1.setFilled(true);
		o1.setColor(obstacleColor);
		add(o1);
		settings.add(o1);
		
		GRect o2 = new GRect( rightPointOf(o1) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS, PROGRAM_WIDTH/4, STANDARD_BLOCK_SIZE);
		o2.setFilled(true);
		o2.setColor(obstacleColor);
		add(o2);
		settings.add(o2);
		
		GRect o3 = new GRect( rightPointOf(o2) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS, PROGRAM_WIDTH/2, STANDARD_BLOCK_SIZE);
		o3.setFilled(true);
		o3.setColor(obstacleColor);
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
		
		//player settings
		int bodyHeight = (int) (STANDARD_BLOCK_SIZE);
		int bodyWidth = (int) (0.6666666667 * bodyHeight);
		GRect player = new GRect(SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS*2 + STANDARD_BLOCK_SIZE, bodyWidth, bodyHeight);
		player.setFilled(true);
		player.setColor(playerColor);
		add(player);
		
		//enemy settings
		GRect enemy = new GRect(rightPointOf(player) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS*2 + STANDARD_BLOCK_SIZE, bodyWidth, bodyHeight);
		enemy.setFilled(true);
		enemy.setColor(enemyColor);
		add(enemy);
		
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e != null) {
			lastX = e.getX();
			lastY = e.getY();
			mousePressedObstacle(e);
			mousePressedPlayer(e);
			mousePressedEnemy(e);
		}
	}	
	public void mousePressedObstacle(MouseEvent e) {
		toDrag = getElementAt(e.getX(), e.getY());
		if (isObstacleInSettings(e)) {
			duplicateToDrag();
		}
	}
	public void mousePressedPlayer(MouseEvent e) {
		toDrag = getElementAt(e.getX(), e.getY());
		if (isPlayerInSettings(e)) {
			duplicateToDrag();
		}
	}
	public void mousePressedEnemy(MouseEvent e) {
		toDrag = getElementAt(e.getX(), e.getY());
		if (isEnemyInSettings(e)) {
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
		mouseDraggedPlayer(e);
		mouseDraggedEnemy(e);
	}
	public void mouseDraggedObstacle(MouseEvent e) {
		if (isObstacle(e)) {
			toDrag.move((e.getX()-lastX), (e.getY()-lastY));
			lastX = e.getX();
			lastY = e.getY();
		}
	}
	public void mouseDraggedPlayer(MouseEvent e) {
		if (isPlayer(e)) {
			toDrag.move((e.getX()-lastX), (e.getY()-lastY));
			lastX = e.getX();
			lastY = e.getY();
		}
	}
	public void mouseDraggedEnemy(MouseEvent e) {
		if (isEnemy(e)) {
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
		mouseReleasedPlayer(e);
		mouseReleasedEnemy(e);
		toDrag = null;
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
	}
	public void mouseReleasedPlayer(MouseEvent e) {
		if (isPlayerInSettings(e)) {
			removePlayer(toDrag);
		}
		if (isPlayerInMap(e)) {
			removePlayer(toDrag);
			add(toDrag);
			addPlayer(toDrag);
		}
	}
	public void mouseReleasedEnemy(MouseEvent e) {
		if (isEnemyInSettings(e)) {
			removeEnemy(toDrag);
		}
		if (isEnemyInMap(e)) {
			removeEnemy(toDrag);
			add(toDrag);
			addEnemy(toDrag);
		}
	}
	/*
	 * 
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		toClick = getElementAt(e.getX(), e.getY());
		mouseClickedSaveButton(e);
		mouseClickedMoveLeftButton(e);
		mouseClickedMoveRightButton(e);
		
	}
	public void mouseClickedSaveButton(MouseEvent e) {
		if(isSaveButton(e)) {
			saveMapToFile();
		}
	}
	public void mouseClickedMoveLeftButton(MouseEvent e) {
		if(isMoveLeftButton(e) && screen > 1) {
			moveObstacles(+1);
			movePlayer(+1);
			moveEnemies(+1);
			screen--;
		}
	}
	public void mouseClickedMoveRightButton(MouseEvent e) {
		if(isMoveRightButton(e)) {
			moveObstacles(-1);
			movePlayer(-1);
			moveEnemies(-1);
			screen++;
		}
	}
	
	private void saveMapToFile() {
		System.out.println("Save Button Clicked!");
		// Open file
		try {
			x = new Formatter(FILENAME);
		}
		catch(Exception e) {
			System.out.println("Error with textfile.");
		}
		// Write player in file
		x.format("\n*Player xPosition yPosition\n");
		for(GObject mP: mapPlayer) {
			x.format("Player %d %d\n", 
					(int)mP.getWidth(),  (int)mP.getHeight());
		}
		// Write enemy in file
		x.format("\n*Enemy health damage width length xPosition yPosition movesWithin\n");
		for(GObject mE: mapEnemies) {
			x.format("Enemy 1 1 %d %d %d %d 50\n", 
					(int)mE.getWidth(),  (int)mE.getHeight(), 
					(int)mE.getX() + (PROGRAM_WIDTH * (screen-1)), (int)mE.getY()-TOP_SETTINGS_TAB_HEIGHT);
		}
		// Write obstacle to file
		x.format("\n*Obstacle width length movesBoolean xPosition yPosition xVelocity yVelocity instantDeathBoolean\n");
		for(GObject mO: mapObstacles) {
			x.format("Obstacle %d %d false %d %d 0 0 false\n", 
					(int)mO.getWidth(),  (int)mO.getHeight(), 
					(int)mO.getX() + (PROGRAM_WIDTH * (screen-1)), (int)mO.getY()-TOP_SETTINGS_TAB_HEIGHT);
		}
		x.close();
	}
	private void duplicateToDrag() {
		GRect d = new GRect(toDrag.getX(), toDrag.getY(), toDrag.getWidth(), toDrag.getHeight());
		d.setFilled(true);
		d.setColor(toDrag.getColor());
		add(d);
	}

	//OBSTACLE
	public boolean isObstacle(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == obstacleColor);
	}
	private boolean isObstacleInMap(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == obstacleColor && e.getY() > TOP_SETTINGS_TAB_HEIGHT);
	}
	public boolean isObstacleInSettings(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == obstacleColor && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
	}
	private void moveObstacles(int direction) {
		for(GObject s: mapObstacles) {
			s.move(PROGRAM_WIDTH * direction, 0);
		}
	}
	private void removeObstacle(GObject o) {
		mapObstacles.remove(o);
		remove(o);
	}
	private void addObstacle(GObject o) {
		mapObstacles.add(o);
		add(o);
	}
	
	//PLAYER
	public boolean isPlayer(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == playerColor);
	}
	private boolean isPlayerInMap(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == playerColor && e.getY() > TOP_SETTINGS_TAB_HEIGHT);
	}
	public boolean isPlayerInSettings(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == playerColor && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
	}
	private void movePlayer(int direction) {
		for(GObject mP: mapPlayer) {
			mP.move(PROGRAM_WIDTH * direction, 0);
		}
	}
	private void removePlayer(GObject p) {
		mapPlayer.remove(p);
		remove(p);
	}
	private void addPlayer(GObject p) {
		mapPlayer.add(p);
		add(p);
	}
	
	//ENEMY
	public boolean isEnemy(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == enemyColor);
	}
	private boolean isEnemyInMap(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == enemyColor && e.getY() > TOP_SETTINGS_TAB_HEIGHT);
	}
	public boolean isEnemyInSettings(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GRect 
				&& toDrag.getColor() == enemyColor && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
	}
	private void moveEnemies(int direction) {
		for(GObject mE: mapEnemies) {
			mE.move(PROGRAM_WIDTH * direction, 0);
		}
	}
	private void removeEnemy(GObject e) {
		mapEnemies.remove(e);
		remove(e);
	}
	private void addEnemy(GObject e) {
		mapEnemies.add(e);
		add(e);
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
	private double rightPointOf(GObject o) {
		return (o.getX() + o.getWidth());
	}

}












