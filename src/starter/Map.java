package starter;

import java.util.*;
import java.io.*;

public class Map {

	private static final String FILENAME = "level_";
	private static final String EXTENSION = ".txt";
	
	private ArrayList<Obstacle> Obstacles;
	private ArrayList<Enemy> Enemies;
	private ArrayList<Mask> Masks;
	private Player mainPlayer;
	
	public Map() {
		Obstacles = new ArrayList<Obstacle>();
		Enemies = new ArrayList<Enemy>();
		Masks = new ArrayList<Mask>();
		this.readFromFile(1);
	}
	
	public void readFromFile(int level) {
		this.resetAllLists();
		File file = new File("");
		try {
			file = new File(System.getProperty("user.dir") + "//" + FILENAME + level + EXTENSION);
			Scanner read = new Scanner(new FileReader(file));
			
			while(read.hasNextLine()) {
				String str = read.next();
				
				if(str.equals("Obstacle")) {
					int width = read.nextInt();
					int length = read.nextInt();
					boolean moves = read.nextBoolean();
					int x = read.nextInt();
					int y = read.nextInt();
					int velocityx = read.nextInt();
					int velocityy = read.nextInt();
					boolean instantDeath = read.nextBoolean();
					String skin = read.next();
					boolean visible = read.nextBoolean();
					Obstacle temp = new Obstacle(width, length, moves, x, y, velocityx, velocityy, instantDeath, skin, visible);
					Obstacles.add(temp);
				}
				
				if(str.equals("Player")) {
					int x = read.nextInt();
					int y = read.nextInt();
					mainPlayer = new Player(x, y);
				}
				//(int health, int damage, Size size, Position position, movesWithin)
				if(str.equals("Enemy")) {
					int health = read.nextInt();
					int damage = read.nextInt();
					int width = read.nextInt();
					int height = read.nextInt();
					int xPos = read.nextInt();
					int yPos = read.nextInt();
					int movesWithin = read.nextInt();
					Enemy temp = new Enemy(health, damage, new Size(width, height), new Position(xPos, yPos), movesWithin);
					Enemies.add(temp);
				}
				if(str.equals("Mask")) {
					int xPos = read.nextInt();
					int yPos = read.nextInt();
					int width = read.nextInt();
					int height = read.nextInt();
					Mask mask = new Mask(new Position(xPos, yPos), new Size(width, height));
					Masks.add(mask);
				}
			}
			read.close();
		} catch (Exception e) {
			
		}
	}
	
	public void resetAllLists() {
		Obstacles.clear();
		Enemies.clear();
		Masks.clear();
	}
	
	public ArrayList<Obstacle> getObstacleList() {
		return Obstacles;
	}
	public ArrayList<Enemy> getEnemyList() {
		return Enemies;
	}
	public Player getPlayer() {
		return mainPlayer;
	}
	public ArrayList<Mask> getMaskList() {
		return Masks;
	}
}
