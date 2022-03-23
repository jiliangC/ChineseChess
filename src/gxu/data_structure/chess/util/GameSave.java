package gxu.data_structure.chess.util;

import gxu.data_structure.chess.core.ChessBoard;


/**
 * 象棋残局
 */
public class GameSave {
    private ChessBoard chessBoard;
    private boolean red;
    private boolean playing;

    public GameSave(ChessBoard chessBoard, boolean red, boolean hasWin) {
        this.chessBoard = chessBoard;
        this.red = red;
        this.playing = hasWin;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
