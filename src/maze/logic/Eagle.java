package maze.logic;

import java.util.Stack;

public class Eagle extends Character {

	private static final long serialVersionUID = 1L;
	private boolean launched = false;
	private boolean sword = false;
	private Stack<Point> history = new Stack<Point>();
	private boolean flying = false;
	private boolean withHero = true;

	//

	public Eagle(char[][] board, Hero h) {

		this.board = board;
		dead = false;
		symbol = 'N';
		setPosition(h.getPosition());
	}

	void changeSymbol() {

		symbol = 'Y';
	}

	public boolean validMove(int dx, int dy, Game thatGame) {
		return true;
	}

	public boolean hasBeenLaunched() {

		return launched;
	}

	public void launch() {

		launched = true;
		flying(true);
	}

	public void addToStack(Point p) {

		history.add(p);
	}

	public Stack<Point> getHistory() {

		return history;
	}

	public boolean hasSword() {

		return sword;
	}

	public void setSword(boolean s) {

		sword = s;
	}

	public boolean isFlying() {

		return flying;
	}

	public void flying(boolean f) {

		flying = f;
	}

	public boolean isWithHero() {

		return withHero;
	}

	public void setWithHero(boolean s) {

		withHero = s;
	}
}
