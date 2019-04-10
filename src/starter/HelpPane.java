package starter;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class HelpPane extends GraphicsPane{

	private MainApplication program;
	private GLabel title;
	private GButton backToMenu;
	private GButton backToGame;
	private ArrayList<GLabel> elements = new ArrayList<GLabel>(6);
	private ArrayList<GImage> images = new ArrayList<GImage>(6);
	private static String[] options = {"Right", "Left", "Jump", "Attack", "PowerUp", "Masks"};
	private static String[] imageNames = {"d_key.png", "a_key.png", "w_key.png", "z_key.png", "x_key.png", "maniaxe_mask.png"};
	
	public HelpPane(MainApplication app) {
		super();
		program = app;
		title = new GLabel("HELP", 225, 100);
		title.setFont("Arial-70");
		backToMenu = new GButton("Back to Title", 250, 450, 100, 50);
		backToGame = new GButton("Back to Game", 250, 500, 100, 50);

		int x = 110;
		int y = 250;
		int z = 160;
		int w = 100;
		for(int i = 0; i < 6; i++) {
			if(i == 3) {
				x = 110;
				y += 120;
				z += 120;
				w = 100;
			}
			GImage im = new GImage(imageNames[i], w, z);
			GLabel g = new GLabel(options[i], x, y);
			images.add(i, im);
			elements.add(i, g);
			x+=170;
			w+=170;
		}

	}
	
	
	
	@Override
	public void showContents() {
		program.add(title);
		program.add(backToMenu);
		program.add(backToGame);
		for(int i = 0; i < 6; i++) {
			program.add(elements.get(i));
			program.add(images.get(i));
		}
	}
	
	@Override
	public void hideContents() {
		program.remove(title);
		program.remove(backToMenu);
		program.remove(backToGame);
		for(int i = 0; i < 6; i++) {
			program.remove(elements.get(i));
			program.remove(images.get(i));
		}
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
