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
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void switchToGame() {
		GraphicsPane temp = getCurrentScreen();
		if(temp.equals(help)) {
			switchTo(game);
			game.returnToGame();
		} else {
			if(temp.equals(mainMenu)) {
				game.resetAll();
			}
			switchToScreen(game);
		}
		setSize(WINDOW_WIDTH*2, WINDOW_HEIGHT);
	}

	public void switchToHelp() {
		GraphicsPane temp = getCurrentScreen();
		switchToScreen(help);
		if(temp.equals(mainMenu)) {
			help.removeGameButton();
		}
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void switchToScore() {
		switchToScreen(score);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	/*
	private void playRandomSound() {
		AudioPlayer audio = AudioPlayer.getInstance();
		audio.playSound(MUSIC_FOLDER, SOUND_FILES[count % SOUND_FILES.length]);
	}
	 */
}
