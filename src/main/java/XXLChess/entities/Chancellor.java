package XXLChess.entities;

import XXLChess.utils.Coordinate;

/**
 * The Chancellor class represents a Chancellor chess piece.
 * In XXL Chess, a Chancellor is a piece that combines the powers of a Knight and a Rook.
 * This means it can move in an 'L' shape (like a Knight) or it can move in a straight line forwards and backwards (like a Rook).
 * The Chancellor's symbol is 'e' for white and 'E' for black.
 */
public class Chancellor extends Piece {
    /**
     * Constructs a Chancellor with the given owner and coordinate.
     *
     * @param owner      The owner of the Chancellor, which can be either BLACK or WHITE.
     * @param coordinate The coordinate of the Chancellor on the game board.
     */
    public Chancellor(Owner owner, Coordinate coordinate) {
        super(owner, coordinate);
    }

    /**
     * Gets the character representation of the Chancellor based on its owner.
     *
     * @return 'E' if the owner is BLACK, 'e' otherwise.
     */
    @Override
    public char toChar() {
        if (owner == Owner.BLACK) {
            return 'E';
        } else {
            return 'e';
        }
    }

    /**
     * Checks if the Chancellor can move to the target coordinate on the given board.
     * A Chancellor can move one square along one axis and three squares along the other.
     *
     * @param board  The game board.
     * @param target The target coordinate.
     * @return true if the move is valid, false otherwise.
     */

    @Override
    public boolean canEat(Board board, Coordinate target) {
        int dx = Math.abs(target.getX() - this.coordinate.getX());
        int dy = Math.abs(target.getY() - this.coordinate.getY());

        // Check if the move is valid for a Knight
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            Piece targetPiece = board.getPieceAt(target);
            // The target square can be empty or contain an opponent's piece
            return targetPiece == null || targetPiece.getOwner() != this.getOwner();
        }

        // Check if the move is valid for a Rook
        if (dx == 0 || dy == 0) {
            if (!board.isPathClear(this.coordinate, target)) {
                return false;
            }
            Piece targetPiece = board.getPieceAt(target);
            // The target square can be empty or contain an opponent's piece
            return targetPiece == null || targetPiece.getOwner() != this.getOwner();
        }

        return false;
    }

}
