package starter;

import acm.graphics.GImage;

public class Mask {
	private boolean taken;
	public Position spawnPosition;
	private GImage mask;
	private static final String skin = "maniaxe_mask.png";
	
	public Mask(Position p, Size s) {
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
}


