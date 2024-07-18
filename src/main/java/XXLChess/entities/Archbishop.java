package XXLChess.entities;

import XXLChess.utils.Coordinate;

/**
 * The Archbishop class represents an Archbishop chess piece.
 * In XXL Chess, an Archbishop is a piece that combines the powers of a Knight and a Bishop.
 * The Archbishop's symbol is 'h' for white and 'H' for black.
 */
public class Archbishop extends Piece {
    /**
     * Constructs an Archbishop with the given owner and coordinate.
     *
     * @param owner      The owner, which can be either WHITE or BLACK.
     * @param coordinate The initial coordinate of the piece.
     */
    public Archbishop(Owner owner, Coordinate coordinate) {
        super(owner, coordinate);
    }

    /**
     * Returns the character representation of the Archbishop, which is different for different owners.
     *
     * @return 'H' if the owner is BLACK, 'h' if the owner is WHITE.
     */
    @Override
    public char toChar() {
        if (owner == Owner.BLACK) {
            return 'H';
        } else {
            return 'h';
        }
    }

    /**
     * Checks if the Archbishop can move to the target coordinate on the given board.
     * The Archbishop can move like a Knight or a Bishop.
     *
     * @param board  The current chess board.
     * @param target The target coordinate.
     * @return true if the move is legal, false otherwise.
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

        // Check if the move is valid for a Bishop
        if (dx == dy) {
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
