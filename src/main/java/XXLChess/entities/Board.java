package XXLChess.entities;

import XXLChess.utils.Coordinate;

import static XXLChess.utils.Constants.*;

import java.util.*;
import java.util.function.Function;

/**
 * The board on which the game is played.
 */
public class Board {

    public int checkHint = -1;
    public boolean gameOver = false;
    public boolean checked = false;
    public Owner winner = null;

    private Piece[][] board;

    private Owner turns;
    private Owner playerHandle;

    public Owner getPlayerHandle() {
        return playerHandle;
    }

    public GAME_STATUS gameStatus;

    public Owner getTurns() {
        return turns;
    }

    public int timeWhite = 0;
    public int timeWhiteIncrement = 0;
    public int timeBlack = 0;
    public int timeBlackIncrement = 0;


    private ArrayList<King> kings = new ArrayList<>();

    private Piece selectedPiece = null;
    private ArrayList<Block> prevStep = new ArrayList<>();

    public Board() {
        gameStatus = GAME_STATUS.NOT_END;
        turns = Owner.WHITE;
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = null;
            }
        }

    }


    public Board(Owner playerHandle) {
        gameStatus = GAME_STATUS.NOT_END;
        turns = Owner.WHITE;
        this.playerHandle = playerHandle;
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = null;
            }
        }
    }

    public Board(Board src) {
        turns = src.turns;
        this.playerHandle = src.playerHandle;
        this.prevStep = new ArrayList<>(src.prevStep);
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        this.kings = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (src.board[i][j] != null)
                    this.addPiece(PieceFactory.buildPiece(src.board[i][j].toChar(), src.board[i][j].coordinate));

            }
        }
        this.selectedPiece = this.getPieceAt(src.selectedPiece.coordinate);

    }

    public void addPiece(Piece piece) {
        if (piece instanceof King)
            kings.add((King) piece);
        int x = piece.coordinate.getX();
        int y = piece.coordinate.getY();
        board[x][y] = piece;
    }

    public ArrayList<Piece> getPieces() {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != null) {
                    pieces.add(board[i][j]);
                }
            }
        }
        return pieces;
    }


    public void clickBoard(Coordinate coordinate) {
        if (coordinate.getX() < 0 || coordinate.getX() >= 14 || coordinate.getY() < 0 || coordinate.getY() >= 14) {
            if(isMyKingChecked()){
                checkHint = 0;
            }
            return;
        }
        if (selectedPiece == null) {
            Piece piece = board[coordinate.getX()][coordinate.getY()];
            if (piece == null) {
                selectedPiece = null;
                if(isMyKingChecked()){
                    checkHint = 0;
                }
                return;
            }
            if (piece.equals(selectedPiece)) {
                selectedPiece = null;
                if(isMyKingChecked()){
                    checkHint = 0;
                }
                return;
            }
            if (turns == playerHandle && piece.owner == playerHandle) {
                selectedPiece = piece;
                if(isMyKingChecked() && getAccessBlocks().isEmpty()){
                    checkHint = 0;
                }
            }
        } else {
            ArrayList<Block> accessBlocks = getAccessBlocks();
            if(isMyKingChecked() && getAccessBlocks().isEmpty()){
                checkHint = 0;
            }
            for (Block block : accessBlocks) {
                if (block.getCoordinate().getX() == coordinate.getX() && block.getCoordinate().getY() == coordinate.getY()) {
                    move(coordinate);
                    return;
                }
            }
            Piece piece = board[coordinate.getX()][coordinate.getY()];
            if (piece == null) {
                selectedPiece = null;
                if(isMyKingChecked()){
                    checkHint = 0;
                }
                return;
            }
            if (piece.equals(selectedPiece)) {
                selectedPiece = null;
                if(isMyKingChecked()){
                    checkHint = 0;
                }
                return;
            }
            if (turns == playerHandle && piece.owner == playerHandle) {
                selectedPiece = piece;
                if(isMyKingChecked() && getAccessBlocks().isEmpty()){
                    checkHint = 0;
                }
            }
        }

    }

    private boolean isMove = false;

    public boolean isMoved() {
        boolean res = isMove;
        isMove = false;
        return res;
    }


    public void move(Coordinate target) {
        prevStep.clear();
        prevStep.add(new Block(LIGHT_GREEN, target));
        prevStep.add(new Block(LIGHT_GREEN, selectedPiece.coordinate));

        // Pawn promotion
        if (selectedPiece.toChar() == 'p' && target.getY() < 7) {
            selectedPiece = new Queen(turns, selectedPiece.coordinate);
        }

        if (selectedPiece.toChar() == 'P' && target.getY() > 6) {
            selectedPiece = new Queen(turns, selectedPiece.coordinate);
        }

        // Castling
        if (selectedPiece instanceof King && selectedPiece.getCoordinate().getX() - target.getX() == 2) {
            // Move Rook
            Rook rook = (Rook) getPieceAt(new Coordinate(0, selectedPiece.coordinate.getY()));
            board[target.getX()][target.getY()] = selectedPiece;
            board[selectedPiece.getCoordinate().getX()][selectedPiece.getCoordinate().getY()] = null;
            selectedPiece.setCoordinate(target);
            selectedPiece = null;
            board[rook.getCoordinate().getX()][rook.getCoordinate().getY()] = null;
            board[target.getX() + 1][target.getY()] = rook;
            rook.setCoordinate(new Coordinate(target.getX() + 1,target.getY()));


        } else if (selectedPiece instanceof King && selectedPiece.getCoordinate().getX() - target.getX() == -2) {
            // Move Rook
            Rook rook = (Rook) getPieceAt(new Coordinate(BOARD_SIZE - 1, selectedPiece.coordinate.getY()));
            board[target.getX()][target.getY()] = selectedPiece;
            board[selectedPiece.getCoordinate().getX()][selectedPiece.getCoordinate().getY()] = null;
            selectedPiece.setCoordinate(target);
            selectedPiece = null;
            board[rook.getCoordinate().getX()][rook.getCoordinate().getY()] = null;
            board[target.getX() - 1][target.getY()] = rook;
            rook.setCoordinate(new Coordinate(target.getX() - 1,target.getY()));

        } else {
            board[target.getX()][target.getY()] = selectedPiece;
            board[selectedPiece.getCoordinate().getX()][selectedPiece.getCoordinate().getY()] = null;
            selectedPiece.setCoordinate(target);
            selectedPiece = null;
        }

        // afterMove
        nextPlayer();
        isMove = true;
    }

    public void AIMove() {
        Map<Piece, ArrayList<Block>> moveMap = new HashMap<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != null && board[i][j].owner == turns) {
                    selectedPiece = board[i][j];
                    ArrayList<Block> moveAbleBlocks = getAccessBlocks();
                    if (!moveAbleBlocks.isEmpty())
                        moveMap.put(board[i][j], moveAbleBlocks);
                }
            }
        }

        // Move
        Random random = new Random();
        if (!moveMap.isEmpty()) {
            // Select a random piece from the keySet
            List<Piece> pieces = new ArrayList<>(moveMap.keySet());
            Piece randomPiece = pieces.get(random.nextInt(pieces.size()));

            // Select a random position from the list of moveable positions of the selected piece
            List<Block> targets = moveMap.get(randomPiece);
            Block randomTarget = targets.get(random.nextInt(targets.size()));

            // Perform a move operation
            selectedPiece = randomPiece;
            move(randomTarget.getCoordinate());
        } else {
            if (isMyKingChecked()) {
                if (this.turns == Owner.BLACK) {
                    winner = Owner.WHITE;
                } else {
                    winner = Owner.BLACK;
                }
                gameStatus = GAME_STATUS.GAME_OVER;
            } else {
                gameStatus = GAME_STATUS.NO_WIN;
            }
        }
        isMove = true;


        // After Move check player can move
        Map<Piece, ArrayList<Block>> playerMoveMap = new HashMap<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != null && board[i][j].owner == turns) {
                    selectedPiece = board[i][j];
                    ArrayList<Block> moveAbleBlocks = getAccessBlocks();
                    if (!moveAbleBlocks.isEmpty())
                        playerMoveMap.put(board[i][j], moveAbleBlocks);
                }
            }
        }
        if(playerMoveMap.isEmpty()){
            if (isMyKingChecked()) {
                if (this.turns == Owner.BLACK) {
                    winner = Owner.WHITE;
                } else {
                    winner = Owner.BLACK;
                }
                gameStatus = GAME_STATUS.GAME_OVER;
            } else {
                gameStatus = GAME_STATUS.NO_WIN;
            }
        }
        selectedPiece = null;

    }

    private void nextPlayer() {
        if (this.turns == Owner.BLACK) {
            this.timeBlack += timeBlackIncrement;
            turns = Owner.WHITE;
        } else {
            this.timeWhite += timeWhiteIncrement;
            turns = Owner.BLACK;
        }
    }

    public ArrayList<Block> getBlocks() {
        ArrayList<Block> res = new ArrayList<>();
        res.addAll(prevStep);
        if (selectedPiece != null) {
            res.add(new Block(GREEN, selectedPiece.coordinate));
            res.addAll(getAccessBlocks());
        }
        if (isOtherKingChecked()) {
            res.add(new Block(RED, getOtherPlayerKing().coordinate));
        }
        if(isMyKingChecked()){
            res.add(new Block(RED, getCurrentPlayerKing().coordinate));
        }

        // Hint
        if(checkHint >= 0){
            if(checkHint % 60 > 30){
                res.add(new Block(BROWN,getCurrentPlayerKing().coordinate));
            }else {
                res.add(new Block(RED,getCurrentPlayerKing().coordinate));
            }
            checkHint ++ ;
            if(checkHint > 180){
                checkHint = -1;
            }
        }
        return res;
    }

    private King getOtherPlayerKing() {
        for (King king : kings) {
            if (king.owner != turns) {
                return king;
            }
        }
        throw new IllegalStateException();
    }

    private void processDirection(int x, int y, Function<Integer, Coordinate> direction, ArrayList<Block> res) {
        for (int i = 1; i < BOARD_SIZE; i++) {
            Coordinate newCoordinate = direction.apply(i);
            int i_x = newCoordinate.getX();
            int i_y = newCoordinate.getY();
            if (i_x >= 0 && i_x < BOARD_SIZE && i_y >= 0 && i_y < BOARD_SIZE) {
                if (isKingCheckedAfterMove(new Coordinate(i_x, i_y))) continue;
                if (!isPathClear(selectedPiece.getCoordinate(), new Coordinate(i_x, i_y))) {
                    continue;
                }
                if (board[i_x][i_y] == null) {
                    res.add(new Block(BLUE, new Coordinate(i_x, i_y)));
                } else {
                    if (board[i_x][i_y].owner != turns) {
                        res.add(new Block(LIGHT_RED, new Coordinate(i_x, i_y)));
                    }
                    break;
                }
            } else {
                break;
            }
        }
    }


    public ArrayList<Block> getAccessBlocks() {
        ArrayList<Block> res = new ArrayList<>();
        int x = selectedPiece.coordinate.getX();
        int y = selectedPiece.coordinate.getY();
        if (selectedPiece != null) {
            switch (selectedPiece.toChar()) {
                case 'p': {
                    // One step
                    if (y > 0) {
                        // One step
                        if (board[x][y - 1] == null) {
                            if (isKingCheckedAfterMove(new Coordinate(x, y - 1))) break;
                            res.add(new Block(BLUE, new Coordinate(x, y - 1)));
                        }
                        // 2 step
                        if (y == BOARD_SIZE - 2 && board[x][y - 1] == null && board[x][y - 2] == null) {
                            if (isKingCheckedAfterMove(new Coordinate(x, y - 2))) break;
                            res.add(new Block(BLUE, new Coordinate(x, y - 2)));
                        }
                        // eat step
                        if (x > 0 && board[x - 1][y - 1] != null && board[x - 1][y - 1].owner != turns) {
                            res.add(new Block(LIGHT_RED, new Coordinate(x - 1, y - 1)));
                        }
                        if (x < BOARD_SIZE - 1 && board[x + 1][y - 1] != null && board[x + 1][y - 1].owner != turns) {
                            res.add(new Block(LIGHT_RED, new Coordinate(x + 1, y - 1)));
                        }
                    }
                    break;
                }
                case 'P': {
                    // One step
                    if (y < BOARD_SIZE - 1) {
                        // One step
                        if (board[x][y + 1] == null) {
                            if (isKingCheckedAfterMove(new Coordinate(x, y + 1))) break;
                            res.add(new Block(BLUE, new Coordinate(x, y + 1)));
                        }
                        // 2 step
                        if (y == 1 && board[x][y + 1] == null && board[x][y + 2] == null) {
                            if (isKingCheckedAfterMove(new Coordinate(x, y + 2))) break;
                            res.add(new Block(BLUE, new Coordinate(x, y + 2)));
                        }
                        // eat step
                        if (x > 0 && board[x - 1][y + 1] != null && board[x - 1][y + 1].owner != turns) {
                            res.add(new Block(LIGHT_RED, new Coordinate(x - 1, y + 1)));
                        }
                        if (x < BOARD_SIZE - 1 && board[x + 1][y + 1] != null && board[x + 1][y + 1].owner != turns) {
                            res.add(new Block(LIGHT_RED, new Coordinate(x + 1, y + 1)));
                        }
                    }
                    break;
                }
                case 'R':
                case 'r': {
                    processDirection(x, y, i -> new Coordinate(x, y - i), res); // Up
                    processDirection(x, y, i -> new Coordinate(x, y + i), res); // Down
                    processDirection(x, y, i -> new Coordinate(x - i, y), res); // Left
                    processDirection(x, y, i -> new Coordinate(x + i, y), res); // Right
                    break;
                }
                case 'N':
                case 'n': {
                    int[][] dirs = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
                    for (int[] dir : dirs) {
                        int i_x = x + dir[0];
                        int i_y = y + dir[1];
                        if (i_x >= 0 && i_x < BOARD_SIZE && i_y >= 0 && i_y < BOARD_SIZE) {
                            if (isKingCheckedAfterMove(new Coordinate(i_x, i_y))) continue;
                            if (board[i_x][i_y] == null) {
                                res.add(new Block(BLUE, new Coordinate(i_x, i_y)));
                            } else {
                                if (board[i_x][i_y].owner != turns) {
                                    res.add(new Block(LIGHT_RED, new Coordinate(i_x, i_y)));
                                }
                            }
                        }
                    }
                    break;
                }
                case 'B':
                case 'b': {
                    processDirection(x, y, i -> new Coordinate(x - i, y - i), res); // Upper left
                    processDirection(x, y, i -> new Coordinate(x + i, y - i), res); // Upper right
                    processDirection(x, y, i -> new Coordinate(x - i, y + i), res); // Lower left
                    processDirection(x, y, i -> new Coordinate(x + i, y + i), res); // Lower right
                    break;
                }
                case 'k': // King
                case 'K': // King
                {
                    int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
                    for (int[] dir : dirs) {
                        int i_x = x + dir[0];
                        int i_y = y + dir[1];
                        if (i_x >= 0 && i_x < BOARD_SIZE && i_y >= 0 && i_y < BOARD_SIZE) {
                            if (isKingCheckedAfterMove(new Coordinate(i_x, i_y))) continue;
                            if (board[i_x][i_y] == null) {
                                res.add(new Block(BLUE, new Coordinate(i_x, i_y)));
                            } else if (board[i_x][i_y].owner != turns) {
                                res.add(new Block(LIGHT_RED, new Coordinate(i_x, i_y)));
                            }
                        }
                    }

                    // castling
                    if (!((King) selectedPiece).haveBeenMove) {
                        // find rook
                        Rook rook1 = (Rook) getPieceAt(new Coordinate(0, ((King) selectedPiece).getCoordinate().getY()));
                        Rook rook2 = (Rook) getPieceAt(new Coordinate(BOARD_SIZE - 1, ((King) selectedPiece).getCoordinate().getY()));
                        if (rook1 != null && !rook1.haveBeenMove && isPathClear(((King) selectedPiece).coordinate, rook1.coordinate)) {
                            res.add(new Block(BLUE, new Coordinate(selectedPiece.coordinate.getX() - 2, selectedPiece.coordinate.getY())));
                        }
                        if (rook2 != null && !rook2.haveBeenMove && isPathClear(((King) selectedPiece).coordinate, rook2.coordinate)) {
                            res.add(new Block(BLUE, new Coordinate(selectedPiece.coordinate.getX() + 2, selectedPiece.coordinate.getY())));
                        }
                    }
                    break;
                }
                case 'h': // Archbishop
                case 'H': // Archbishop
                {
                    // Like Knight
                    int[][] knightDirs = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
                    for (int[] dir : knightDirs) {
                        int i_x = x + dir[0];
                        int i_y = y + dir[1];
                        if (i_x >= 0 && i_x < BOARD_SIZE && i_y >= 0 && i_y < BOARD_SIZE) {
                            if (isKingCheckedAfterMove(new Coordinate(i_x, i_y))) continue;
                            if (board[i_x][i_y] == null) {
                                res.add(new Block(BLUE, new Coordinate(i_x, i_y)));
                            } else if (board[i_x][i_y].owner != turns) {
                                res.add(new Block(LIGHT_RED, new Coordinate(i_x, i_y)));
                            }
                        }
                    }

                    // Like Bishop
                    processDirection(x, y, i -> new Coordinate(x - i, y - i), res); // Upper left
                    processDirection(x, y, i -> new Coordinate(x + i, y - i), res); // Upper right
                    processDirection(x, y, i -> new Coordinate(x - i, y + i), res); // Lower left
                    processDirection(x, y, i -> new Coordinate(x + i, y + i), res); // Lower right

                    break;
                }
                case 'c': // Camel
                case 'C': // Camel
                {
                    int[][] camelDirs = {{-3, -1}, {-3, 1}, {-1, -3}, {-1, 3}, {1, -3}, {1, 3}, {3, -1}, {3, 1}};
                    for (int[] dir : camelDirs) {
                        int i_x = x + dir[0];
                        int i_y = y + dir[1];
                        if (i_x >= 0 && i_x < BOARD_SIZE && i_y >= 0 && i_y < BOARD_SIZE) {
                            if (isKingCheckedAfterMove(new Coordinate(i_x, i_y))) continue;
                            if (board[i_x][i_y] == null) {
                                res.add(new Block(BLUE, new Coordinate(i_x, i_y)));
                            } else if (board[i_x][i_y].owner != turns) {
                                res.add(new Block(LIGHT_RED, new Coordinate(i_x, i_y)));
                            }
                        }
                    }
                    break;
                }
                case 'g': // General/Guard
                case 'G': // General/Guard
                {
                    // Like Knight
                    int[][] knightDirs = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
                    for (int[] dir : knightDirs) {
                        int i_x = x + dir[0];
                        int i_y = y + dir[1];
                        if (i_x >= 0 && i_x < BOARD_SIZE && i_y >= 0 && i_y < BOARD_SIZE) {
                            if (isKingCheckedAfterMove(new Coordinate(i_x, i_y))) continue;
                            if (board[i_x][i_y] == null) {
                                res.add(new Block(BLUE, new Coordinate(i_x, i_y)));
                            } else if (board[i_x][i_y].owner != turns) {
                                res.add(new Block(LIGHT_RED, new Coordinate(i_x, i_y)));
                            }
                        }
                    }

                    // Like King
                    int[][] kingDirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
                    for (int[] dir : kingDirs) {
                        int i_x = x + dir[0];
                        int i_y = y + dir[1];
                        if (i_x >= 0 && i_x < BOARD_SIZE && i_y >= 0 && i_y < BOARD_SIZE) {
                            if (isKingCheckedAfterMove(new Coordinate(i_x, i_y))) continue;
                            if (board[i_x][i_y] == null) {
                                res.add(new Block(BLUE, new Coordinate(i_x, i_y)));
                            } else if (board[i_x][i_y].owner != turns) {
                                res.add(new Block(LIGHT_RED, new Coordinate(i_x, i_y)));
                            }
                        }
                    }
                    break;
                }

                case 'A': // Amazon
                case 'a': // Amazon
                {
                    // Like Knight
                    int[][] knightDirs = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
                    for (int[] dir : knightDirs) {
                        int i_x = x + dir[0];
                        int i_y = y + dir[1];
                        if (i_x >= 0 && i_x < BOARD_SIZE && i_y >= 0 && i_y < BOARD_SIZE) {
                            if (isKingCheckedAfterMove(new Coordinate(i_x, i_y))) continue;
                            if (board[i_x][i_y] == null) {
                                res.add(new Block(BLUE, new Coordinate(i_x, i_y)));
                            } else if (board[i_x][i_y].owner != turns) {
                                res.add(new Block(LIGHT_RED, new Coordinate(i_x, i_y)));
                            }
                        }
                    }

                    // Like Bishop
                    processDirection(x, y, i -> new Coordinate(x - i, y - i), res); // Upper left
                    processDirection(x, y, i -> new Coordinate(x + i, y - i), res); // Upper right
                    processDirection(x, y, i -> new Coordinate(x - i, y + i), res); // Lower left
                    processDirection(x, y, i -> new Coordinate(x + i, y + i), res); // Lower right

                    // Like Rook
                    processDirection(x, y, i -> new Coordinate(x, y - i), res); // Up
                    processDirection(x, y, i -> new Coordinate(x, y + i), res); // Down
                    processDirection(x, y, i -> new Coordinate(x - i, y), res); // Left
                    processDirection(x, y, i -> new Coordinate(x + i, y), res); // Right

                    break;
                }
                case 'e': // Chancellor
                case 'E': // Chancellor
                {
                    // Like Knight
                    int[][] knightDirs = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
                    for (int[] dir : knightDirs) {
                        int i_x = x + dir[0];
                        int i_y = y + dir[1];
                        if (i_x >= 0 && i_x < BOARD_SIZE && i_y >= 0 && i_y < BOARD_SIZE) {
                            if (isKingCheckedAfterMove(new Coordinate(i_x, i_y))) continue;
                            if (board[i_x][i_y] == null) {
                                res.add(new Block(BLUE, new Coordinate(i_x, i_y)));
                            } else if (board[i_x][i_y].owner != turns) {
                                res.add(new Block(LIGHT_RED, new Coordinate(i_x, i_y)));
                            }
                        }
                    }

                    // Like Rook
                    processDirection(x, y, i -> new Coordinate(x, y - i), res); // Up
                    processDirection(x, y, i -> new Coordinate(x, y + i), res); // Down
                    processDirection(x, y, i -> new Coordinate(x - i, y), res); // Left
                    processDirection(x, y, i -> new Coordinate(x + i, y), res); // Right

                    break;
                }
                case 'q': // Queen
                case 'Q': // Queen
                {
                    // Like Bishop
                    processDirection(x, y, i -> new Coordinate(x - i, y - i), res); // Upper left
                    processDirection(x, y, i -> new Coordinate(x + i, y - i), res); // Upper right
                    processDirection(x, y, i -> new Coordinate(x - i, y + i), res); // Lower left
                    processDirection(x, y, i -> new Coordinate(x + i, y + i), res); // Lower right

                    // Like Rook
                    processDirection(x, y, i -> new Coordinate(x, y - i), res); // Up
                    processDirection(x, y, i -> new Coordinate(x, y + i), res); // Down
                    processDirection(x, y, i -> new Coordinate(x - i, y), res); // Left
                    processDirection(x, y, i -> new Coordinate(x + i, y), res); // Right

                    break;
                }


            }
        }
        return res;
    }

    public Piece getPieceAt(Coordinate coordinate) {
        return board[coordinate.getX()][coordinate.getY()];
    }

    public boolean isPathClear(Coordinate start, Coordinate end) {

        int startX = start.getX();
        int startY = start.getY();
        int endX = end.getX();
        int endY = end.getY();

        int dx = Integer.compare(endX, startX);
        int dy = Integer.compare(endY, startY);

        // Start from the next square after 'start'
        startX += dx;
        startY += dy;

        while (startX != endX || startY != endY) {
            if (getPieceAt(new Coordinate(startX, startY)) != null) {
                // Found a piece in the path
                return false;
            }

            startX += dx;
            startY += dy;
        }

        // No pieces found in the path
        return true;
    }

    public boolean isKingCheckedAfterMove(Coordinate target) {
        // Create a copy of the board
        Board tempBoard = new Board(this);

        tempBoard.move(target);
        tempBoard.nextPlayer();
        // Check if the king is in check
        return tempBoard.isMyKingChecked();
    }

    public boolean isMyKingChecked() {
        King myKing = getCurrentPlayerKing();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Piece piece = board[i][j];
                if (piece == null) continue;
                if (piece.owner != this.turns) {
                    if (piece.canEat(this, myKing.coordinate)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private King getCurrentPlayerKing() {
        for (King king : this.kings) {
            if (king.owner == turns) {
                return king;
            }
        }
        throw new IllegalStateException();
    }

    public boolean isOtherKingChecked() {
        King otherKing = getOtherPlayerKing();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Piece piece = board[i][j];
                if (piece == null) continue;
                if (piece.owner != otherKing.owner) {
                    if (piece.canEat(this, otherKing.coordinate)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<Block> getContributeBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        King myKing = null;
        for (King king : kings) {
            if (king.getOwner() == turns) {
                myKing = king;
            }
        }

        if (myKing == null) {
            throw new IllegalStateException("The current player has no king.");
        }

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Coordinate coordinate = new Coordinate(i, j);
                Piece piece = board[i][j];

                if (piece != null && piece.getOwner() != turns) {
                    // Threatens the king directly
                    if (piece.canEat(this, myKing.getCoordinate())) {
                        blocks.add(new Block(LIGHT_RED, coordinate));
                    }

                    // Check all blocks around the king
                    for (Coordinate aroundKing : myKing.getSurroundingCoordinates()) {
                        // If this piece blocks the king's movement
                        if (piece.canEat(this, aroundKing)) {
                            blocks.add(new Block(LIGHT_RED, coordinate));
                        }
                    }
                }
            }
        }

        return blocks;
    }

}
