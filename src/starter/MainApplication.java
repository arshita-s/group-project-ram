package starter;
public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 600;
	//public static final String MUSIC_FOLDER = "sounds";
	//private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3" };

	private ACMgraphics game;
	private MainMenuPane mainMenu;
	private HelpPane help;
	private ScorePane score;

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		game = new ACMgraphics(this);
		mainMenu = new MainMenuPane(this);
		help = new HelpPane(this);
		score = new ScorePane(this);
		switchToMainMenu();
	}
	
	
	public void switchToMainMenu() {
		switchToScreen(mainMenu);
	}

	public void switchToGame() {
		switchToScreen(game);
	}
	
	public void switchToHelp() {
		switchToScreen(help);
	}
	
	public void switchToScore() {
		switchToScreen(score);
	}
	
	public void switchHelpInGame() {
		switchTo(help);
	}

	/*
	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}
	*/
}
