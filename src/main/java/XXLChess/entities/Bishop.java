package XXLChess.entities;

import XXLChess.utils.Coordinate;

/**
 * The Bishop class represents a Bishop chess piece.
 * A Bishop can move in any direction diagonally.
 * The Bishop's symbol is 'b' for white and 'B' for black.
 */
public class Bishop extends Piece {
    /**
     * Constructs a Bishop with the given owner and coordinate.
     *
     * @param owner      The owner, which can be either WHITE or BLACK.
     * @param coordinate The initial coordinate of the piece.
     */
    public Bishop(Owner owner, Coordinate coordinate) {
        super(owner, coordinate);
    }

    /**
     * Returns the character representation of the Bishop, which is different for different owners.
     *
     * @return 'B' if the owner is BLACK, 'b' if the owner is WHITE.
     */
    @Override
    public char toChar() {
        if (owner == Owner.BLACK) {
            return 'B';
        } else {
            return 'b';
        }
    }

    /**
     * Checks if the Bishop can move to the target coordinate on the given board.
     * The Bishop can move in any number of squares diagonally.
     *
     * @param board  The current chess board.
     * @param target The target coordinate.
     * @return true if the move is legal, false otherwise.
     */
    @Override
    public boolean canEat(Board board, Coordinate target) {
        int dx = Math.abs(target.getX() - this.coordinate.getX());
        int dy = Math.abs(target.getY() - this.coordinate.getY());

        // Check if the move is a diagonal
        if (dx == dy) {
            Piece targetPiece = board.getPieceAt(target);
            // The target square can be empty or contain an opponent's piece
            // And check if the path to the target is clear
            return (targetPiece == null || targetPiece.getOwner() != this.getOwner())
                    && board.isPathClear(this.coordinate, target);
        }

        return false;
    }

}
