package starter;

import acm.graphics.GImage;

public class Mask {
	private boolean taken;
	public Position spawnPosition;
	public Position currentPosition;
	private GImage mask;
	private static final String skin = "maniaxe_mask.png";
	
	public Mask(Position p, Size s) {
		spawnPosition = p;
		currentPosition = p;
		taken = false;
		mask = new GImage(skin, p.getX(), p.getY());
		mask.setSize(s.getWidth(), s.getHeight());
		
	}
	
	public void setTaken(boolean t) {
		taken = t;
	}
	
	public boolean isTaken() {
		return taken;
	}
	
	
	public GImage getSkin() {
		return mask;
	}
	
	public Position getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void setSpawnPosition(Position p) {
		spawnPosition = p;
	}
	
	public Position getSpawnPosition() {
		return spawnPosition;
	}
	
	public void resetPosition() {
		mask.setLocation(spawnPosition.getX(), spawnPosition.getY());
		setCurrentPosition(spawnPosition);
	}
}


