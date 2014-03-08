package maze.logic;

import maze.builder.MazeBuilder;

public class Sword extends Element {

	private boolean caught;
	
	public Sword(Point p, MazeBuilder board) {
		this.board = board;
		caught = false;
		symbol = 'E';
		setPosition(new Point(p));
	}
	
	public Sword(MazeBuilder board) {
		this.board = board;
		caught = false;
		symbol = 'E';
		setPosition(new Point());
		do {
			int x = Utils.getRandomInteger(board.getMaze().length - 2) + 1;
			int y = Utils.getRandomInteger(board.getMaze().length - 2) + 1;
			setPostion(x,y);
			
		} while(board.getMaze()[getY()][getX()] != ' ');
	}
	
	public boolean isCaught() {
		return caught;
	}
	
	public void changeState() {
		caught = true;
		board.getMaze()[getPosition().getY()][getPosition().getX()] = ' ';
	}
}