package gxu.data_structure.chess.robot;

import gxu.data_structure.chess.Constants;
import gxu.data_structure.chess.XqChessBoard;
import gxu.data_structure.chess.XqWalkState;
import gxu.data_structure.chess.core.Move;

import java.util.ArrayList;

public class MaxMinTree implements Constants {
    private final int anInt = 1000000;
    private final XqChessBoard ChessBoard = new XqChessBoard();

    private final XqWalkState WalkState;
    private final Evaluation evaluation;

    public MaxMinTree(XqChessBoard xqChessBoard) {
        //模拟专用棋盘
        ChessBoard.cloneSquares(xqChessBoard.getUcpcSquares());
        ChessBoard.setJiang(xqChessBoard.getJiang());
        ChessBoard.setShuai(xqChessBoard.getShuai());
        WalkState = new XqWalkState(ChessBoard);
        evaluation = new Evaluation(WalkState);
    }

    public int Max_min_tree(int deep, boolean red, int state) {
        if (deep <= 0 || state == redJiang || state == blackJiang) {
            return evaluation.eva(1);
        }

        ArrayList<Piece> pieceArrayList = new ArrayList<>();
        ArrayList<Move> moves = new ArrayList<>();

        //得到所有棋子的走法
        WalkState.setPieceArrayList(red, pieceArrayList);
        WalkState.setMoves(red, pieceArrayList, moves);


        int eva = -anInt;
        // 获取所有的走法
        for (Move i : moves) {
            //走这一步
            int fromState = ChessBoard.getState(i.getFrom().getX(), i.getFrom().getY());
            int toState = ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), fromState);
            ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), Constants.EMPTY);

            eva = Math.max(eva, -Max_min_tree(deep - 1, !red, toState));

            //撤回这一步
            ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), fromState);
            ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), toState);
        }

        return eva;
    }

    public Move rootSearch(int deep, boolean red) {
        int eva = -anInt;
        //问题所在
        ArrayList<Piece> pieceArrayList = new ArrayList<>();
        ArrayList<Move> moves = new ArrayList<>();
        Move mm = null;

        WalkState.setPieceArrayList(red, pieceArrayList);
        WalkState.setMoves(red, pieceArrayList, moves);

        for (Move i : moves) {
            //走这一步
            int fromState = ChessBoard.getState(i.getFrom().getX(), i.getFrom().getY());
            int toState = ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), fromState);
            ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), Constants.EMPTY);

            int selfX = ChessBoard.getJiang().getX();
            int selfY = ChessBoard.getJiang().getY();
            //走后被将军了
            if (WalkState.isJiangJun(false, selfX, selfY)) {
                //撤回这一步
                ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), fromState);
                ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), toState);
                continue;
            }

            int e = -Max_min_tree(deep - 1, !red, toState);
            if (eva < e) {
                mm = i;
                eva = e;
            }

            //撤回这一步
            ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), fromState);
            ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), toState);
        }
        System.out.println();
        return mm;
    }

}
