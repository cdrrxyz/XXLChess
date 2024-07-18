package XXLChess;

//import org.reflections.Reflections;
//import org.reflections.scanners.Scanners;

import XXLChess.entities.*;
import XXLChess.utils.Color;
import XXLChess.utils.Constants;
import XXLChess.utils.Coordinate;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.core.PFont;
import processing.event.MouseEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.awt.Font;
import java.io.*;
import java.util.*;

import static XXLChess.utils.Constants.*;

public class App extends PApplet {

    public static final int SPRITESIZE = 480;
    public static final int CELLSIZE = 48;
    public static final int SIDEBAR = 120;
    public static final int BOARD_WIDTH = 14;

    public static int WIDTH = CELLSIZE * BOARD_WIDTH + SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH * CELLSIZE;
    public static final int FPS = 60;


    // Image
    private PImage AMAZON_BLACK;
    private PImage ARCHBISHOP_BLACK;
    private PImage BISHOP_BLACK;
    private PImage CAMEL_BLACK;
    private PImage CHANCELLOR_BLACK;
    private PImage KING_BLACK;
    private PImage KNIGHT_KING_BLACK;
    private PImage KNIGHT_BLACK;
    private PImage PAWN_BLACK;
    private PImage QUEEN_BLACK;
    private PImage ROOK_BLACK;
    private PImage AMAZON_WHITE;
    private PImage ARCHBISHOP_WHITE;
    private PImage BISHOP_WHITE;
    private PImage CAMEL_WHITE;
    private PImage CHANCELLOR_WHITE;
    private PImage KING_WHITE;
    private PImage KNIGHT_KING_WHITE;
    private PImage KNIGHT_WHITE;
    private PImage PAWN_WHITE;
    private PImage QUEEN_WHITE;
    private PImage ROOK_WHITE;

    public String configPath;

    Board board;
    private boolean userBlock;


    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
     */
    public void setup() {
        frameRate(FPS);


        // Load images during setup

        AMAZON_BLACK = loadImage("src/main/resources/XXLChess/b-amazon.png");
        ARCHBISHOP_BLACK = loadImage("src/main/resources/XXLChess/b-archbishop.png");
        BISHOP_BLACK = loadImage("src/main/resources/XXLChess/b-bishop.png");
        CAMEL_BLACK = loadImage("src/main/resources/XXLChess/b-camel.png");
        CHANCELLOR_BLACK = loadImage("src/main/resources/XXLChess/b-chancellor.png");
        KING_BLACK = loadImage("src/main/resources/XXLChess/b-king.png");
        KNIGHT_KING_BLACK = loadImage("src/main/resources/XXLChess/b-knight-king.png");
        KNIGHT_BLACK = loadImage("src/main/resources/XXLChess/b-knight.png");
        PAWN_BLACK = loadImage("src/main/resources/XXLChess/b-pawn.png");
        QUEEN_BLACK = loadImage("src/main/resources/XXLChess/b-queen.png");
        ROOK_BLACK = loadImage("src/main/resources/XXLChess/b-rook.png");
        AMAZON_WHITE = loadImage("src/main/resources/XXLChess/w-amazon.png");
        ARCHBISHOP_WHITE = loadImage("src/main/resources/XXLChess/w-archbishop.png");
        BISHOP_WHITE = loadImage("src/main/resources/XXLChess/w-bishop.png");
        CAMEL_WHITE = loadImage("src/main/resources/XXLChess/w-camel.png");
        CHANCELLOR_WHITE = loadImage("src/main/resources/XXLChess/w-chancellor.png");
        KING_WHITE = loadImage("src/main/resources/XXLChess/w-king.png");
        KNIGHT_KING_WHITE = loadImage("src/main/resources/XXLChess/w-knight-king.png");
        KNIGHT_WHITE = loadImage("src/main/resources/XXLChess/w-knight.png");
        PAWN_WHITE = loadImage("src/main/resources/XXLChess/w-pawn.png");
        QUEEN_WHITE = loadImage("src/main/resources/XXLChess/w-queen.png");
        ROOK_WHITE = loadImage("src/main/resources/XXLChess/w-rook.png");
        // Load config
        JSONObject conf = loadJSONObject(new File(this.configPath));

        JSONObject timeControls = conf.getJSONObject("time_controls");

        JSONObject player = timeControls.getJSONObject("player");
        JSONObject cpu = timeControls.getJSONObject("cpu");

        int playerSeconds = player.getInt("seconds");
        int cpuSeconds = cpu.getInt("seconds");

        int playerIncrement = player.getInt("increment");
        int cpuIncrement = player.getInt("increment");


        if (conf.get("player_colour").equals("white")) {
            board = new Board(Owner.WHITE);
            board.timeWhite = playerSeconds * FPS;
            board.timeBlack = cpuSeconds * FPS;
            board.timeWhiteIncrement = playerIncrement * FPS;
            board.timeBlackIncrement = cpuIncrement * FPS;
        } else {
            board = new Board(Owner.BLACK);
            board.timeBlack = playerSeconds * FPS;
            board.timeWhite = cpuIncrement * FPS;
            board.timeWhiteIncrement = cpuIncrement * FPS;
            board.timeBlackIncrement = playerIncrement * FPS;
        }
        isGameEnd = false;
        // Load board
        try {
            BufferedReader reader = new BufferedReader(new FileReader("level1.txt"));
            for (int i = 0; i < BOARD_SIZE; i++) {
                String line = reader.readLine();
                if (line != null) {
                    for (int j = 0; j < line.length(); j++) {
                        char pieceChar = line.charAt(j);
                        if (pieceChar != ' ') {  // Ignore empty spaces.
                            Coordinate coordinate = new Coordinate(j, i);
                            Piece piece = PieceFactory.buildPiece(pieceChar, coordinate);
                            board.addPiece(piece);
                        }
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    public void keyPressed() {
        // Press 'r' to restart
        if (key == 'r') {
            // reset game
            if (isGameEnd) {
                setup();
                isGameEnd = false;
                draw();
            }

        }
        // Press 'e' to resign
        if (key == 'e') {
            board.gameStatus = GAME_STATUS.RESIGNED;
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    public void keyReleased() {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // Parse to coordinate
        x = x / BLOCK_A;
        y = y / BLOCK_A;
        board.clickBoard(new Coordinate(x, y));

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    private boolean isGameEnd = false;

    /**
     * Draw all elements in the game by current frame.
     */
    public void draw() {
        if (isGameEnd) {
            return;
        }

        background(150);

        if (board.getTurns() == Owner.BLACK) {
            board.timeBlack--;
        } else {
            board.timeWhite--;
        }

        if (board.getTurns() != board.getPlayerHandle()) {
            board.AIMove();
        }

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                drawRect(BROWN, new Coordinate(x, y));
            }
        }

        ArrayList<Block> blocks = board.getBlocks();

        for (Block block : blocks) {
            drawRect(block.getColor(), block.getCoordinate());
        }

        for (Piece piece : board.getPieces()) {
            drawPiece(piece);
        }

        fill(255);
        textSize(24);
        int seconds = (board.timeBlack / FPS) % 60;
        int minutes = (board.timeBlack / FPS) / 60;
        String timeString = nf(minutes, 2) + ":" + nf(seconds, 2);
        text(timeString, 680, 60);

        seconds = (board.timeWhite / FPS) % 60;
        minutes = (board.timeWhite / FPS) / 60;
        timeString = nf(minutes, 2) + ":" + nf(seconds, 2);
        text(timeString, 680, 632);

        if (board.gameStatus == GAME_STATUS.NO_WIN) {
            textSize(15);
            text("Stalemate – draw!\nPressed r to restart game", 680, 300,100,100);
            isGameEnd = true;
        } else if (board.gameStatus == GAME_STATUS.GAME_OVER) {
            if (board.winner == Owner.WHITE) {
                textSize(15);
                text("You won by checkmate!\nPress 'r' to restart the game", 680, 300,100,100);
            } else {
                textSize(15);
                text("You lost by checkmate!\nPress 'r' to restart the game", 680, 300,100,100);
                isGameEnd = true;
            }

            for (Block block : board.getContributeBlocks()) {
                drawRect(block.getColor(), block.getCoordinate());
            }

        }

        if (board.timeWhite < 0) {
            textSize(15);
            text("You lost on time!\nPress 'r' to restart game", 680, 300,100,100);
            isGameEnd = true;
        }
        if (board.timeBlack < 0) {
            textSize(15);
            text("You win on time!\nPress 'r' to restart the game", 680, 300,100,100);
            isGameEnd = true;
        }

        if (board.gameStatus == GAME_STATUS.RESIGNED) {
            textSize(15);
            text("You resigned!\nPress 'r' to restart game",680,300,100,100);
            isGameEnd = true;
        }

        if (board.isMyKingChecked() || board.isOtherKingChecked()) {
            textSize(24);
            text("Check!", 680, 300);
            if (board.checkHint >= 0 ) {
                textSize(15);
                text("You must defend your king!",680,350,100,100);
            }
        }





    }

    // Add any additional methods or attributes you want. Please put classes in different files.


    public static void main(String[] args) {
        PApplet.main("XXLChess.App");
    }

    private void drawRect(Color color, Coordinate coordinate) {
        noStroke();
        if (color.equals(BROWN) && (coordinate.getX() + coordinate.getY()) % 2 == 0) {
            color = LIGHT_BROWN;
        }
        if (color.equals(BLUE) && (coordinate.getX() + coordinate.getY()) % 2 == 0) {
            color = LIGHT_BLUE;
        }
        fill(color.getR(), color.getG(), color.getB());
        rect(
                Constants.BLOCK_A * coordinate.getX(),
                Constants.BLOCK_A * coordinate.getY(),
                Constants.BLOCK_A,
                Constants.BLOCK_A
        );

    }

    private void drawPiece(Piece piece) {
        switch (piece.toChar()) {
            case 'R':
                image(ROOK_BLACK, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'r':
                image(ROOK_WHITE, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'P':
                image(PAWN_BLACK, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'p':
                image(PAWN_WHITE, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'N':
                image(KNIGHT_BLACK, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'n':
                image(KNIGHT_WHITE, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'B':
                image(BISHOP_BLACK, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'b':
                image(BISHOP_WHITE, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'H':
                image(ARCHBISHOP_BLACK, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'h':
                image(ARCHBISHOP_WHITE, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'C':
                image(CAMEL_BLACK, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'c':
                image(CAMEL_WHITE, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'G':
                image(KNIGHT_KING_BLACK, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'g':
                image(KNIGHT_KING_WHITE, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'A':
                image(AMAZON_BLACK, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'a':
                image(AMAZON_WHITE, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'K':
                image(KING_BLACK, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'k':
                image(KING_WHITE, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'E':
                image(CHANCELLOR_BLACK, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'e':
                image(CHANCELLOR_WHITE, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'Q':
                image(QUEEN_BLACK, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            case 'q':
                image(QUEEN_WHITE, Constants.BLOCK_A * piece.getCoordinate().getX(), Constants.BLOCK_A * piece.getCoordinate().getY(), Constants.BLOCK_A, Constants.BLOCK_A);
                break;
            default:
                break;

        }
    }

}

