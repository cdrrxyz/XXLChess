package XXLChess.entities;

import XXLChess.utils.Color;
import XXLChess.utils.Coordinate;

/**
 * Represents the Block in the game of Chess. A Block has a color and a coordinate.
 */
public class Block {
    private Color color;
    private Coordinate coordinate;

    /**
     * Constructs a Block with the given color and coordinate.
     *
     * @param color      The color of the block, which can be different based on the game's context.
     * @param coordinate The coordinate of the block on the game board.
     */
    public Block(Color color, Coordinate coordinate) {
        this.color = color;
        this.coordinate = coordinate;
    }

    /**
     * Gets the color of the block.
     *
     * @return The color of the block.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the color of the block.
     *
     * @param color The color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Gets the coordinate of the block on the game board.
     *
     * @return The coordinate of the block.
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Sets the coordinate of the block on the game board.
     *
     * @param coordinate The coordinate to set.
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
