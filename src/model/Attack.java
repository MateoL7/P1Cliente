package model;

public class Attack {
	
	public String type = "Attack";
	private int to;
	
	public Attack() {}
	
	public Attack(int to) {
		this.to = to;
	}

	public int getTo() {
		return to;
	}

}
