package maze.test;

import static org.junit.Assert.*;

import org.junit.Test;

import maze.builder.*;
import maze.logic.*;
import maze.logic.Dragon.DragonMode;

public class heroTests {

	@Test
	public void test1_moveToAdjacentBlankCell() {

		Game game = new Game(new Default(), DragonMode.FIXED, 'd');
		Point oldPositionHero = new Point(game.getHero().getPosition());

		game.heroMoves("d");
		assertNotEquals(game.getHero().getPosition(), oldPositionHero); // hero moved
		assertEquals(new Point(2,1), game.getHero().getPosition()); // hero moved correctly
		game.heroMoves("a");
		assertEquals(oldPositionHero, game.getHero().getPosition()); // returns to original position
		game.heroMoves("s");
		assertEquals(new Point(1,2), game.getHero().getPosition());
	}

	@Test
	public void test2_moveToWall() {

		Game game = new Game(new Default(), DragonMode.FIXED, 'd');
		Point oldPositionHero = new Point(game.getHero().getPosition());

		game.heroMoves("w");
		assertEquals(game.getHero().getPosition(), oldPositionHero);
		game.heroMoves("a");
		assertEquals(game.getHero().getPosition(), oldPositionHero);
	}

	@Test
	// verifies if the hero catches the sword and becomes armed
	public void test3_hasCaughtSword() {

		Game game = new Game(new Default(), DragonMode.FIXED, 'd');
		String moves = "dddssssaaasss";
		for(int i = 0; i < moves.length(); ++i) {

			game.heroMoves(moves.substring(i,i+1));
		}

		assertTrue(game.getSword().isCaught());
		assertTrue(game.getHero().isArmed());
	}

	@Test
	public void test4_isKilledByDragon() {

		Game game = new Game(new Default(), DragonMode.FIXED, 'd');
		String moves = "s";

		for(int i = 0; i < moves.length(); ++i) {
			
			game.heroMoves(moves.substring(i,i+1));
			game.update();
		}

		assertFalse(game.getHero().isAlive());
	}

	@Test
	public void test5_hasKilledTheDragon() {

		Game game = new Game(new Default(), DragonMode.FIXED, 'd');
		String moves = "dddssssaaassswwww";
		for(int i = 0; i < moves.length(); ++i) {

			game.heroMoves(moves.substring(i,i+1));
			game.update();
		}

		assertFalse(game.getDragons()[0].isAlive());
		assertTrue(game.getHero().hasKilledADragon());
	}
	
	@Test
	public void test6_goToExit_win() {

		Game game = new Game(new Default(), DragonMode.FIXED, 'd');
		String moves = "dddssssaaassswwwwsdddddsssddwwwd";
		for(int i = 0; i < moves.length(); ++i) {

			game.heroMoves(moves.substring(i,i+1));
			game.update();
		}

		assertTrue(game.isOver());
	}
	
	public void test7_goToExit_notWinninig() {

		Game game = new Game(new Default(), DragonMode.FIXED, 'd');
		
		// going straight to exit
		String moves = "dddddddssssd";
		for(int i = 0; i < moves.length(); ++i) {

			game.heroMoves(moves.substring(i,i+1));
			game.update();
		}
		
		//assertEquals(new Point(8,5),game.getHero().getPosition());
		assertFalse(game.isOver());
		
		
		// getting the sword and going to exit without killing the dragon
		game.getHero().setPostion(1,1);
		moves = "dddssssaaassswwwdddddsssddwwwd";
		for(int i = 0; i < moves.length(); i++) {

			game.heroMoves(moves.substring(i,i+1));
			game.update();
		}

		//assertEquals(new Point(8,5),game.getHero().getPosition());
		assertFalse(game.isOver());
	}

}

