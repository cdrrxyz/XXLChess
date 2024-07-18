package XXLChess.entities;

import XXLChess.utils.Coordinate;

/**
 * The Amazon class represents an Amazon chess piece.
 * In XXL Chess, an Amazon is a piece that combines the powers of a Knight, a Bishop and a Rook.
 * The Amazon's symbol is 'a' for white and 'A' for black.
 */
public class Amazon extends Piece {
    /**
     * Creates a new Amazon piece.
     *
     * @param owner      the owner of the piece
     * @param coordinate the coordinate of the piece on the board
     */
    public Amazon(Owner owner, Coordinate coordinate) {
        super(owner, coordinate);
    }

    /**
     * Returns a char representing the Amazon piece. 'A' for black and 'a' for white.
     *
     * @return a char representing the Amazon piece
     */
    @Override
    public char toChar() {
        if (owner == Owner.BLACK) {
            return 'A';
        } else {
            return 'a';
        }
    }

    /**
     * Checks if the Amazon can move to the target coordinate on the board.
     * The Amazon can move like a Knight, Bishop or Rook.
     *
     * @param board  the board the piece is on
     * @param target the target coordinate to move to
     * @return true if the move is valid, false otherwise
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
