package starter;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class HelpPane extends GraphicsPane{

	private static final int numHelpElements = 6;
	private int XLabel = 110;
	private int YLabel = 275;
	private int XImage = 100;
	private int YImage = 185;
	private MainApplication program;
	private GLabel title;
	private GButton backToMenu;
	private GButton backToGame;
	private ArrayList<GLabel> elements = new ArrayList<GLabel>(7);
	private ArrayList<GImage> images = new ArrayList<GImage>(7);
	private static String[] labels = {"Right", "Left", "Jump", "Jump On Top to Attack", "Masks", "Pause"};
	private static String[] imageNames = {"d_key.png", "a_key.png", "w_key.png", "w_key.png", "maniaxe_mask.png", "esc_key.png"};
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = {"Fantasy_Game_Background.mp3" };
	
	public HelpPane(MainApplication app) {
		super();
		program = app;
		title = new GLabel("HELP", 210, 100);
		title.setFont("Arial-70");
		backToMenu = new GButton("Back to Title", 250, 450, 100, 50);
		backToGame = new GButton("Back to Game", 250, 500, 100, 50);
		for(int i = 0; i < 6; i++) {
			if(i == 3) {
				XLabel = 70;
				YLabel += 120;
				YImage += 120;
				XImage = 100;
			}
			GImage image = new GImage(imageNames[i], XImage, YImage);
			GLabel label = new GLabel(labels[i], XLabel, YLabel);
			images.add(i, image);
			elements.add(i, label);
			if(i == 3) {
				XLabel+=40;
			}
			XLabel+=170;
			XImage+=170;
		}
	}
	
	public void removeGameButton() {
		try {
			program.remove(backToGame);
		} catch(NullPointerException e) {
			
		}
	}
	
	
	private void stopBackgroundMusic() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.stopSound(MUSIC_FOLDER, SOUND_FILES[0]);
	}
	
	@Override
	public void showContents() {
		program.add(title);
		program.add(backToMenu);
		program.add(backToGame);
		for(int i = 0; i < numHelpElements; i++) {
			program.add(elements.get(i));
			program.add(images.get(i));
		}
	}
	
	@Override
	public void hideContents() {
		program.remove(title);
		program.remove(backToMenu);
		removeGameButton();
		for(int i = 0; i < numHelpElements; i++) {
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
			stopBackgroundMusic();
			program.switchToGame();
		}
	}
}
