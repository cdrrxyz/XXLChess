package XXLChess.entities;

import XXLChess.utils.Coordinate;

public class PieceFactory {
    public static Piece buildPiece(char piece, Coordinate coordinate){
        switch (piece){
            case 'P': return new Pawn(Owner.BLACK, coordinate);
            case 'p': return new Pawn(Owner.WHITE, coordinate);
            case 'R': return new Rook(Owner.BLACK, coordinate);
            case 'r': return new Rook(Owner.WHITE, coordinate);
            case 'N': return new Knight(Owner.BLACK, coordinate);
            case 'n': return new Knight(Owner.WHITE, coordinate);
            case 'B': return new Bishop(Owner.BLACK, coordinate);
            case 'b': return new Bishop(Owner.WHITE, coordinate);
            case 'H': return new Archbishop(Owner.BLACK, coordinate);
            case 'h': return new Archbishop(Owner.WHITE, coordinate);
            case 'C': return new Camel(Owner.BLACK, coordinate);
            case 'c': return new Camel(Owner.WHITE, coordinate);
            case 'G': return new General(Owner.BLACK, coordinate);
            case 'g': return new General(Owner.WHITE, coordinate);
            case 'A': return new Amazon(Owner.BLACK, coordinate);
            case 'a': return new Amazon(Owner.WHITE, coordinate);
            case 'K': return new King(Owner.BLACK, coordinate);
            case 'k': return new King(Owner.WHITE, coordinate);
            case 'E': return new Chancellor(Owner.BLACK, coordinate);
            case 'e': return new Chancellor(Owner.WHITE, coordinate);
            case 'Q': return new Queen(Owner.BLACK, coordinate);
            case 'q': return new Queen(Owner.WHITE, coordinate);
            default: throw new IllegalArgumentException("Invalid piece: " + piece);
        }
    }

}
