package maze.logic;

public class Hero extends Character {
	
	private static final long serialVersionUID = 1L;
	private boolean armed;
	private boolean killedADragon;
	private boolean hasEagle = true;
	
	//
	
	public Hero(Point p, char[][] board) {
		
		this.board = board;
		dead = false;
		armed = false;
		killedADragon = false;
		symbol = 'H';
		setPosition(new Point(p));
	}

	public Hero(char[][] board) {
		
		this.board = board;
		dead = false;
		armed = false;
		killedADragon = false;
		symbol = 'H';
		do {
			int x = Utils.getRandomInteger(board.length - 2) + 1;
			int y = Utils.getRandomInteger(board.length - 2) + 1;
			setPostion(x,y);

		} while(board[getY()][getX()] != ' ');
	}

	public boolean validMove(int dx, int dy, Game thatGame) {
		
		if (board[getY() + dy][getX() + dx] == ' '
				|| board[getY() + dy][getX() + dx] == 'd'
				|| board[getY() + dy][getX() + dx] == 'f'
				|| board[getY() + dy][getX() + dx] == 'Y')
			return true;
		
		if (board[getY() + dy][getX() + dx] == 'E') {
			
			changeSymbol();
			return true;
		}
		
		if (board[getY() + dy][getX() + dx] == 'S' 
				&& hasKilledADragon()) {
			// no need to check if he's armed, couldn't have killed a dragon if he hadn't caught the sword
			thatGame.end();
			return true;
		}

		return false;
	}

	public boolean hasKilledADragon() {
		
		return killedADragon;
	}

	public void killDragon() {
		
		killedADragon = true;
	}

	public boolean isArmed() {
		
		return armed;
	}

	public void changeSymbol() {

		symbol = 'A';
		armed = true;
	}
	
	public void die() {
		
		dead = true;
		symbol = '*';
	}
	
	public boolean hasEagle() {
		return hasEagle;
	}
	
	public void setEagle(boolean s) {
		hasEagle = s;
	}
}
