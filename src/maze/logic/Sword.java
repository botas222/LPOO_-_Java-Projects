package maze.logic;

//import maze.builder.Builder;

public class Sword extends Element {

	private static final long serialVersionUID = 1L;
	private boolean caught;

	//
	
	public Sword(Point p, char[][] board) {
		
		this.board = board;
		caught = false;
		symbol = 'E';
		setPosition(new Point(p));
	}
	
	public Sword(char[][] board) {
		
		this.board = board;
		caught = false;
		symbol = 'E';
		setPosition(new Point());
		do {
			int x = Utils.getRandomInteger(board.length - 2) + 1;
			int y = Utils.getRandomInteger(board.length - 2) + 1;
			setPostion(x,y);
			
		} while(board[getY()][getX()] != ' ');
	}
	
	public boolean isCaught() {
		
		return caught;
	}
	
	public void changeState(boolean s) {
		
		caught = s;
	}
}