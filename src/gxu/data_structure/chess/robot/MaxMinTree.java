package gxu.data_structure.chess.robot;

import gxu.data_structure.chess.Constants;
import gxu.data_structure.chess.XqChessBoard;
import gxu.data_structure.chess.XqWalkState;
import gxu.data_structure.chess.core.Move;

import java.util.ArrayList;
import java.util.List;

public class MaxMinTree {
    private final int anInt = 1000000;
    private final XqChessBoard ChessBoard = new XqChessBoard();

    private final XqWalkState WalkState;
    private final Evaluation evaluation;

    public MaxMinTree(XqChessBoard xqChessBoard) {
        ChessBoard.cloneSquares(xqChessBoard.getUcpcSquares());
        ChessBoard.setJiang(xqChessBoard.getJiang());
        ChessBoard.setShuai(xqChessBoard.getShuai());
        WalkState = new XqWalkState(ChessBoard);
        evaluation = new Evaluation(ChessBoard);
        //System.out.println(Arrays.toString(ChessBoard.getUcpcSquares()));
    }

    //获取当前方可走的所有棋子
    private void setPieceArrayList(boolean red, ArrayList<Piece> pieceArrayList) {
        for (int i = 0; i < 255; i++) {
            int state = ChessBoard.getState(i);
            //红方
            if (red && XqWalkState.isRed(state)) {
                pieceArrayList.add(new Piece(state, i, WalkState));
            }
            //黑方
            if (!red && XqWalkState.isBlack(state)) {
                pieceArrayList.add(new Piece(state, i, WalkState));
            }
        }

    }

    private void setMoves(boolean red, ArrayList<Piece> pieceArrayList, ArrayList<Move> moves) {
        for (Piece piece : pieceArrayList) {
            int fromX = piece.ForX();
            int fromY = piece.ForY();
            List<Move> list = piece.getWalker().getAllMove(red, fromX, fromY);
            moves.addAll(list);
        }
    }

    public int Max_min_tree(int deep, boolean red) {
        if (deep <= 0) {
            return evaluation.eva();
        }

        //问题所在
        ArrayList<Piece> pieceArrayList = new ArrayList<>();
        ArrayList<Move> moves = new ArrayList<>();

        //得到所有棋子的走法
        setPieceArrayList(red, pieceArrayList);
        setMoves(red, pieceArrayList, moves);


        int eva = -anInt;
        // 获取所有的走法
        for (Move i : moves) {
            //走这一步
            int fromState = ChessBoard.getState(i.getFrom().getX(), i.getFrom().getY());
            int toState = ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), fromState);
            ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), Constants.EMPTY);

            eva = Math.max(eva, -Max_min_tree(deep - 1, !red));

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

        //得到所有棋子的走法
        setPieceArrayList(red, pieceArrayList);
        setMoves(red, pieceArrayList, moves);

        for (Move i : moves) {
            //走这一步
            int fromState = ChessBoard.getState(i.getFrom().getX(), i.getFrom().getY());
            int toState = ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), fromState);
            ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), Constants.EMPTY);

            int e = -Max_min_tree(deep - 1, !red);
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


    //示例
    public int e() {
        return evaluation.eva();
    }

}
