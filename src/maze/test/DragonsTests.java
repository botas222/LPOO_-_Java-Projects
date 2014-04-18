package maze.test;

import static org.junit.Assert.*;

import org.junit.Test;

import maze.builder.Default;
import maze.logic.Direction;
import maze.logic.Dragon;
import maze.logic.Game;
import maze.logic.Point;
import maze.logic.Dragon.DragonMode;

public class DragonsTests {

	@Test
	//test if one dragon can move one cell
	public void test1_moveToAdjacentBlankCell() {

		Game game = new Game(new Default(), DragonMode.MOVING, 'd');
		Dragon d = game.getDragons()[0];
		Point oldPositionDragon = new Point(d.getPosition());

		d.move(Direction.UP, game);
		assertNotEquals(d.getPosition(), oldPositionDragon); // dragon moved
		assertEquals(new Point(1,2),d.getPosition()); // dragon moved correctly
		d.move(Direction.DOWN, game);
		assertEquals(oldPositionDragon, d.getPosition()); // returns to original position
	}

	@Test
	public void test2_moveToWall() {

		Game game = new Game(new Default(), DragonMode.MOVING, 'd');
		Dragon d = game.getDragons()[0];
		Point oldPositionDragon = new Point(d.getPosition());

		d.move(Direction.LEFT, game);
		assertEquals(oldPositionDragon, d.getPosition());
		d.move(Direction.RIGHT, game);
		assertEquals(oldPositionDragon, d.getPosition());
	}

	@Test
	public void test3_sleep() {

		Game game = new Game(new Default(),DragonMode.MOVINGANDSLEEPING,'d');
		Dragon d = game.getDragons()[0];
		//Point oldPositionDragon = new Point(d.getPosition());

		assertFalse(d.isAsleep());
	}

    @Test
    public void test4_multipleDragonsMovement() {

        /*
         * 0 - UP
         * 1 - DOWN
         * 2 - LEFT
         * 3 - RIGHT
         */

        Game game = new Game(new Default(),DragonMode.FIXED,3);

        // none of the dragons should move, since FIXED mode is selected
        for (Dragon d : game.getDragons()) {

            Point oldPositionDragon = new Point(d.getPosition());
            game.dragonMoves(d,0);
            assertEquals(oldPositionDragon,d.getPosition());
        }

        Game game2 = new Game(new Default(),DragonMode.MOVING,5);
        Dragon d1 = game2.getDragons()[0];
        Dragon d2 = game2.getDragons()[1];
        Dragon d3 = game2.getDragons()[2];
        Dragon d4 = game2.getDragons()[3];
        Dragon d5 = game2.getDragons()[4];

        d1.setPostion(4,1);
        d2.setPostion(5,1);
        d3.setPostion(6,1);
        d4.setPostion(7,1);
        d5.setPostion(8,1);

        Point oldPositionDragon = new Point(d1.getPosition());
        game.dragonMoves(d1,1);
        assertNotEquals(oldPositionDragon,d1.getPosition());

        oldPositionDragon = d2.getPosition();
        game.dragonMoves(d2,1);
        assertEquals(oldPositionDragon,d2.getPosition());

        oldPositionDragon = d3.getPosition();
        game.dragonMoves(d3,1);
        assertNotEquals(oldPositionDragon,d3.getPosition());

        oldPositionDragon = d4.getPosition();
        game.dragonMoves(d4,1);
        assertEquals(oldPositionDragon,d4.getPosition());

        oldPositionDragon = d5.getPosition();
        game.dragonMoves(d5,1);
        assertNotEquals(oldPositionDragon,d5.getPosition());
    }
}
