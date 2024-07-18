package XXLChess.entities;

import XXLChess.utils.Coordinate;

/**
 * The parent class of each type of chess piece.
 */
public abstract class Piece {
    protected Coordinate coordinate;
    protected final Owner owner;

    public Piece(Owner owner, Coordinate coordinate) {
        this.coordinate = coordinate;
        this.owner = owner;
    }

    public Piece(Piece piece) {
        this.coordinate = piece.coordinate;
        this.owner = piece.owner;
    }

    public abstract char toChar();

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Owner getOwner() {
        return owner;
    }

    public abstract boolean canEat(Board board, Coordinate target);

    public boolean canMoveTo(Board board, Coordinate target) {
        return canEat(board, target);
    }

}
