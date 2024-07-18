package XXLChess;

import XXLChess.entities.Archbishop;
import XXLChess.entities.Board;

import XXLChess.entities.Owner;
import org.testng.annotations.Test;
import XXLChess.utils.Coordinate;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ArchbishopTest {
    @Test
    public void testCanMoveTo() {
        // Initialize a chess board
        Board board = new Board();

        // Initialize an Archbishop piece at coordinate (4, 4)
        Archbishop archbishop = new Archbishop(Owner.WHITE, new Coordinate(4, 4));
        board.addPiece(archbishop);

        // Archbishop should be able to move like a Knight
        assertTrue(archbishop.canEat(board, new Coordinate(6, 5))); // two squares forward, one square right
        assertTrue(archbishop.canEat(board, new Coordinate(3, 2))); // one square backward, two squares left

        // Archbishop should be able to move like a Bishop
        assertTrue(archbishop.canEat(board, new Coordinate(6, 6))); // two squares diagonally right
        assertTrue(archbishop.canEat(board, new Coordinate(2, 2))); // two squares diagonally left

        // Archbishop can't move outside of its ability
        assertFalse(archbishop.canEat(board, new Coordinate(5, 7))); // outside of its move pattern
    }
}
