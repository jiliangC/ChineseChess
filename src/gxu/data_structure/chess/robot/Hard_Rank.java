package gxu.data_structure.chess.robot;

import gxu.data_structure.chess.XqChessBoard;
import gxu.data_structure.chess.core.Move;

public class Hard_Rank {
    private final int hard;

    public Hard_Rank(int hard) {
        this.hard = hard;
    }

    public Move search_walk(XqChessBoard chessBoard, boolean red) {
        Move move = null;
        switch (hard) {
            case 1 -> {
                MaxMinTree maxMinTree = new MaxMinTree(chessBoard);
                move = maxMinTree.rootSearch(3, red);
            }
            case 2 -> {
                Alpha_Beta alpha_beta = new Alpha_Beta(chessBoard);
                move = alpha_beta.rootSearch(4, -10000000, 10000000, red);
            }
            default -> {
            }
        }
        return move;
    }
}
