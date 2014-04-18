package maze.logic;

import java.io.*;
import java.util.Arrays;

import maze.builder.*;
import maze.cli.Message;
import maze.logic.Dragon.DragonMode;

public class Game implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Builder createMaze;
	private boolean gameOver;
	private char[][] board;
	private final char[][] builder; // stores the maze w/o any
									// elements/characters
	private Hero hero;
	private Eagle eagle;
	private Sword sword;
	private Dragon dragons[];

	/*
	 * CONSTRUCTORS
	 */

	// Constructor to a empty game
	public Game(int width, int height) {
		Builder empty = new EmptyMaze(width, height);
		createMaze = empty;
		board = empty.getBoard();
		builder = new char[board.length][board.length];
		for (int i = 0; i < board.length; ++i)
			builder[i] = Arrays.copyOf(board[i], board[i].length);
	}
	
	// Constructor to a game with a default board ...
	public Game(Builder b, DragonMode mode, char dummy) {

		board = b.getBoard();
		builder = new char[board.length][board.length];
		for (int i = 0; i < board.length; ++i)
			builder[i] = Arrays.copyOf(board[i], board[i].length);

		gameOver = false;
		hero = new Hero(new Point(1, 1), board);
		sword = new Sword(new Point(1, 8), board);
		eagle = new Eagle(board, hero);
		dragons = new Dragon[1];
		dragons[0] = new Dragon(new Point(1, 3), board, mode);
	}

	// Constructor to a game with size variable given by user
	public Game(Builder b, DragonMode mode, int nrDragons) {

		board = b.getBoard();
		builder = new char[board.length][board.length];
		for (int i = 0; i < board.length; ++i)
			builder[i] = Arrays.copyOf(board[i], board[i].length);

		gameOver = false;
		hero = new Hero(board);
		sword = new Sword(board);
		eagle = new Eagle(board, hero);
		dragons = new Dragon[nrDragons];
		for (int i = 0; i < dragons.length; ++i) {
			dragons[i] = new Dragon(board, mode, hero);
		}
	}

	/*
	 * GETTERS/SETTERS
	 */

	// returns a boolean with the state game information
	public boolean isOver() {

		return gameOver;
	}

	// when game is done
	public void end() {

		gameOver = true;
	}

	// return character dragon
	public Dragon[] getDragons() {

		return dragons;
	}

	// returns character hero
	public Hero getHero() {

		return hero;
	}

	// returns character eagle
	public Eagle getEagle() {

		return eagle;
	}

	// returns element sword
	public Sword getSword() {
		return sword;
	}

	// returns board game
	public char[][] getBoard() {

		return board;
	}

	/*
	 * MAIN METHODS
	 */

	// processes the hero movement
	public void heroMoves(String moveHero) {

		if (getHero().isAlive()) {

			for (Dragon d : dragons) {
				dragonMoves(d,Utils.getRandomInteger(4));
			}

			eagleMoves();

			switch (moveHero) {
			case "l":
				if (!eagle.hasBeenLaunched())
					startEagle();
				break;
			case "w":
				getHero().move(Direction.UP, this);
				break;
			case "s":
				getHero().move(Direction.DOWN, this);
				break;
			case "a":
				getHero().move(Direction.LEFT, this);
				break;
			case "d":
				getHero().move(Direction.RIGHT, this);
				break;
			default:
				break;
			}

			if (hero.hasEagle())
				eagle.setPosition(hero.getPosition());

			checkSwordAndHeroPositions();
			checkEagleAndHeroPositions();

		}
	}

    // processes the dragon movement, depending on its mode and if it is asleep or not
    public void dragonMoves(Dragon d, int i) {

        Direction dir = Direction.values()[i];

        if (d.isAlive()) {
            if (d.getMode().equals(DragonMode.FIXED))
                return;

            if (d.getMode().equals(DragonMode.MOVING)) {
                d.move(dir, this);
            }

            // if dragon is asleep, generate a integer in [2,4] to know how many
            // moves it stays on that state
            // if dragon is awake, generate a integer in [1,5] to know how many
            // moves it stays on that state
            if (d.getMode().equals(DragonMode.MOVINGANDSLEEPING)) {
                if (d.getMovesLeft() == 0) {
                    d.setIsAsleep(Utils.getRandomBoolean());
                    if (d.isAsleep())
                        d.setMoves(Utils.getRandomInteger(3) + 2);
                    else
                        d.setMoves(Utils.getRandomInteger(5) + 1);
                }
                // if the dragon is asleep then it doesn't move
                if (!d.isAsleep()) {
                    d.move(dir, this);
                }

                d.decMoves();
            }
        }
    }

    public void eagleMoves() {

        if (eagle.hasBeenLaunched()) {

            if (eagle.hasSword())
                eagleMovesReverse();

            else {

                if (sword.getX() < eagle.getX() && sword.getY() < eagle.getY()
                        && !sword.isCaught()) {

                    eagle.move(Direction.UP, this);
                    eagle.move(Direction.LEFT, this);
                    eagle.addToStack(eagle.getPosition());
                }

                else if (sword.getX() > eagle.getX()
                        && sword.getY() < eagle.getY() && !sword.isCaught()) {

                    eagle.move(Direction.UP, this);
                    eagle.move(Direction.RIGHT, this);
                    eagle.addToStack(eagle.getPosition());
                }

                else if (sword.getX() < eagle.getX()
                        && sword.getY() > eagle.getY() && !sword.isCaught()) {

                    eagle.move(Direction.DOWN, this);
                    eagle.move(Direction.LEFT, this);
                    eagle.addToStack(eagle.getPosition());
                }

                else if (sword.getX() > eagle.getX()
                        && sword.getY() > eagle.getY() && !sword.isCaught()) {

                    eagle.move(Direction.DOWN, this);
                    eagle.move(Direction.RIGHT, this);
                    eagle.addToStack(eagle.getPosition());
                }

                else if (eagle.getX() == sword.getX() && !sword.isCaught()) {

                    if (eagle.getY() > sword.getY())
                        eagle.move(Direction.UP, this);

                    else
                        eagle.move(Direction.DOWN, this);

                    eagle.addToStack(eagle.getPosition());
                }

                else if (eagle.getY() == sword.getY() && !sword.isCaught()) {

                    if (eagle.getX() > sword.getX())
                        eagle.move(Direction.LEFT, this);

                    else
                        eagle.move(Direction.RIGHT, this);

                    eagle.addToStack(eagle.getPosition());
                }

                if (eagle.getPosition().equals(sword.getPosition())
                        && eagle.isAlive()) {
                    sword.changeState(true);
                    eagle.setSword(true);
                    eagle.changeSymbol();
                    eagle.flying(false);
                    eagle.getHistory().pop();
                }
            }
        }
    }

	private String heroVsDragons(Dragon d) {

		if (Utils.isNear(hero, d) && d.isAlive()) {

			if (hero.isArmed()) {

				d.die();
				hero.killDragon();
				return Message.killedADragon;
			}

			else if (!d.isAsleep()) {

				hero.die();
				end();
				return Message.playerDied;
			}
		}

		return "";
	}

	private String eagleVsDragons(Dragon d) {

		if (eagle.isAlive() && eagle.hasBeenLaunched() && !eagle.isFlying()
				&& !eagle.isWithHero())

			if (Utils.isNear(eagle, d) && !d.isAsleep()) {

				eagle.die();
				eagle.setSword(false);
				sword.changeState(false);
				return Message.eagleDied;
			}

		return "";
	}

	public String update() {

		String result = "";
		for (Dragon d : getDragons()) {

			result = result + (heroVsDragons(d));
			if (!getHero().isAlive())
				return result;
		}

		for (Dragon d : getDragons()) {

			result = result + (eagleVsDragons(d));
		}

		return result;
	}

	public void drawElements() {

		resetBoard();
		for (Dragon d : dragons) {

			if (d.isAlive()) {

				if (!sword.isCaught())
					checkSwordAndDragonPositions(d, sword);

				d.draw();
			}
		}

		hero.draw();

		if (eagle.hasBeenLaunched() && eagle.isAlive() && !hero.isArmed())
			eagle.draw();
		if (!sword.isCaught() && !dragonOverSword())
			sword.draw();
	}

	private void eagleMovesReverse() {

		if (!eagle.getHistory().empty() && eagle.isAlive()) {

			eagle.setPosition(eagle.getHistory().pop());
			sword.setPosition(eagle.getPosition());
			eagle.flying(true);
		}

		else {
			sword.setPosition(eagle.getPosition());
			eagle.flying(false);
		}
	}

	/*
	 * AUXILIARY METHODS
	 */

	private void resetBoard() {

		for (int i = 0; i < board.length; ++i)
			board[i] = Arrays.copyOf(builder[i], board[i].length);
	}

	private boolean dragonOverSword() {

		for (Dragon d : dragons) {
			if (d.getPosition().equals(sword.getPosition()))
				return true;
		}

		return false;
	}

	public void startEagle() {

		hero.setEagle(false);
		eagle.addToStack(eagle.getPosition());
		eagle.launch();
		eagle.setWithHero(false);
		eagleMoves();
	}

	private void checkEagleAndHeroPositions() {

		if (hero.getPosition().equals(eagle.getPosition()) && eagle.hasSword()) {
			hero.changeSymbol();
			hero.setEagle(true);
			eagle.setWithHero(true);
		}
	}

	private void checkSwordAndHeroPositions() {

		if (hero.getPosition().equals(sword.getPosition())) {

			sword.changeState(true);
			hero.changeSymbol();
		}
	}

	private void checkSwordAndDragonPositions(Dragon d, Sword sword) {

		if (d.getPosition().equals(sword.getPosition())) {

			if (d.isAsleep())
				d.changeSymbol('f');

			else
				d.changeSymbol('F');
		}

		else {

			if (d.isAsleep())
				d.changeSymbol('d');

			else
				d.changeSymbol('D');
		}
	}

	private static final String savedGamesFolder = System.getProperty("user.dir") + "/Saved Games/";
	
	public void save(String saveName) throws IOException {
		
		File savesFolder = new File(savedGamesFolder);
		if (!savesFolder.exists())
			savesFolder.mkdir();

		ObjectOutputStream file = null;
		
		try {
			file = new ObjectOutputStream(new FileOutputStream(savedGamesFolder
					+ saveName));
			file.writeObject(this);
			file.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Builder getCreateMaze() {
		return createMaze;
	}
}
