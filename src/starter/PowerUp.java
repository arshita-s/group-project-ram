package starter;

import acm.graphics.GImage;

public class PowerUp {
	private static final int amtPower = 10;
	private boolean taken;
	public Position spawnPosition;
	private GImage powerup;
	private static final String skin = " ";
	
	public PowerUp(Position p, Size s) {
		taken = false;
		powerup = new GImage(skin, p.getX(), p.getY());
		powerup.setSize(s.getWidth(), s.getHeight());
		
	}
	
	public void setTaken(boolean t) {
		taken = t;
	}
	
	public boolean isTaken() {
		return taken;
	}
	
	public int getPower() {
		return amtPower;
	}
	
	public GImage getSkin() {
		return powerup;
	}
}
