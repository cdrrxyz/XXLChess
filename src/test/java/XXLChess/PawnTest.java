package XXLChess;

import XXLChess.entities.Pawn;
import XXLChess.entities.Board;
import XXLChess.entities.Owner;
import org.testng.annotations.Test;
import XXLChess.utils.Coordinate;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class PawnTest {
    @Test
    public void testCanMoveTo() {
        // Initialize a chess board
        Board board = new Board();

        // Initialize a Pawn piece at coordinate (4, 4)
        Pawn pawn = new Pawn(Owner.WHITE, new Coordinate(4, 4));
        board.addPiece(pawn);

        // Pawn should be able to move forward one square
        assertTrue(pawn.canEat(board, new Coordinate(4, 3))); // one square forward

        // Pawn should not be able to move backwards
        assertFalse(pawn.canEat(board, new Coordinate(4, 5))); // one square backward

        // Pawn should not be able to move sideways
        assertFalse(pawn.canEat(board, new Coordinate(5, 4))); // one square right
        assertFalse(pawn.canEat(board, new Coordinate(3, 4))); // one square left

        // Assume a black piece at (5, 5)
        board.addPiece(new Pawn(Owner.BLACK, new Coordinate(5, 5)));

        // Pawn should be able to capture diagonally
        assertTrue(pawn.canEat(board, new Coordinate(5, 5))); // capture

        // Assume a white piece at (3, 5)
        board.addPiece(new Pawn(Owner.WHITE, new Coordinate(3, 5)));

        // Pawn should not be able to capture diagonally if the piece is of the same color
        assertFalse(pawn.canEat(board, new Coordinate(3, 5))); // no capture

    }
}
