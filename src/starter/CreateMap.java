package starter;
import acm.graphics.*;
import acm.program.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;

import acm.graphics.GObject;

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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int PROGRAM_WIDTH = 800;
	private static final int PROGRAM_HEIGHT = 600;
	private static final double STANDARD_BLOCK_SIZE = PROGRAM_HEIGHT * 0.08333333333333333333333333333;
	private static final double SPACE_BETWEEN_SETTINGS = PROGRAM_WIDTH * 0.005;
	private static final int TOP_SETTINGS_TAB_HEIGHT = (int) (STANDARD_BLOCK_SIZE + ( 2 * SPACE_BETWEEN_SETTINGS) ) * 1;
	private static final String FILENAME = "levelCreated.txt";
	
	private ArrayList<GObject> mapObstacles = new ArrayList<GObject>();
	private ArrayList<GObject> mapPlayer = new ArrayList<GObject>();
	private ArrayList<GObject> mapEnemies = new ArrayList<GObject>();
	private ArrayList<GObject> mapMask = new ArrayList<GObject>();
	private ArrayList<GObject> settings = new ArrayList<GObject>();
	private int screen = 1;
	private GObject toDrag;
	private GObject toClick;
	private int lastX;
	private int lastY;
	private Formatter x;
	private HashMap<GImageType, Color> type = new HashMap<GImageType, Color>();
	private static int colorCounter = 0;
	
	public void init() {
		setSize(PROGRAM_WIDTH+TOP_SETTINGS_TAB_HEIGHT, PROGRAM_HEIGHT+TOP_SETTINGS_TAB_HEIGHT);
		requestFocus();
	}
	public void run() {
		setupSettings();
		addMouseListeners();
	}

	private void setUpHashMap() {
		type.put(GImageType.LEFT_BUTTON_SCREEN, getNewColor());
		type.put(GImageType.RIGHT_BUTTON_SCREEN, getNewColor());
		type.put(GImageType.LEFT_BUTTON_SETTINGS, getNewColor());
		type.put(GImageType.RIGHT_BUTTON_SETTINGS, getNewColor());
		type.put(GImageType.DOWNLOAD_BUTTON, getNewColor());
		type.put(GImageType.SAVE_BUTTON, getNewColor());
		type.put(GImageType.PLAYER, getNewColor());
		type.put(GImageType.ENEMY, getNewColor());
		type.put(GImageType.OBSTACLE, getNewColor());
		type.put(GImageType.MASK, getNewColor());
	}
	private Color getNewColor() {
		colorCounter++;
		return new Color((colorCounter%255)%255, colorCounter%255, colorCounter/255);
	}
	private void setGImageType(GImage gI, GImageType gT) {
		gI.setColor(type.get(gT));
	}
	private double row(int i) {
		double row = (SPACE_BETWEEN_SETTINGS * i) + (i-1) * STANDARD_BLOCK_SIZE;
		
		return row;
	}
	private void setupSettings() {
		setUpHashMap();
		// top settings tab
		GRect topST = new GRect(0, 0, PROGRAM_WIDTH, TOP_SETTINGS_TAB_HEIGHT);
		topST.setFilled(true);
		topST.setColor(Color.GRAY);
		add(topST);
		settings.add(topST);
		
		// right tab settings
		GRect rightST = new GRect(800, 0, TOP_SETTINGS_TAB_HEIGHT, this.getHeight());
		rightST.setFilled(true);
		rightST.setColor(Color.GRAY);
		add(rightST);
		settings.add(rightST);
		
		GImage or1 = new GImage("purple_grass_dirt_1x6.png" , PROGRAM_WIDTH + SPACE_BETWEEN_SETTINGS, TOP_SETTINGS_TAB_HEIGHT + SPACE_BETWEEN_SETTINGS);
		or1.setSize(STANDARD_BLOCK_SIZE, STANDARD_BLOCK_SIZE*6);
		or1.setColor(type.get(GImageType.OBSTACLE));
		add(or1);
		settings.add(or1);
		
		GImage or2 = new GImage("purple_grass_dirt_1x3.png" , PROGRAM_WIDTH + SPACE_BETWEEN_SETTINGS, bottomPointOf(or1) + SPACE_BETWEEN_SETTINGS);
		or2.setSize(STANDARD_BLOCK_SIZE, STANDARD_BLOCK_SIZE*3);
		or2.setColor(type.get(GImageType.OBSTACLE));
		add(or2);
		settings.add(or2);
		
		GImage or3 = new GImage("maniaxe_mask.png" , PROGRAM_WIDTH + SPACE_BETWEEN_SETTINGS, bottomPointOf(or2) + SPACE_BETWEEN_SETTINGS);
		or3.setSize(STANDARD_BLOCK_SIZE * 2/3, STANDARD_BLOCK_SIZE*2/3);
		or3.setColor(type.get(GImageType.MASK));
		add(or3);
		settings.add(or3);
		
		//obstacle settings
		GImage o1 = new GImage("purple_grass_dirt_1x1.png", SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS);
		o1.setSize(STANDARD_BLOCK_SIZE, STANDARD_BLOCK_SIZE);
		setGImageType(o1, GImageType.OBSTACLE);
		add(o1);
		settings.add(o1);
		
		GImage o2 = new GImage("purple_grass_dirt_4x1.png" , rightPointOf(o1) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS); 
		o2.setSize(STANDARD_BLOCK_SIZE*4, STANDARD_BLOCK_SIZE);
		o2.setColor(type.get(GImageType.OBSTACLE));
		add(o2);
		settings.add(o2);
		
		GImage o3 = new GImage("purple_grass_dirt_6x1.png" , rightPointOf(o2) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS);
		o3.setSize(STANDARD_BLOCK_SIZE*6, STANDARD_BLOCK_SIZE);
		o3.setColor(type.get(GImageType.OBSTACLE));
		add(o3);
		settings.add(o3);
		
		
		double rightSpaceLeft = PROGRAM_WIDTH - rightPointOf(o3);
		double buttonSize = ( rightSpaceLeft - (SPACE_BETWEEN_SETTINGS * 4) ) / 3;

		//player settings
		int bodyHeight = (int) (STANDARD_BLOCK_SIZE);
		int bodyWidth = (int) (0.6666666667 * bodyHeight);
		
		GImage player = new GImage(System.getProperty("user.dir") + "/PlayerAnimations/player_walk_right_1.png", rightPointOf(o3) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS);
		player.setSize(bodyWidth, bodyHeight);
		setGImageType(player, GImageType.PLAYER);
		add(player);
		settings.add(player);
		
		//enemy settings
		GImage enemy = new GImage(System.getProperty("user.dir") + "/EnemyAnimations/enemy_walk_left_1.png", rightPointOf(player) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS);
		enemy.setSize(bodyWidth, bodyHeight);
		setGImageType(enemy, GImageType.ENEMY);
		add(enemy);
		settings.add(enemy);
	
		buttonSize *= .9;
		//move left button screen
		GImage moveLB = new GImage("left_button_screen.png", rightPointOf(enemy) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS);
		moveLB.setSize(buttonSize, STANDARD_BLOCK_SIZE);
		setGImageType(moveLB, GImageType.LEFT_BUTTON_SCREEN);
		add(moveLB);
		settings.add(moveLB);
		
		//move right button screen
		GImage moveRB = new GImage("right_button_screen.png", rightPointOf(moveLB) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS);
		moveRB.setSize(buttonSize, STANDARD_BLOCK_SIZE);
		setGImageType(moveRB, GImageType.RIGHT_BUTTON_SCREEN);
		add(moveRB);
		settings.add(moveRB);
		
		//save button
		GImage saveButton = new GImage("save_button.png", rightPointOf(moveRB) + SPACE_BETWEEN_SETTINGS, SPACE_BETWEEN_SETTINGS);
		saveButton.setSize(buttonSize, STANDARD_BLOCK_SIZE);
		setGImageType(saveButton, GImageType.SAVE_BUTTON);
		add(saveButton);
		settings.add(saveButton);
		
		
		
	}
	private void prioritizeSettings() {
		for(GObject s: settings) {
			remove(s);
			add(s);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e != null) {
			lastX = e.getX();
			lastY = e.getY();
			mousePressedObstacle(e);
			mousePressedPlayer(e);
			mousePressedEnemy(e);
			mousePressedMask(e);
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
	public void mousePressedMask(MouseEvent e) {
		toDrag = getElementAt(e.getX(), e.getY());
		if (isMaskInSettings(e)) {
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
		mouseDraggedMask(e);
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
	public void mouseDraggedMask(MouseEvent e) {
		if (isMask(e)) {
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
		mouseReleasedMask(e);
		//prioritizeSettings();

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
	public void mouseReleasedMask(MouseEvent e) {
		if (isMaskInSettings(e)) {
			removeMask(toDrag);
		}
		if (isMaskInMap(e)) {
			System.out.println("I am called");

			removeMask(toDrag);
			add(toDrag);
			addMask(toDrag);
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
			moveMask(+1);
			screen--;
		}
	}
	public void mouseClickedMoveRightButton(MouseEvent e) {
		if(isMoveRightButton(e)) {
			moveObstacles(-1);
			movePlayer(-1);
			moveEnemies(-1);
			moveMask(-1);
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
					(int)mP.getX()+ (PROGRAM_WIDTH * (screen-1)),  (int)mP.getY()-TOP_SETTINGS_TAB_HEIGHT);
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
			x.format("Obstacle %d %d false %d %d 0 0 false %s true\n", 
					(int)mO.getWidth(),  (int)mO.getHeight(), (int)mO.getX() + (PROGRAM_WIDTH * (screen-1)), 
					(int)mO.getY()-TOP_SETTINGS_TAB_HEIGHT, filename(mO));
		}
		// Write mask to file
		x.format("\n*Mask xPos yPos\n");
		for(GObject mO: mapMask) {
			x.format("Mask %d %d\n", 
					(int)mO.getX()+ (PROGRAM_WIDTH * (screen-1)),  (int)mO.getY()-TOP_SETTINGS_TAB_HEIGHT);
		}
		x.close();
	}
	private String filename(GObject mP) {
		return "purple_grass_dirt_" + (int) mP.getWidth()/50 + "x" + (int) mP.getHeight()/50 + ".png";
	}
 	private void duplicateToDrag() {
		if (toDrag instanceof GRect) { 
			GRect d = new GRect(toDrag.getX(), toDrag.getY(), toDrag.getWidth(), toDrag.getHeight());
			d.setColor(toDrag.getColor());
			add(d);
		}
		else if (toDrag instanceof GImage) {
			GImage d = new GImage(((GImage) toDrag).getImage(), toDrag.getX(), toDrag.getY());
			d.setSize(toDrag.getWidth(), toDrag.getHeight());
			d.setColor(toDrag.getColor());
			add(d);
		}
	}

	//OBSTACLE
	public boolean isObstacle(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GImage 
				&& toDrag.getColor() == type.get(GImageType.OBSTACLE));
	}
	private boolean isObstacleInMap(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GImage 
				&& toDrag.getColor() == type.get(GImageType.OBSTACLE) && e.getY() > TOP_SETTINGS_TAB_HEIGHT && e.getX() <= PROGRAM_WIDTH);
	}
	public boolean isObstacleInSettings(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GImage 
				&& toDrag.getColor() == type.get(GImageType.OBSTACLE) && (e.getY() <= TOP_SETTINGS_TAB_HEIGHT) || e.getX() > PROGRAM_WIDTH);
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
		return (toDrag != null && toDrag instanceof GImage 
				&& toDrag.getColor() == type.get(GImageType.PLAYER));
	}
	private boolean isPlayerInMap(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GImage 
				&& toDrag.getColor() == type.get(GImageType.PLAYER) && e.getY() > TOP_SETTINGS_TAB_HEIGHT);
	}
	public boolean isPlayerInSettings(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GImage 
				&& toDrag.getColor() == type.get(GImageType.PLAYER) && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
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
		return (toDrag != null && toDrag instanceof GImage 
				&& toDrag.getColor() == type.get(GImageType.ENEMY));
	}
	private boolean isEnemyInMap(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GImage 
				&& toDrag.getColor() == type.get(GImageType.ENEMY) && e.getY() > TOP_SETTINGS_TAB_HEIGHT);
	}
	public boolean isEnemyInSettings(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GImage 
				&& toDrag.getColor() == type.get(GImageType.ENEMY) && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
	}
	private void moveEnemies(int direction) {
		for(GObject mP: mapEnemies) {
			mP.move(PROGRAM_WIDTH * direction, 0);
		}
	}
	private void removeEnemy(GObject p) {
		mapEnemies.remove(p);
		remove(p);
	}
	private void addEnemy(GObject p) {
		mapEnemies.add(p);
		add(p);
	}
	
	//MASK
	public boolean isMask(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GImage 
				&& toDrag.getColor() == type.get(GImageType.MASK));
	}
	private boolean isMaskInMap(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GImage 
				&& toDrag.getColor() == type.get(GImageType.MASK) && e.getY() > TOP_SETTINGS_TAB_HEIGHT);
	}
	public boolean isMaskInSettings(MouseEvent e) {
		return (toDrag != null && toDrag instanceof GImage 
				&& toDrag.getColor() == type.get(GImageType.MASK) && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
	}
	private void moveMask(int direction) {
		for(GObject mM: mapMask) {
			mM.move(PROGRAM_WIDTH * direction, 0);
		}
	}
	private void removeMask(GObject p) {
		mapMask.remove(p);
		remove(p);
	}
	private void addMask(GObject p) {
		mapMask.add(p);
		add(p);
	}
	
	
	
	
	
	
	private boolean isSaveButton(MouseEvent e) {
		return (toClick != null && toClick instanceof GImage 
				&&  toClick.getColor() == type.get(GImageType.SAVE_BUTTON) && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
	}
	private boolean isMoveLeftButton(MouseEvent e) {
		return (toClick != null && toClick instanceof GImage 
				&&  toClick.getColor() == type.get(GImageType.LEFT_BUTTON_SCREEN) && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
	}
	private boolean isMoveRightButton(MouseEvent e) {
		return (toClick != null && toClick instanceof GImage 
				&&  toClick.getColor() == type.get(GImageType.RIGHT_BUTTON_SCREEN) && e.getY() <= TOP_SETTINGS_TAB_HEIGHT);
	}
	private double rightPointOf(GObject o) {
		return (o.getX() + o.getWidth());
	}
	private double bottomPointOf(GObject o) {
		return (o.getY() + o.getHeight());
	}

}












