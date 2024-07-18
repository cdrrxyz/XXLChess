package XXLChess.entities;

import XXLChess.utils.Coordinate;

/**
 * The Camel class represents a Camel chess piece.
 * In XXL Chess, a Camel is a piece that moves in an extended L-shape.
 * This means it moves by jumping to the destination square: two squares in one direction
 * and three squares at a right angle, for a total of five squares.
 * The Camel's symbol is 'c' for white and 'C' for black.
 */
public class Camel extends Piece {
    /**
     * Constructor for creating a new instance of a Camel piece.
     *
     * @param owner      the Owner (BLACK/WHITE) of this piece.
     * @param coordinate the initial Coordinate for this piece.
     */
    public Camel(Owner owner, Coordinate coordinate) {
        super(owner, coordinate);
    }
    /**
     * Returns the character symbol for this piece.
     * The symbol is 'c' for a white Camel and 'C' for a black Camel.
     *
     * @return the character symbol for this piece.
     */
    @Override
    public char toChar() {
        if(owner == Owner.BLACK){
            return 'C';
        }else {
            return 'c';
        }
    }
    /**
     * Determines if this Camel can move to a given target coordinate on a given board.
     * The Camel moves in an extended L-shape: two squares in one direction and three squares at a right angle.
     *
     * @param board  the Board on which to test the move.
     * @param target the target Coordinate to move to.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean canEat(Board board, Coordinate target) {
        int dx = Math.abs(target.getX() - this.coordinate.getX());
        int dy = Math.abs(target.getY() - this.coordinate.getY());

        // Check if the move is valid for a Camel
        if ((dx == 3 && dy == 1) || (dx == 1 && dy == 3)) {
            Piece targetPiece = board.getPieceAt(target);
            // The target square can be empty or contain an opponent's piece
            return targetPiece == null || targetPiece.getOwner() != this.getOwner();
        }

        return false;
    }

}
