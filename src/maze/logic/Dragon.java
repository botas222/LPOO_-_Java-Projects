package maze.logic;

public class Dragon extends Character {
	
	private static final long serialVersionUID = 1L;
	private boolean asleep;
	private DragonMode mode;
	private int movesLeft;

	//

	public DragonMode getMode() {

		return mode;
	}

	public enum DragonMode {
		
		FIXED,MOVING,MOVINGANDSLEEPING;
	}

	public Dragon(Point p, char[][] board, DragonMode mode) {
		
		this.board = board;
		dead = false;
		symbol = 'D';
		setPosition(new Point(p));
		asleep = false;
		this.mode = mode;
		if (mode.equals(DragonMode.MOVINGANDSLEEPING)) 
			movesLeft = Utils.getRandomInteger(5) + 1; 
	}

	public Dragon(char[][] board, DragonMode mode, Hero h) {
		
		this.board = board;
		dead = false;
		symbol = 'D';
		asleep = false;
		this.mode = mode;
		if (mode.equals(DragonMode.MOVINGANDSLEEPING)) 
			movesLeft = Utils.getRandomInteger(5) + 1;
		do {
			int x = Utils.getRandomInteger(board.length - 2) + 1;
			int y = Utils.getRandomInteger(board.length - 2) + 1;
			setPostion(x,y);

		} while(board[getY()][getX()] != ' ' 
				|| Math.abs(h.getY()-getY()) < 4 
				|| Math.abs(h.getX()-getX()) < 4);
	}

	public boolean validMove(int dx, int dy, Game thatGame) {

		if (board[getY() + dy][getX() + dx] == ' '
				|| board[getY() + dy][getX() + dx] == 'E')
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

	public int getMovesLeft() {
		
		return movesLeft;
	}
	
	public void setMoves(int n) {
		
		movesLeft = n;
	}

	public void decMoves() {

		--movesLeft;
	}
	
}