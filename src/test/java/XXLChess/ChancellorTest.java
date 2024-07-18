package XXLChess;

import XXLChess.entities.Chancellor;
import XXLChess.entities.Board;
import XXLChess.entities.Owner;
import org.testng.annotations.Test;
import XXLChess.utils.Coordinate;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ChancellorTest {
    @Test
    public void testCanMoveTo() {
        // Initialize a chess board
        Board board = new Board();

        // Initialize a Chancellor piece at coordinate (4, 4)
        Chancellor chancellor = new Chancellor(Owner.WHITE, new Coordinate(4, 4));
        board.addPiece(chancellor);

        // Chancellor should be able to move like a Knight
        assertTrue(chancellor.canEat(board, new Coordinate(6, 5))); // two squares forward, one square right
        assertTrue(chancellor.canEat(board, new Coordinate(3, 2))); // one square backward, two squares left

        // Chancellor should be able to move like a Rook
        assertTrue(chancellor.canEat(board, new Coordinate(4, 7))); // three squares to the right
        assertTrue(chancellor.canEat(board, new Coordinate(4, 1))); // three squares to the left

        // Chancellor can't move outside of its ability
        assertFalse(chancellor.canEat(board, new Coordinate(5, 7))); // outside of its move pattern
    }
}
