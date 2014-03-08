package maze.logic;

import maze.builder.*;

public class Game {
	private boolean gameOver;
	private MazeBuilder board;
	private Hero hero;
	private Eagle eagle;
	private Sword sword;
	private Dragon dragon;

	//Constructor to a game with a default board ... dummy variable to make difference
	public Game(MazeBuilder b, int dummy) {
		gameOver = false;
		board = b;
		hero = new Hero(new Point(1,1), b);
		eagle = new Eagle();
		sword = new Sword(new Point(1,8), b);
		dragon = new Dragon(new Point(1,3), b);
	}

	//Constructor to a game with size variable given by user 
	public Game(MazeBuilder b) {
		gameOver = false;
		board = b;
		hero = new Hero(b);
		sword = new Sword(b);
		dragon = new Dragon(b);
	}

	//returns a boolean with the state game information
	public boolean isGameOver() {
		return gameOver;
	}

	//when game is done
	public void endGame() {
		gameOver = true;
	}

	//return character dragon
	public Dragon getDragons() {
		return dragon;
	}

	//returns character hero
	public Hero getHero() {
		return hero;
	}

	//returns board game
	public MazeBuilder getBoard() {
		return board;
	}

	public int heroVsDragon() {
		int result = 0;
		if(Math.abs(hero.getY()-dragon.getY()) <= 1 && Math.abs(hero.getX()-dragon.getX()) <= 1) {
			if(!dragon.isAsleep()) {
				if(hero.isArmed() && dragon.isAlive()) {
					dragon.die(board);
					hero.killDragon();
					result = 1;
				}
				else if(!hero.isArmed()) {
					hero.die(board);
					endGame();
					result = 2;
				}
			}
			else {
				if(hero.isArmed() && dragon.isAlive()) {
					dragon.die(board);
					hero.killDragon();
					result = 1;
				}
			}
		}
		return result;
	}

	public void updatePositions() {
		if (hero.isArmed()) {
			sword.changeState();
			if (dragon.isAlive())
				dragon.draw();
		}

		// hero not armed means that both the sword hasn't been caught yet and the dragon hasn't been killed 
		// if the sword and the dragon are in the same position draw the dragon ('F') only
		else if(Utils.equalPositions(dragon.getPosition(), sword.getPosition())) {
			if(!dragon.isAsleep()) {
				dragon.changeSymbol('F');
				dragon.draw();
			}
			else {
				dragon.changeSymbol('f');
				dragon.draw();
			}
		}
		else if(!Utils.equalPositions(dragon.getPosition(), sword.getPosition())){
			if(!dragon.isAsleep()) {
				dragon.changeSymbol('D');
				dragon.draw();
				sword.draw();
			}
			else {
				dragon.changeSymbol('d');
				dragon.draw();
				sword.draw();
			}
		}

		// it is impossible for the dragon to be dead and the sword not having been caught
		hero.draw();
	}

	// return the eagle possible move	
	public Direction eagleDestination(Point initialPosition) {
		if(eagle.getY() < sword.getY())
			return Direction.DOWN;
		else if(eagle.getY() > sword.getY())
			return Direction.UP;
		else if(eagle.getX() < sword.getX())
			return Direction.RIGHT;
		else
			return Direction.LEFT;
	}

	// change a position to blank char
	public void blankPosition(Point p) {
		board.getMaze()[p.getY()][p.getX()] = ' ';
	}
}