package maze.builder;

public class Default implements MazeBuilder{
	public char b[][];
	
	// for default mode (fixed size and positions)
	public Default() {
		b = new char[][] {
				{'X','X','X','X','X','X','X','X','X','X'},
				{'X','H',' ',' ',' ',' ',' ',' ',' ','X'},
				{'X',' ','X','X',' ','X',' ','X',' ','X'},
				{'X','D','X','X',' ','X',' ','X',' ','X'},
				{'X',' ','X','X',' ','X',' ','X',' ','X'},
				{'X',' ',' ',' ',' ',' ',' ','X',' ','S'},
				{'X',' ','X','X',' ','X',' ','X',' ','X'},
				{'X',' ','X','X',' ','X',' ','X',' ','X'},
				{'X','E','X','X',' ',' ',' ',' ',' ','X'},
				{'X','X','X','X','X','X','X','X','X','X'}
		};
	}

	public char[][] getMaze() {
		return b;
	}
}
			//Au