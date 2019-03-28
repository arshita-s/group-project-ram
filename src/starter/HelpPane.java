package starter;

import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class HelpPane extends GraphicsPane{

	private MainApplication program;
	private GLabel title;
	private GButton backToMenu;
	
	public HelpPane(MainApplication app) {
		super();
		program = app;
		title = new GLabel("HELP", 350, 100);
		title.setFont("Arial-70");
		backToMenu = new GButton("Back to Menu", 400, 500, 100, 50);
	}
	
	@Override
	public void showContents() {
		program.add(title);
		program.add(backToMenu);
	}
	
	@Override
	public void hideContents() {
		program.remove(title);
		program.remove(backToMenu);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == backToMenu) {
			program.switchToMainMenu();
		}
	}
}
