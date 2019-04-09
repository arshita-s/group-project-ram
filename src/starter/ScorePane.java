package starter;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class ScorePane extends GraphicsPane{
	private MainApplication program;
	
	private GButton backToMenu;
	private GButton next;
	private GLabel score;
	private GLabel num;
	
	public ScorePane(MainApplication app) {
		super();
		program = app;
		backToMenu = new GButton("Back to Menu", 600, 400, 100, 50);
		next = new GButton("Next", 600, 475, 100, 50);
		score = new GLabel("Score:", 120, 150);
		score.setFont("Arial-70");
		num = new GLabel("1000", 200, 220);
		num.setFont("Arial-70");
	}
	
	@Override
	public void showContents() {
		program.add(backToMenu);
		program.add(next);
		program.add(score);
		program.add(num);
	}
	
	@Override
	public void hideContents() {
		program.remove(backToMenu);
		program.remove(next);
		program.remove(score);
		program.remove(num);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == backToMenu) {
			//program.switchToMainMenu();
		} else if(obj == next) {
			//program.switchToGame();
		}
	}
	
}
