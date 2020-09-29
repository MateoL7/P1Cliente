package model;

public class GameStatus {
	
	public String type = "GameStatus";
	public String actualStatus;
	
	public GameStatus() {
		
	}
	
	public GameStatus(String actualStatus) {
		this.actualStatus = actualStatus;
	}
	
	public String getActualStatus() {
		return actualStatus;
	}


}
