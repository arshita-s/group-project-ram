package starter;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.awt.*;
// Here I will take obstacles and put them on the screen
public class ACMgraphics extends GraphicsProgram{
	public static final int PROGRAM_HEIGHT = 700;
	public static final int PROGRAM_WIDTH = 700;
	private GCanvas map;
	
	public void init() {
		setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
		requestFocus();
	}
	//change parameter to object
	public GRect createObstacle(Position p, Size s, Velocity v) {
		GRect objRec = new GRect(p.getX(), p.getY(), s.getWidth(), s.getHeight()); 
		objRec.setFillColor(Color.BLACK);
		objRec.setFilled(true);
		return objRec;
	}
	public void setupLevel() {
		//creating map
		GCanvas map = new GCanvas();
		map.setVisible(true);
		map.setLocation(0, 0);
		add(map);
		
		//adding obstacles to map
		GRect obstacle = createObstacle(new Position(10,50), new Size(50, 15), new Velocity(0,0));
		map.add(obstacle);
		obstacle = createObstacle(new Position(0,0), new Size(50, 15), new Velocity(0,0));
		map.add(obstacle);
		
		
		//revalidating map
		map.revalidate();
		System.out.println(map.getX());
	}
	public void run() {
		setupLevel();
		
	}
}
