package starter;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class HelpPane extends GraphicsPane{

	private static final int numHelpElements = 6;
	private int XLabel = 110;
	private int YLabel = 250;
	private int XImage = 100;
	private int YImage = 160;
	private static int finalXLabel = 280;
	private static int finalYLabel = 490;
	private static int finalXImage = 270;
	private static int finalYImage = 400;
	private MainApplication program;
	private GLabel title;
	private GButton backToMenu;
	private GButton backToGame;
	private ArrayList<GLabel> elements = new ArrayList<GLabel>(7);
	private ArrayList<GImage> images = new ArrayList<GImage>(7);
	private static String[] labels = {"Right", "Left", "Jump", "Attack", "PowerUp", "Masks", "Pause"};
	private static String[] imageNames = {"d_key.png", "a_key.png", "w_key.png", "z_key.png", "x_key.png", "maniaxe_mask.png", "esc_key.png"};
	public static final String MUSIC_FOLDER = "sounds";
	private static final String[] SOUND_FILES = {"Fantasy_Game_Background.mp3" };
	
	public HelpPane(MainApplication app) {
		super();
		program = app;
		title = new GLabel("HELP", 225, 100);
		title.setFont("Arial-70");
		backToMenu = new GButton("Back to Title", 400, 450, 100, 50);
		backToGame = new GButton("Back to Game", 400, 500, 100, 50);
		for(int i = 0; i < 6; i++) {
			if(i == 3) {
				XLabel = 110;
				YLabel += 120;
				YImage += 120;
				XImage = 100;
			}
			GImage image = new GImage(imageNames[i], XImage, YImage);
			GLabel label = new GLabel(labels[i], XLabel, YLabel);
			images.add(i, image);
			elements.add(i, label);
			XLabel+=170;
			XImage+=170;
		}
		elements.add(new GLabel(labels[numHelpElements], finalXLabel, finalYLabel));
		images.add(new GImage(imageNames[numHelpElements], finalXImage, finalYImage));
	}
	
	public void removeGameButton() {
		try {
			program.remove(backToGame);
		} catch(NullPointerException e) {
			
		}
	}
	
	private void playBackgroundMusic() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[0]);
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
		for(int i = 0; i < numHelpElements+1; i++) {
			program.add(elements.get(i));
			program.add(images.get(i));
		}
	}
	
	@Override
	public void hideContents() {
		program.remove(title);
		program.remove(backToMenu);
		removeGameButton();
		for(int i = 0; i < numHelpElements+1; i++) {
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
