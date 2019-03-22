package starter;

import java.util.*;
import java.io.*;

public class Map {

	private static final String FILENAME = "level.txt";
	
	private ArrayList<Obstacle> Obstacles;
	
	public Map() {
		Obstacles = new ArrayList<Obstacle>();
	}
	
	public void readFromFile() {
		File file = new File("");
		try {
			file = new File(System.getProperty("user.dir") + "//" + FILENAME);
			Scanner read = new Scanner(new FileReader(file));
			while(read.hasNextLine()) {
				String str = read.next();
				if(str.equals("Obstacle")) {
					System.out.println(str);
					Obstacle temp = new Obstacle();
				}
			}
			read.close();
		} catch (Exception e) {
			
		}
	}
	
}
