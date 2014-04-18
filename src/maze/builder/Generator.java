package maze.builder;

import java.util.Arrays;
import java.util.Stack;
import maze.logic.*;

public class Generator implements Builder {
	
	private static final char labExit = 'S';
	private static final char wall = 'X';
	private char b[][];
	private int boardSize;
	private boolean visited[][];
	private int visitedSize;
	private Stack<Point> history;
	private Point guideCell;
	private Point exit;

	//
	
	// for random mode (has a user-specified size, and random positions)
	public Generator(int s) {
		
		boardSize = s;
		visitedSize = (boardSize-1)/2;

		// init
		b = new char[boardSize][boardSize];
		visited = new boolean[visitedSize][visitedSize];
		guideCell = new Point();
		exit = new Point();
		history = new Stack<Point>();

		// board initialization
		for (int i = 0; i < boardSize; ++i) {
			for (int j = 0; j < boardSize; ++j) {
				if (i % 2 != 0 && j % 2 != 0)
					b[i][j] = ' ';
				else 
					b[i][j] = wall;
			}
		}

		// visited array initialization
		for (int i = 0; i < visitedSize; ++i)
			Arrays.fill(visited[i], false);

		// choosing the side where the guide cell will be
		// and assigning the start position of the guideCell
		int side = Utils.getRandomInteger(4);
		switch (side) {
		case 0: // up
			guideCell.setY(0);
			guideCell.setX(Utils.getRandomInteger(visitedSize));
			break;
		case 1: // down
			guideCell.setY(visitedSize-1);
			guideCell.setX(Utils.getRandomInteger(visitedSize));
			break;
		case 2: // left
			guideCell.setX(0);
			guideCell.setY(Utils.getRandomInteger(visitedSize));
			break;
		case 3: // right
			guideCell.setX(visitedSize-1);
			guideCell.setY(Utils.getRandomInteger(visitedSize));
			break;
		default:
			System.out.println("UNEXPECTED VALUE!");
			break;
		}

		// set the exit place
		defineExit(side, guideCell.getX(), guideCell.getY());

		// add to stack, mark as 'visited', write the exit
		addToStack();
		markAsVisited();
		b[exit.getY()][exit.getX()] = labExit;

		// create a random labyrinth
		generateMaze();
	}

	// depending on the position of the guide cell, sets the exit place
	private void defineExit(int side, int x, int y) {
		
		switch (side) {
		case 0: // up
			exit.setX(Utils.visitedToLab(x));
			exit.setY(Utils.visitedToLab(y)-1);
			break;
		case 1: // down
			exit.setX(Utils.visitedToLab(x));
			exit.setY(Utils.visitedToLab(y)+1);
			break;
		case 2: // left
			exit.setX(Utils.visitedToLab(x)-1);
			exit.setY(Utils.visitedToLab(y));
			break;
		case 3: // right
			exit.setX(Utils.visitedToLab(x)+1);
			exit.setY(Utils.visitedToLab(y));
			break;
		default:
			System.out.println("UNEXPECTED VALUES!");
			break;
		}	
	}

	// marks the guide cell position as visited
	private void markAsVisited() {
		
		visited[guideCell.getY()][guideCell.getX()] = true;		
	}

	// add guide cell coordinates to stack
	private void addToStack() {
		
		history.add(new Point(guideCell));		
	}

	// generates a random maze
	private void generateMaze() {
		
		while (!history.isEmpty()) {
			if (allNeighborsVisited()) {
				history.pop(); 
				if (!history.isEmpty()) {
					guideCell = history.peek(); // update guideCell
				}
				else
					break;
			}
			Direction d = Direction.values()[Utils.getRandomInteger(4)];
			moveGuideCell(d); // try to move the guide cell in direction 'd'
		}
	}

	// moves the guide cell in the direction 'd', if it is possible
	private boolean moveGuideCell(Direction d) {
		
		// if the cell where we are trying to go has already been visited
		// the guide cell does not move
		if (nextCellVisited(d))
			return false;

		Point lab = new Point();
		lab.setX(Utils.visitedToLab(guideCell.getX()));
		lab.setY(Utils.visitedToLab(guideCell.getY()));

		switch (d) {
		case UP:
			b[lab.getY()-1][lab.getX()] = ' '; // remove the 'X'
			guideCell.setY(guideCell.getY()-1); // update guide cell coordinates
			break;
		case DOWN:
			b[lab.getY()+1][lab.getX()] = ' '; // remove the 'X'
			guideCell.setY(guideCell.getY()+1); // update guide cell coordinates
			break;
		case LEFT:
			b[lab.getY()][lab.getX()-1] = ' '; // remove the 'X'
			guideCell.setX(guideCell.getX()-1); // update guide cell coordinates
			break;
		case RIGHT:
			b[lab.getY()][lab.getX()+1] = ' '; // remove the 'X'
			guideCell.setX(guideCell.getX()+1); // update guide cell coordinates
			break;
		default:
			System.out.println("UNEXPECTED VALUE!");
		}

		markAsVisited();
		addToStack();

		return true;
	}

	// checks whether all the adjacent cells to the guide cell
	// have been visited or not
	private boolean allNeighborsVisited() {
		
		for (int i = 0 ; i < 4; ++i) {
			if (!nextCellVisited(Direction.values()[i])) {
				return false;
			}
		}
		return true;
	}

	// checks whether the next cell (in a determined direction) to the guide cell
	// has already been visited or not
	private boolean nextCellVisited(Direction direction) {
		
		switch (direction) {
		case UP:
			if (guideCell.getY() < 1)
				return true;
			return visited[guideCell.getY()-1][guideCell.getX()];
		case DOWN:
			if (guideCell.getY() >= visitedSize-1)
				return true;
			return visited[guideCell.getY()+1][guideCell.getX()];
		case LEFT:
			if (guideCell.getX() < 1)
				return true;
			return visited[guideCell.getY()][guideCell.getX()-1];
		case RIGHT:
			if (guideCell.getX() >= visitedSize-1)
				return true;
			return visited[guideCell.getY()][guideCell.getX()+1];
		default:
			System.out.println("UNEXPECTED VALUE!");
			return false;
		}
	}

	@Override
	public char[][] getBoard() {
		
		return b;
	}

	@Override
	public int getBoardSize() {

		return boardSize;
	}
}
