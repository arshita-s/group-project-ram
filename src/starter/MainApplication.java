package starter;


public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 600;
	public final int GAME_WINDOW_WIDTH = 800;

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
			} else {
				game.nextLevel();
			}
			switchToScreen(game);
		}
		setSize(GAME_WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	public int getGameWidth() {
		return GAME_WINDOW_WIDTH;
	}
	
	public void switchToHelp() {
		GraphicsPane temp = getCurrentScreen();
		switchToScreen(help);
		if(temp.equals(mainMenu)) {
			help.removeGameButton();
		}
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void switchToScore(int score_num) {
		score.setScore(score_num);
		switchToScreen(score);
		if(game.isLastLevel()) {
			score.removeNextButton();
		}
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
}
