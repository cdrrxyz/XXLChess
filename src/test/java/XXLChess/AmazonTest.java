package XXLChess;

import XXLChess.entities.Amazon;
import XXLChess.entities.Board;

import XXLChess.entities.Owner;
import org.testng.annotations.Test;
import XXLChess.utils.Coordinate;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class AmazonTest {
    @Test
    public void testCanMoveTo() {
        // Initialize a chess board
        Board board = new Board();

        // Initialize an Amazon piece at coordinate (4, 4)
        Amazon amazon = new Amazon(Owner.WHITE, new Coordinate(4, 4));
        board.addPiece(amazon);

        // Amazon should be able to move like a Knight
        assertTrue(amazon.canEat(board, new Coordinate(6, 5))); // two squares forward, one square right
        assertTrue(amazon.canEat(board, new Coordinate(3, 2))); // one square backward, two squares left

        // Amazon should be able to move like a Bishop
        assertTrue(amazon.canEat(board, new Coordinate(6, 6))); // two squares diagonally right
        assertTrue(amazon.canEat(board, new Coordinate(2, 2))); // two squares diagonally left

        // Amazon should be able to move like a Rook
        assertTrue(amazon.canEat(board, new Coordinate(4, 7))); // three squares to the right
        assertTrue(amazon.canEat(board, new Coordinate(4, 1))); // three squares to the left

        // Amazon can't move outside of its ability
        assertFalse(amazon.canEat(board, new Coordinate(5, 7))); // outside of its move pattern
    }
}
