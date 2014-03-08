package maze.logic;

import maze.builder.MazeBuilder;

public class Dragon extends Character {

	private boolean asleep;
	
	public Dragon(Point p, MazeBuilder board) {
		this.board = board;
		dead = false;
		symbol = 'D';
		setPosition(new Point(p));
		asleep = false;
	}
	
	public Dragon(MazeBuilder board) {
		this.board = board;
		dead = false;
		symbol = 'D';
		asleep = false;
		do {
			int x = Utils.getRandomInteger(board.getMaze().length - 2) + 1;
			int y = Utils.getRandomInteger(board.getMaze().length - 2) + 1;
			setPostion(x,y);
			
		} while(board.getMaze()[getY()][getX()] != ' ');
	}
	
	public boolean validMove(int dx, int dy, Game thatGame) {
		if (board.getMaze()[getY() + dy][getX() + dx] == ' '
				|| board.getMaze()[getY() + dy][getX() + dx] == 'E')
			return true;
		return false;
	}
	
	public void setIsAsleep(boolean asleep) {
		this.asleep = asleep;
		if (asleep)
			symbol = 'd';
		else
			symbol = 'D';
	}
	
	public boolean isAsleep() {
		return asleep;
	}
	
	public void changeSymbol(char c) {
		symbol = c;
	}	
}