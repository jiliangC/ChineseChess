package gxu.data_structure.chess.robot;

import gxu.data_structure.chess.Constants;
import gxu.data_structure.chess.XqChessBoard;
import gxu.data_structure.chess.XqWalkState;
import gxu.data_structure.chess.core.Move;

import java.util.ArrayList;


public class Alpha_Beta {
    private final XqChessBoard ChessBoard = new XqChessBoard();
    private final XqWalkState WalkState;
    private final Evaluation evaluation;
    private final boolean RED = true, BLACK = false;

    public Alpha_Beta(XqChessBoard xqChessBoard) {
        //模拟专用棋盘
        ChessBoard.cloneSquares(xqChessBoard.getUcpcSquares());
        ChessBoard.setJiang(xqChessBoard.getJiang());
        ChessBoard.setShuai(xqChessBoard.getShuai());
        WalkState = new XqWalkState(ChessBoard);
        evaluation = new Evaluation(WalkState);
    }

    int AlphaBeta(int deep, int a, int b, boolean red) {
        if (deep == 0) {
            return -evaluation.eva();
        }

        ArrayList<Piece> pieceArrayList = new ArrayList<>();
        ArrayList<Move> moves = new ArrayList<>();
        //得到所有棋子的走法
        WalkState.setPieceArrayList(red, pieceArrayList);
        WalkState.setMoves(red, pieceArrayList, moves);

        //黑棋，极大值
        if (!red) {
            for (Move i : moves) {
                //走这一步
                int fromState = ChessBoard.getState(i.getFrom().getX(), i.getFrom().getY());
                int toState = ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), fromState);
                ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), Constants.EMPTY);
                a = Math.max(a, AlphaBeta(deep - 1, a, b, RED));
                //撤回这一步
                ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), fromState);
                ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), toState);
                if (a > b) break;
            }
            return a;
        }
        //红棋，极小值
        else {
            for (Move i : moves) {
                //走这一步
                int fromState = ChessBoard.getState(i.getFrom().getX(), i.getFrom().getY());
                int toState = ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), fromState);
                ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), Constants.EMPTY);
                b = Math.min(b, AlphaBeta(deep - 1, a, b, BLACK));
                //撤回这一步
                ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), fromState);
                ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), toState);
                if (a > b) break;
            }
            return b;
        }
    }

    //red = false
    public Move rootSearch(int deep, int a, int b, boolean red) {

        ArrayList<Piece> pieceArrayList = new ArrayList<>();
        ArrayList<Move> moves = new ArrayList<>();
        //得到所有棋子的走法
        WalkState.setPieceArrayList(red, pieceArrayList);
        WalkState.setMoves(red, pieceArrayList, moves);

        Move mm = null;

        for (Move i : moves) {
            //走这一步
            int fromState = ChessBoard.getState(i.getFrom().getX(), i.getFrom().getY());
            int toState = ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), fromState);
            ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), Constants.EMPTY);

            int temp_a = AlphaBeta(deep - 1, a, b, RED);
            if (temp_a > a) {
                a = temp_a;
                mm = i;
            }
            //撤回这一步
            ChessBoard.setState(i.getFrom().getX(), i.getFrom().getY(), fromState);
            ChessBoard.setState(i.getTo().getX(), i.getTo().getY(), toState);
        }
        return mm;

    }


}
