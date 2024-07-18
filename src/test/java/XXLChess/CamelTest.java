package XXLChess;

import XXLChess.entities.Board;
import XXLChess.entities.Camel;
import XXLChess.entities.Owner;
import org.testng.annotations.Test;
import XXLChess.utils.Coordinate;

import static org.testng.Assert.*;

public class CamelTest {
    @Test
    public void testCanMoveTo() {
        // Initialize a chess board
        Board board = new Board();

        // Initialize a Camel piece at coordinate (5, 5)
        Camel camel = new Camel(Owner.WHITE, new Coordinate(5, 5));
        board.addPiece(camel);

        // Camel should be able to move one square along one axis and three squares along the other
        assertTrue(camel.canEat(board, new Coordinate(8, 4))); // three squares forward, one square right
        assertTrue(camel.canEat(board, new Coordinate(6, 2))); // one square forward, three squares left

        // Camel can't move outside of its ability
        assertFalse(camel.canEat(board, new Coordinate(7, 5))); // straight line movement is not allowed
        assertFalse(camel.canEat(board, new Coordinate(8, 7))); // diagonal movement is not allowed
    }
}
