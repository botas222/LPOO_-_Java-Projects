package maze.logic;

import maze.builder.MazeBuilder;

public class Element {

	protected char symbol;
	private Point position;
	protected MazeBuilder board;

	public void updateBoard(MazeBuilder actBoard) {
		this.board = actBoard;
	}

	public int getX() {
		return position.getX();
	}

	public int getY() {
		return position.getY();
	}

	public Point getPosition() {
		return new Point(position);
	}

	public void setPostion(int x, int y) {
		position = new Point(x,y);
	}

	public void setPosition(Point p) {
		position = new Point(p);

	}

	public void moveXY(int dx, int dy) {
		position.setX(position.getX()+dx);
		position.setY(position.getY()+dy);
	}

	public void draw() {
		board.getMaze()[getY()][getX()] = symbol;
	}
}
