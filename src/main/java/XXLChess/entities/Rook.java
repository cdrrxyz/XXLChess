package XXLChess.entities;

import XXLChess.utils.Coordinate;

/**
 * The Rook class represents a Rook chess piece.
 * A Rook can move in a straight line forwards and backwards through any square on the board that isn't occupied by another piece.
 * The Rook's symbol is 'r' for white and 'R' for black.
 */
public class Rook extends Piece {

    public boolean haveBeenMove = false;

    /**
     * Constructor for creating a new instance of a Rook piece.
     * @param owner the Owner (BLACK/WHITE) of this piece.
     * @param coordinate the initial Coordinate for this piece.
     */
    public Rook(Owner owner, Coordinate coordinate) {
        super(owner, coordinate);
    }

    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.haveBeenMove = true;
        super.setCoordinate(coordinate);
    }

    /**
     * Returns the character symbol for this piece.
     * The symbol is 'r' for a white Rook and 'R' for a black Rook.
     *
     * @return the character symbol for this piece.
     */
    @Override
    public char toChar() {
        if (owner == Owner.BLACK) {
            return 'R';
        } else {
            return 'r';
        }
    }

    /**
     * Determines if this Rook can move to a given target coordinate on a given board.
     * The Rook can move horizontally and vertically.
     * @param board  the Board on which to test the move.
     * @param target the target Coordinate to move to.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean canEat(Board board, Coordinate target) {
        // Rooks can move in straight lines horizontally or vertically
        if (this.coordinate.getX() == target.getX() || this.coordinate.getY() == target.getY()) {
            // Check that there are no pieces in the way
            if (!board.isPathClear(this.coordinate, target)) {
                return false;
            }

            // Check if there is no piece of the same player at the target location
            Piece targetPiece = board.getPieceAt(target);
            if (targetPiece == null || targetPiece.getOwner() != this.getOwner()) {
                return true;
            }
        }

        return false;
    }

}
