package starter;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class HelpPane extends GraphicsPane{

	private MainApplication program;
	private GLabel title;
	private GButton backToMenu;
	private GButton backToGame;
	private ArrayList<GLabel> elements;
	//private String[] stuff;
	
	public HelpPane(MainApplication app) {
		super();
		program = app;
		title = new GLabel("HELP", 350, 100);
		title.setFont("Arial-70");
		backToMenu = new GButton("Back to Title", 300, 400, 100, 50);
		backToGame = new GButton("Back to Game", 400, 500, 100, 50);
		/*
		int x = 166;
		int y = 100;
		for(int i = 0; i < 6; i++) {
			if(i == 3) {
				x = 166;
				y+=100;
			}
			elements.add(new GLabel(stuff[i], x, y));
			x+=166;
		}
		*/
	}
	
	
	
	@Override
	public void showContents() {
		program.add(title);
		program.add(backToMenu);
		program.add(backToGame);
		/*for(int i = 0; i < 6; i++) {
			program.add(elements.get(i));
		}*/
	}
	
	@Override
	public void hideContents() {
		program.remove(title);
		program.remove(backToMenu);
		program.remove(backToGame);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == backToMenu) {
			program.switchToMainMenu();
		} else if(obj == backToGame) {
			//program.switchToGame();
		}
	}
}
