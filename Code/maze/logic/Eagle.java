package maze.logic;

public class Eagle extends Character {
	boolean hasEagleLauched = false;
	
	public Eagle() {
		symbol = ' ';
	}
	
	public void eagleHasLauched() {
		hasEagleLauched = true;
	}
	
	void changeSymbol() {
		symbol = 'E';
	}
	
	public boolean validMove(int dx, int dy, Game thatGame) {return true;}
}
