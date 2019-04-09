package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class MainMenuPane extends GraphicsPane{
	private MainApplication program;
	
	private GButton start;
	private GButton help;
	private GLabel titleOne;
	private GLabel titleTwo;
	
	public MainMenuPane(MainApplication app) {
		super();
		program = app;
		start = new GButton("Start", 353, 312, 100, 50);
		help = new GButton("Help", 353, 412, 100, 50);
		titleOne = new GLabel("Kid", 120, 150);
		titleOne.setFont("Arial-70");
		titleTwo = new GLabel("Lizard", 200, 220);
		titleTwo.setFont("Arial-70");
	}
	
	@Override
	public void showContents() {
		program.add(start);
		program.add(help);
		program.add(titleOne);
		program.add(titleTwo);
	}
	
	@Override
	public void hideContents() {
		program.remove(start);
		program.remove(help);
		program.remove(titleOne);
		program.remove(titleTwo);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == start) {
			program.switchToGame();
		} else if(obj == help) {
			program.switchToHelp();
		}
	}
	
}
