package starter;

public class PowerUp {
	private static final int amtPower = 10;
	private boolean taken;
	public Position spawnPosition;
	
	public PowerUp(Position p) {
		taken = false;
		spawnPosition = p;
		
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
}
