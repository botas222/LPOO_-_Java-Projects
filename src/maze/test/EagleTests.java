package maze.test;

import static org.junit.Assert.*;

import org.junit.Test;

import maze.builder.*;
import maze.logic.*;
import maze.logic.Dragon.DragonMode;

public class EagleTests {
	
	@Test
	public void test1_EagleInitialPosition() {

		Game game = new Game(new Default(), DragonMode.FIXED, 'd');
		String moves = "dddssssaa";
		for(int i = 0; i < moves.length(); ++i) {

			game.heroMoves(moves.substring(i,i+1));
		}

		assertEquals(game.getEagle().getPosition(), game.getHero().getPosition());
	}
	
	@Test
	public void test2_HeroLaunchedEagle() {

		Game game = new Game(new Default(), DragonMode.FIXED, 'd');
		String moves = "dddssssaa";
		for(int i = 0; i < moves.length(); ++i) {

			game.heroMoves(moves.substring(i,i+1));
		}

		game.startEagle();
		assertTrue(game.getEagle().hasBeenLaunched());
		assertTrue(game.getEagle().isFlying());
		assertNotEquals(game.getEagle().getPosition(), game.getHero().getPosition());
	}
	
	@Test
	public void test3_EagleCaughtSword() {
		Game game = new Game(new Default(), DragonMode.FIXED, 'd');
		String moves = "dddssssaa";
		for(int i = 0; i < moves.length(); ++i) {

			game.heroMoves(moves.substring(i,i+1));
		}

		game.startEagle();
		assertTrue(game.getEagle().hasBeenLaunched());
		assertTrue(game.getEagle().isFlying());
		
		while(!game.getEagle().getPosition().equals(game.getSword().getPosition())) {
			game.eagleMoves();
		}
		
		assertNotEquals(game.getEagle().getPosition(), game.getHero().getPosition());
		assertEquals(game.getSword().getPosition(), game.getEagle().getPosition());
		assertTrue(game.getEagle().hasSword());
		assertTrue(game.getSword().isCaught());
	}
	
	@Test
	public void test4_EagleTryCatchSword_Die() {
		Game game = new Game(new Default(), DragonMode.FIXED, 'd');
		game.getDragons()[0].setPostion(1,8);
		game.getDragons()[0].setIsAsleep(true);
		
		String moves = "dddssssaa";
		for(int i = 0; i < moves.length(); ++i) {

			game.heroMoves(moves.substring(i,i+1));
		}

		game.startEagle();
		assertTrue(game.getEagle().hasBeenLaunched());
		assertTrue(game.getEagle().isFlying());
		
		while(!game.getEagle().getPosition().equals(game.getSword().getPosition())) {
			game.eagleMoves();
			game.update();
		}
		
		game.getDragons()[0].setIsAsleep(false);
		game.update();
				
		assertNotEquals(game.getEagle().getPosition(), game.getHero().getPosition());
		assertEquals(game.getSword().getPosition(), game.getEagle().getPosition());
		assertFalse(game.getEagle().hasSword());
		assertFalse(game.getSword().isCaught());
		assertFalse(game.getEagle().isAlive());
	}
	
	 @Test
	    public void test5_dieAfterGettingToInitialPosition() {
	        Game game = new Game(new Default(), DragonMode.FIXED, 'd');
	        game.getDragons()[0].setPostion(1,8);
	        game.getDragons()[0].setIsAsleep(true);

	        String moves = "dlddddddddddmmmmmmmmmmm";
	        for(int i = 0; i < moves.length(); ++i) {

	            game.heroMoves(moves.substring(i,i+1));
	            game.update();
	        }

	        assertTrue(game.getEagle().hasBeenLaunched());

	        game.getDragons()[0].setPostion(2,1);
	        game.update();
	        assertFalse(game.getEagle().isAlive());
	        assertFalse(game.getEagle().hasSword());
	        assertFalse(game.getSword().isCaught());
	    }
		
}
