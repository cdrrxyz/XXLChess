package XXLChess;

import XXLChess.entities.General;
import XXLChess.entities.Board;
import XXLChess.entities.Owner;
import org.testng.annotations.Test;
import XXLChess.utils.Coordinate;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class GeneralTest {
    @Test
    public void testCanMoveTo() {
        // Initialize a chess board
        Board board = new Board();

        // Initialize a General piece at coordinate (4, 4)
        General general = new General(Owner.WHITE, new Coordinate(4, 4));
        board.addPiece(general);

        // General should be able to move like a King
        assertTrue(general.canEat(board, new Coordinate(4, 5))); // one square forward
        assertTrue(general.canEat(board, new Coordinate(5, 5))); // one square diagonally right

        // General should be able to move like a Knight
        assertTrue(general.canEat(board, new Coordinate(6, 5))); // two squares forward, one square right
        assertTrue(general.canEat(board, new Coordinate(3, 2))); // one square backward, two squares left

        // General can't move outside of its ability
        assertFalse(general.canEat(board, new Coordinate(5, 7))); // outside of its move pattern
    }
}
