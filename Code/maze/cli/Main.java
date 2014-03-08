package maze.cli;
import maze.logic.*;
import maze.builder.*;
import java.util.Scanner;

public class Main {

	// actualize the board
	public static void print(MazeBuilder board) {
		for (int i = 0; i < board.getMaze().length; ++i) {
			for (int j = 0; j < board.getMaze().length; ++j) {
				System.out.print(board.getMaze()[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void terminationMessages(Game general) {
		if(general.heroVsDragon() == 1) {
			System.out.println("-------------------------------------------------------------");
			System.out.println("You have just killed a Dragon! Now you can go to the Exit (S)");
			System.out.println("-------------------------------------------------------------");
		}
		else if(general.heroVsDragon() == 2) {
			System.out.println("------------------------------------------");
			System.out.println("The Dragon killed you! Sorry, try again :(");
			System.out.println("------------------------------------------");
		}

	}

	public static void dragonMoves(Game general) {
		//if Dragon is asleep then generate a int (3) to how many moves he stay on that state
		//else, and Dragon is waked then generate a int (5) to how many moves he can stay on that state

		if(general.getDragons().isAlive()) {
			//if is dragon asleep then dragon don't move
			if(!general.getDragons().isAsleep()) {
				//System.out.println("aqui nao dormir. Mim Tarzan, tu Jane.");
				general.getDragons().updateBoard(general.getBoard());
				general.getDragons().move(Direction.values()[Utils.getRandomInteger(4)], general);
			}
			else {
				//System.out.println("aqui dormir. Mim Tarzan, tu Jane.");
				general.getDragons().updateBoard(general.getBoard());
			}
		}
	}

	public static void heroMoves(Game general, Scanner input) {

		//hero moves function
		if(general.getHero().isAlive()) {
			general.getHero().updateBoard(general.getBoard());
			System.out.print("Choose your move W/A/S/D: ");
			String moveHero = input.nextLine();

			switch(moveHero) {
			/*case "e":
				eagle.eagleHasLauched();

				break;*/
			case "w":
				general.getHero().move(Direction.UP, general);
				break;
			case "s":
				general.getHero().move(Direction.DOWN, general);
				break;
			case "a":
				general.getHero().move(Direction.LEFT, general);
				break;
			case "d":
				general.getHero().move(Direction.RIGHT, general);
				break;
			default:
				break;
			}
			/*if(!eagle.hasEagleLauched)
				eagle.setPosition(hero.getPosition());*/
		}
	}

	// the inputs by user and random moves to other characters
	public static void runGame(Game general) {
		Scanner sc = new Scanner(System.in);
		int howManyMoves = 0;

		//print on begin
		//general.updatePositions();
		//print(general.getBoard());

		//initialize game cycle
		while(!general.isGameOver()) {
			System.out.println("\n");
			terminationMessages(general);
			general.updatePositions();
			print(general.getBoard());

			//-------------Initialize Moves---------------

			//Dragon Moves
			//Generate his asleep attribute
			if(howManyMoves == 0) {
				general.getDragons().setIsAsleep(Utils.getRandomBoolean());
				if(general.getDragons().isAsleep())
					howManyMoves = Utils.getRandomInteger(3) + 1;
				else
					howManyMoves = Utils.getRandomInteger(5) + 1;
			}
			dragonMoves(general);

			//Hero Moves
			heroMoves(general, sc);

			if(general.isGameOver() && general.getHero().hasKilledADragon()) {
				System.out.println("---------------------------");
				System.out.println("|                         |");
				System.out.println("|Congratulations, You Won!|");
				System.out.println("|          :-D            |");
				System.out.println("---------------------------");
			}

			howManyMoves--;
		}
		sc.close();
	}

	public static void main(String args[]) {
		System.out.println("Choose the type of game you want to play:");
		System.out.println("\nDefault maze\t- (D)");
		System.out.println("Random maze\t- (R)");

		Scanner sc = new Scanner(System.in);
		String mode = sc.nextLine().toUpperCase();

		while (!mode.equals("D") && !mode.equals("R")) {
			System.out.println("Select a valid game mode: ");
			mode = sc.nextLine().toUpperCase();
		}

		switch (mode) {
		case "D":
			Default d1 = new Default();
			Game g1 = new Game(d1,0);
			runGame(g1);
			break;
		case "R":
			int size;
			do {
				System.out.println("Insert the size: ");
				size = sc.nextInt();
			} while (size % 2 == 0);

			Generator b2 = new Generator(size);
			Game g2 = new Game(b2);
			runGame(g2);
			break;
		default:
			System.out.println("UNEXPECTED VALUE!");
			break;
		}
		sc.close();
	}
}