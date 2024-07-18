package XXLChess;

import XXLChess.entities.Bishop;
import XXLChess.entities.Board;

import XXLChess.entities.Owner;
import org.testng.annotations.Test;
import XXLChess.utils.Coordinate;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class BishopTest {
    @Test
    public void testCanMoveTo() {
        // Initialize a chess board
        Board board = new Board();

        // Initialize a Bishop piece at coordinate (4, 4)
        Bishop bishop = new Bishop(Owner.WHITE, new Coordinate(4, 4));
        board.addPiece(bishop);

        // Bishop should be able to move diagonally
        assertTrue(bishop.canEat(board, new Coordinate(6, 6))); // two squares diagonally right
        assertTrue(bishop.canEat(board, new Coordinate(2, 2))); // two squares diagonally left

        // Bishop can't move horizontally or vertically
        assertFalse(bishop.canEat(board, new Coordinate(4, 7))); // horizontal move
        assertFalse(bishop.canEat(board, new Coordinate(7, 4))); // vertical move

        // Bishop can't move outside of its ability
        assertFalse(bishop.canEat(board, new Coordinate(5, 7))); // outside of its move pattern
    }
}
