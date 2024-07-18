package XXLChess.entities;

import XXLChess.utils.Coordinate;

/**
 * The General class represents a General chess piece.
 * In XXL Chess, a General is a piece that combines the powers of a King and a Knight.
 * This means it can move one square in any direction (like a King) or it can move in an 'L' shape like a Knight
 * (2 squares in one direction and 1 square at a right angle).
 * The General's symbol is 'g' for white and 'G' for black.
 */
public class General extends Piece {
    /**
     * Constructor for creating a new instance of a General piece.
     *
     * @param owner      the Owner (BLACK/WHITE) of this piece.
     * @param coordinate the initial Coordinate for this piece.
     */
    public General(Owner owner, Coordinate coordinate) {
        super(owner, coordinate);
    }

    /**
     * Returns the character symbol for this piece.
     * The symbol is 'g' for a white General and 'G' for a black General.
     *
     * @return the character symbol for this piece.
     */
    @Override
    public char toChar() {
        if (owner == Owner.BLACK) {
            return 'G';
        } else {
            return 'g';
        }
    }

    /**
     * Determines if this General can move to a given target coordinate on a given board.
     * The General can move like a King or a Knight.
     *
     * @param board  the Board on which to test the move.
     * @param target the target Coordinate to move to.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean canEat(Board board, Coordinate target) {
        int dx = Math.abs(target.getX() - this.coordinate.getX());
        int dy = Math.abs(target.getY() - this.coordinate.getY());

        // Check if the move is valid for a King
        if (dx <= 1 && dy <= 1) {
            Piece targetPiece = board.getPieceAt(target);
            // The target square can be empty or contain an opponent's piece
            return targetPiece == null || targetPiece.getOwner() != this.getOwner();
        }

        // Check if the move is valid for a Knight
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            Piece targetPiece = board.getPieceAt(target);
            // The target square can be empty or contain an opponent's piece
            return targetPiece == null || targetPiece.getOwner() != this.getOwner();
        }

        return false;
    }

}
