package maze.logic;

import java.io.Serializable;

//import maze.builder.Builder;

public class Element implements Serializable {

	private static final long serialVersionUID = 1L;
	protected char symbol;
	private Point position;
	protected char[][] board;
	
	//

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
		
		board[getY()][getX()] = symbol;
	}
}
