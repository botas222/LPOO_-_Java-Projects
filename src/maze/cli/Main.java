package maze.cli;
import maze.logic.*;
import maze.logic.Dragon.DragonMode;
import maze.builder.*;

import java.util.Scanner;

public class Main {

	// MESSAGES
	
	//


	// update the board
	private static void print(char[][] board) {
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board.length; ++j) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}

	// this is THE function that controls whether the game should run or not
	public static void runGame(Game game) {

		Scanner sc = new Scanner(System.in);

		//initialize game cycle
		while(!game.isOver()) {
			System.out.print("\n\n");
			System.out.println(game.update());
			game.drawElements();
			print(game.getBoard());

			if (game.isOver())
				break;
			
			//Hero Moves
			System.out.print("Choose your move W/A/S/D: ");
			String movement = sc.nextLine();
			game.heroMoves(movement);

			if(game.isOver() && game.getHero().hasKilledADragon())
				System.out.print(Message.winningMessage);
				
		}

		sc.close();
	}

	public static void main(String args[]) {

		System.out.println("Choose the type of game you want to play:");
		System.out.println("\nDefault maze\t- (D)");
		System.out.println("Random maze\t- (R)");

		Scanner sc = new Scanner(System.in);
		String mode = sc.nextLine().toUpperCase();

		// defining game mode
		while (!mode.equals("D") && !mode.equals("R")) {
			System.out.println("Select a valid game mode: ");
			mode = sc.nextLine().toUpperCase();
		}

		// defining dragon mode
		System.out.println("Choose the dragon type:");
		System.out.println("0 - the dragon is fixed");
		System.out.println("1 - the dragon moves around board");
		System.out.println("2 - the dragon moves around the board and sometimes goes to sleep");
		int dMode = sc.nextInt();
		while (dMode != 0 && dMode != 1 && dMode != 2) {
			System.out.print("Inexistent command. Try again: ");
			dMode = sc.nextInt();
		}

		switch (mode) {
		case "D":
			Default d1 = new Default();
			Game g1 = new Game(d1,DragonMode.values()[dMode],'d');
			runGame(g1);
			break;
		case "R":
			int size;
			do {
				System.out.println("Insert the size: ");
				size = sc.nextInt();
			} while (size % 2 == 0 || size < 7);

			System.out.println("Insert the number of dragons: ");
			int n = sc.nextInt();

			Generator b2 = new Generator(size);
			Game g2 = new Game(b2,DragonMode.values()[dMode],n);
			runGame(g2);
			break;
		default:
			System.out.println("UNEXPECTED VALUE!");
			break;
		}

		sc.close();
	}
}