package starter;

import java.util.*;
import java.io.*;

public class Map {

	private static final String FILENAME = "level.txt";
	
	private ArrayList<Obstacle> Obstacles;
	private ArrayList<Enemy> Enemies;
	private Player mainPlayer;
	
	public Map() {
		Obstacles = new ArrayList<Obstacle>();
		Enemies = new ArrayList<Enemy>();
		this.readFromFile();
	}
	
	public void readFromFile() {
		File file = new File("");
		try {
			file = new File(System.getProperty("user.dir") + "//" + FILENAME);
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
					
					Obstacle temp = new Obstacle(width, length, moves, x, y, velocityx, velocityy, instantDeath);
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
					Enemy temp = new Enemy(health, damage, new Size(width, height), new Position(xPos, yPos), 50);
					Enemies.add(temp);
				}
			}
			read.close();
		} catch (Exception e) {
			
		}
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
}
