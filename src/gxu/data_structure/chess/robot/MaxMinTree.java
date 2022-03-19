package gxu.data_structure.chess.robot;

import gxu.data_structure.chess.XqChessBoard;

public class MaxMinTree {
    private final Evaluation evaluation;
    private final XqChessBoard xqChessBoard;
    private final int anInt = 1000000;

    public MaxMinTree(XqChessBoard xqChessBoard) {
        evaluation = new Evaluation(xqChessBoard);
        this.xqChessBoard = xqChessBoard;
    }

    public int Max_min_tree(int deep) {
        if (deep <= 0) {
            return evaluation.eva();
        }
        int eva = -anInt;
        // 获取所有的走法


        return eva;
    }


    //
    public int e() {
        return evaluation.eva();
    }

}
