package XXLChess.entities;

import XXLChess.utils.Coordinate;

import static XXLChess.entities.Owner.WHITE;
/**
 * The Pawn class represents a Pawn chess piece.
 * A pawn can move forward one square, but captures diagonally.
 * The Pawn's symbol is 'p' for white and 'P' for black.
 */
public class Pawn extends Piece {
    /**
     * Constructor for creating a new instance of a Pawn piece.
     *
     * @param owner      the Owner (BLACK/WHITE) of this piece.
     * @param coordinate the initial Coordinate for this piece.
     */
    public Pawn(Owner owner, Coordinate coordinate) {
        super(owner, coordinate);
    }
    /**
     * Returns the character symbol for this piece.
     * The symbol is 'p' for a white Pawn and 'P' for a black Pawn.
     *
     * @return the character symbol for this piece.
     */
    @Override
    public char toChar() {
        if(owner == Owner.BLACK){
            return 'P';
        }else {
            return 'p';
        }
    }
    /**
     * Determines if this Pawn can move to a given target coordinate on a given board.
     * A Pawn can move forward one square, but captures diagonally.
     *
     * @param board  the Board on which to test the move.
     * @param target the target Coordinate to move to.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean canEat(Board board, Coordinate target) {
        int direction = this.getOwner() == Owner.WHITE ? -1 : 1;
        int dx = target.getX() - this.coordinate.getX();
        int dy = (target.getY() - this.coordinate.getY()) * direction;

        // Check if it is a normal move or a capture
        if (Math.abs(dx) == 1) {
            if (dy == 1) {
                // Capture
                Piece targetPiece = board.getPieceAt(target);
                if (targetPiece != null) {
                    return targetPiece.getOwner() != this.getOwner();
                }
            }
        }
        return false;
    }


}
