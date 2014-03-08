package maze.logic;

import java.util.Random;

public class Utils {
	
	public static int getRandomInteger(int seed) {
		Random r = new Random();
		return r.nextInt(seed);
	}
	
	public static boolean getRandomBoolean() {
		Random r = new Random();
		return r.nextBoolean();
	}
	
	// converts coordinates from visited cells array to the labyrinth array
	public static int visitedToLab(int coordinate) {
		return coordinate*2 + 1;
	}
	
	// checks whether the positions of two elements are the same
	public static boolean equalPositions(Point pos1, Point pos2) {
		return (pos1.getX() == pos2.getX() && pos1.getY() == pos2.getY());
	}
}
