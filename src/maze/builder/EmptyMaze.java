package maze.builder;

public class EmptyMaze implements Builder {

	private char b[][];
	private int width;
	
	public EmptyMaze(int width, int height) {
		
		this.width = width;
		b = new char[width][width];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < width; j++) {
				b[i][j] = 'X'; 
			}
		}
	}
	
	public char[][] getBoard() {

		return b;
	}

	public int getBoardSize() {

		return width;
	}

};
