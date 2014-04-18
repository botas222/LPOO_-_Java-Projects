package maze.logic;

import java.util.Random;

public class Utils {
	
	//
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
	
	public static boolean isNear(Element e1, Element e2) {
		
		return (Math.abs(e1.getY() - e2.getY()) <= 1
				&& Math.abs(e1.getX() - e2.getX()) <= 1);
	}
}
