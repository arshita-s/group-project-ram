package starter;

import java.util.*;
import java.io.*;

public class Map {

	private static final String FILENAME = "level.txt";
	
	private ArrayList<Obstacle> Obstacles;
	
	public Map() {
		Obstacles = new ArrayList<Obstacle>();
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
			}
			read.close();
		} catch (Exception e) {
			
		}
	}
	
	public ArrayList<Obstacle> getList() {
		return Obstacles;
	}
	
}
