package XXLChess.entities;

import XXLChess.utils.Coordinate;

/**
 * The Queen class represents a Queen chess piece.
 * A Queen can any number of unoccupied squares in any direction -- horizontally, vertically, or diagonally.
 * The Queen's symbol is 'q' for white and 'Q' for black.
 */
public class Queen extends Piece {
    /**
     * Constructor for creating a new instance of a Queen piece.
     * @param owner the Owner (BLACK/WHITE) of this piece.
     * @param coordinate the initial Coordinate for this piece.
     */
    public Queen(Owner owner, Coordinate coordinate) {
        super(owner, coordinate);
    }

    /**
     * Returns the character symbol for this piece.
     * The symbol is 'q' for a white Queen and 'Q' for a black Queen.
     *
     * @return the character symbol for this piece.
     */
    @Override
    public char toChar() {
        if(owner == Owner.BLACK){
            return 'Q';
        }else {
            return 'q';
        }
    }

    /**
     * Determines if this Queen can move to a given target coordinate on a given board.
     * The Queen can move like a Bishop or a Rook.
     *
     * @param board  the Board on which to test the move.
     * @param target the target Coordinate to move to.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean canEat(Board board, Coordinate target) {
        int dx = Math.abs(target.getX() - this.coordinate.getX());
        int dy = Math.abs(target.getY() - this.coordinate.getY());

        // Check if the move is valid for a Bishop
        if (dx == dy) {
            if (!board.isPathClear(this.coordinate, target)) {
                return false;
            }
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
