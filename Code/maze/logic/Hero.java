package maze.logic;
import maze.builder.*;

public class Hero extends Character {
	
	private boolean armed;
	private boolean killedADragon;
	
	public Hero(Point p, MazeBuilder board) {
		this.board = board;
		dead = false;
		armed = false;
		killedADragon = false;
		symbol = 'H';
		setPosition(new Point(p));
	}

	public Hero(MazeBuilder board) {
		this.board = board;
		dead = false;
		armed = false;
		killedADragon = false;
		symbol = 'H';
		do {
			int x = Utils.getRandomInteger(board.getMaze().length - 2) + 1;
			int y = Utils.getRandomInteger(board.getMaze().length - 2) + 1;
			setPostion(x,y);

		} while(board.getMaze()[getY()][getX()] != ' ');
	}

	public boolean validMove(int dx, int dy, Game thatGame) {
		if (board.getMaze()[getY() + dy][getX() + dx] == ' ')
			return true;
		if (board.getMaze()[getY() + dy][getX() + dx] == 'E') {
			changeSymbol();
			return true;
		}
		if (board.getMaze()[getY() + dy][getX() + dx] == 'S' 
				&& hasKilledADragon()) {
			// no need to check if he's armed, couldn't have killed a dragon if he hadn't caught the sword
			thatGame.endGame();
			return true;
		}
		if(board.getMaze()[getY() + dy][getX() + dx] == 'd')
			return true;
		if(board.getMaze()[getY() + dy][getX() + dx] == 'f')
			return true;
		
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
	
	public void die(MazeBuilder board) {
		dead = true;
		symbol = '*';
		board.getMaze()[getPosition().getY()][getPosition().getX()] = ' ';
	}
}
