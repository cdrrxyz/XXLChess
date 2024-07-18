package XXLChess;

import XXLChess.entities.King;
import XXLChess.entities.Board;
import XXLChess.entities.Owner;
import org.testng.annotations.Test;
import XXLChess.utils.Coordinate;

import java.util.ArrayList;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class KingTest {
    @Test
    public void testCanMoveTo() {
        // Initialize a chess board
        Board board = new Board();

        // Initialize a King piece at coordinate (4, 4)
        King king = new King(Owner.WHITE, new Coordinate(4, 4));
        board.addPiece(king);

        // King should be able to move one square in any direction
        assertTrue(king.canEat(board, new Coordinate(5, 4))); // one square forward
        assertTrue(king.canEat(board, new Coordinate(4, 5))); // one square to the right
        assertTrue(king.canEat(board, new Coordinate(5, 5))); // one square diagonally right

        // King can't move more than one square
        assertFalse(king.canEat(board, new Coordinate(6, 4))); // two squares forward
        assertFalse(king.canEat(board, new Coordinate(4, 6))); // two squares to the right
    }

    @Test
    public void testGetSurroundingCoordinates() {
        // Initialize a King piece at coordinate (4, 4)
        King king = new King(Owner.WHITE, new Coordinate(4, 4));

        // Check the surrounding coordinates
        ArrayList<Coordinate> surrounding = king.getSurroundingCoordinates();
        assertTrue(surrounding.contains(new Coordinate(3, 3))); // upper left
        assertTrue(surrounding.contains(new Coordinate(4, 3))); // upper middle
        assertTrue(surrounding.contains(new Coordinate(5, 3))); // upper right
        assertTrue(surrounding.contains(new Coordinate(3, 4))); // left
        assertTrue(surrounding.contains(new Coordinate(5, 4))); // right
        assertTrue(surrounding.contains(new Coordinate(3, 5))); // lower left
        assertTrue(surrounding.contains(new Coordinate(4, 5))); // lower middle
        assertTrue(surrounding.contains(new Coordinate(5, 5))); // lower right
    }
}
