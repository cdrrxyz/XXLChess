package XXLChess.entities;

import XXLChess.utils.Coordinate;

/**
 * The Knight class represents a Knight chess piece.
 * A Knight can move to any square not already attacked by another piece of its own side,
 * moving two squares horizontally then one square vertically, or moving one square horizontally then two squares vertically.
 * This is a move that can be described as an "L-shape".
 * The Knight's symbol is 'n' for white and 'N' for black.
 */
public class Knight extends Piece {
    /**
     * Constructor for creating a new instance of a Knight piece.
     *
     * @param owner      the Owner (BLACK/WHITE) of this piece.
     * @param coordinate the initial Coordinate for this piece.
     */
    public Knight(Owner owner, Coordinate coordinate) {
        super(owner, coordinate);
    }
    /**
     * Returns the character symbol for this piece.
     * The symbol is 'n' for a white Knight and 'N' for a black Knight.
     *
     * @return the character symbol for this piece.
     */
    @Override
    public char toChar() {
        if(owner == Owner.BLACK){
            return 'N';
        }else {
            return 'n';
        }
    }
    /**
     * Determines if this Knight can move to a given target coordinate on a given board.
     * The Knight moves in an "L-shape": two squares horizontally then one square vertically, or one square horizontally then two squares vertically.
     *
     * @param board  the Board on which to test the move.
     * @param target the target Coordinate to move to.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean canEat(Board board, Coordinate target) {
        int dx = Math.abs(target.getX() - this.coordinate.getX());
        int dy = Math.abs(target.getY() - this.coordinate.getY());

        // Check if the move is an "L" shape
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            Piece targetPiece = board.getPieceAt(target);
            // The target square can be empty or contain an opponent's piece
            return targetPiece == null || targetPiece.getOwner() != this.getOwner();
        }

        return false;
    }

}
