package XXLChess.entities;

import XXLChess.utils.Coordinate;

import java.util.ArrayList;

import static XXLChess.utils.Constants.BOARD_SIZE;

/**
 * The King class represents a King chess piece.
 * A King can move one square in any direction (horizontally, vertically, or diagonally).
 * The King's symbol is 'k' for white and 'K' for black.
 */
public class King extends Piece {
    public boolean haveBeenMove = false;

    @Override
    public void setCoordinate(Coordinate coordinate) {
        this.haveBeenMove = true;
        super.setCoordinate(coordinate);
    }

    /**
     * Constructor for creating a new instance of a King piece.
     *
     * @param owner      the Owner (BLACK/WHITE) of this piece.
     * @param coordinate the initial Coordinate for this piece.
     */
    public King(Owner owner, Coordinate coordinate) {
        super(owner, coordinate);
    }

    /**
     * Returns the character symbol for this piece.
     * The symbol is 'k' for a white King and 'K' for a black King.
     *
     * @return the character symbol for this piece.
     */
    @Override
    public char toChar() {
        if(owner == Owner.BLACK){
            return 'K';
        }else {
            return 'k';
        }
    }

    /**
     * Determines if this King can move to a given target coordinate on a given board.
     * The King can move one space in any direction.
     *
     * @param board  the Board on which to test the move.
     * @param target the target Coordinate to move to.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean canEat(Board board, Coordinate target) {
        Coordinate current = this.coordinate; // Assuming a method to get current position of the piece
        int deltaX = Math.abs(current.getX() - target.getX());
        int deltaY = Math.abs(current.getY() - target.getY());

        // Check if the move is only one square in any direction
        if ((deltaX == 1 && deltaY <= 1) || (deltaY == 1 && deltaX <= 1)) {
            // Check if there is no piece of the same player at the target location
            Piece targetPiece = board.getPieceAt(target);
            return targetPiece == null || targetPiece.getOwner() != this.getOwner();
        }

        return false;
    }
    /**
     * Get a list of surrounding Coordinates within the board boundary.
     *
     * @return ArrayList of surrounding Coordinates.
     */
    public ArrayList<Coordinate> getSurroundingCoordinates() {
        ArrayList<Coordinate> surroundingCoordinates = new ArrayList<>();

        int x = this.getCoordinate().getX();
        int y = this.getCoordinate().getY();

        // The directions around a piece
        int[] directions = {-1, 0, 1};

        for (int dx : directions) {
            for (int dy : directions) {
                if (!(dx == 0 && dy == 0)) {  // Ignore the piece itself
                    int newX = x + dx;
                    int newY = y + dy;

                    // Check if the coordinate is within the board
                    if (newX >= 0 && newX < BOARD_SIZE && newY >= 0 && newY < BOARD_SIZE) {
                        surroundingCoordinates.add(new Coordinate(newX, newY));
                    }
                }
            }
        }

        return surroundingCoordinates;
    }

}
