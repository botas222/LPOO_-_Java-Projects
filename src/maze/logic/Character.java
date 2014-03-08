package maze.logic;

import maze.builder.MazeBuilder;

public abstract class Character extends Element {

	protected boolean dead;
	
	public boolean isAlive() {
		return !dead;
	}
	
	public abstract boolean validMove(int dx, int dy, Game thatGame);
	
	public void move(Direction d, Game thatGame) {
		switch(d) {
		case UP:
			if(validMove(0,-1, thatGame)) {
				thatGame.blankPosition(getPosition());
				moveXY(0,-1);
			}
			break;
		case DOWN:
			if(validMove(0,1, thatGame)) {
				thatGame.blankPosition(getPosition());
				moveXY(0,1);
			}
			break;
		case LEFT:
			if(validMove(-1,0, thatGame)) {
				thatGame.blankPosition(getPosition());
				moveXY(-1,0);
			}
			break;
		case RIGHT:
			if(validMove(1,0, thatGame)) {
				thatGame.blankPosition(getPosition());
				moveXY(1,0);
			}
			break;
		default:
			break;
		}
	}
		
	public void die(MazeBuilder board) {
		dead = true;
		board.getMaze()[getPosition().getY()][getPosition().getX()] = ' ';
	}
}
