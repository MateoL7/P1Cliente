package model;

public class ShotStatus {
	
	public String type = "ShotStatus";
	private boolean bullseye;
	
	public ShotStatus() {}
	
	public ShotStatus(boolean bullseye) {
		this.bullseye = bullseye;
	}
	
	public boolean isBullseye() {
		return bullseye;
	}
	

}
