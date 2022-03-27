package gxu.data_structure.chess;

import gxu.data_structure.chess.core.Move;
import gxu.data_structure.chess.core.WalkState;
import gxu.data_structure.chess.core.WinEnum;
import gxu.data_structure.chess.robot.Piece;
import gxu.data_structure.chess.walk.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XqWalkState implements WalkState, Constants {
    //存棋子状态和对象
    private final Map<Integer, Walker> walkerMap = new HashMap<>();
    //象棋棋盘
    private XqChessBoard chessBoard;

    //将军——棋子
    private final Walker jjMaWalker = new JiangJunMaWalker(this);
    private final Walker jjRedZuWalker = new JiangJunRedZuWalker(this);
    private final Walker jjBlackZuWalker = new JiangJunBlackZuWalker(this);


    public XqWalkState(XqChessBoard chessBoard) {
        this.chessBoard = chessBoard;

        registerWalker(new MaWalker(), redMa, blackMa);
        registerWalker(new XiangWalker(), redXiang, blackXiang);
        registerWalker(new ShiWalker(), redShi, blackShi);
        registerWalker(new CheWalker(), redChe, blackChe);
        registerWalker(new RedZuWalker());
        registerWalker(new BlackZuWalker());
        registerWalker(new JiangWalker(), redJiang, blackJiang);
        registerWalker(new PaoWalker(), redPao, blackPao);
    }

    //判断棋子的颜色非常简单——(state & 8) != 0 表示红方的棋子，(state & 16) != 0 表示黑方的棋子。
    public static boolean isRed(int state) {
        return state != EMPTY && (state & 8) != 0;
    }

    public static boolean isBlack(int state) {
        return state != EMPTY && (state & 16) != 0;
    }

    public static boolean isSelfState(int state, boolean red) {
        return state != EMPTY && (red ? isRed(state) : isBlack(state));
    }

    @Override
    public XqChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(XqChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    @Override
    public boolean canMove(boolean red, Move move) {
        int x = move.getFrom().getX();
        int y = move.getFrom().getY();
        int px = move.getTo().getX();
        int py = move.getTo().getY();

        //坐标在棋盘外面
        if (!chessBoard.inBoard(x, y) || !chessBoard.inBoard(px, py)) {
            return false;
        }
        if (move.getFrom().equals(move.getTo())) {
            return false; //没走
        }
        int state = chessBoard.getState(x, y);
        //当前位置的棋子必须是己方棋子才能走
        if (state == EMPTY || (red ? isBlack(state) : isRed(state))) {
            return false;
        }
        //目标位置必须是空白或者对方的棋子才能走
        int targetState = chessBoard.getState(px, py);
        if (targetState != EMPTY && (red ? isRed(targetState) : isBlack(targetState))) {
            return false;
        }
        //找到对应的walker
        Walker walker = walkerMap.get(state);
        return walker.canMove(red, move);
    }

    public int[] getUcpcSquares() {
        return chessBoard.getUcpcSquares();
    }

    @Override
    public boolean canSelect(boolean red, int x, int y) {
        //只能选中自己的棋子
        if (!chessBoard.inBoard(x, y)) { //在边界外的当然不能选中了
            return false;
        }
        int state = chessBoard.getState(x, y);
        if (state == EMPTY) {//空白的自然不能选中
            return false;
        }
        //只能选中自己的棋子
        if (red) {
            return isRed(state);
        } else {
            return isBlack(state);
        }
    }

    @Override
    public List<Move> getAllMove(boolean red, int x, int y) {
        //不坐标在棋盘上
        if (!chessBoard.inBoard(x, y)) {
            return new ArrayList<>();
        }

        // private final Map<Integer, Walker> walkerMap = new HashMap<>();
        int state = chessBoard.getState(x, y);
        Walker walker = walkerMap.get(state);
        List<Move> move = walker.getAllMove(red, x, y);
        List<Move> moveList = new ArrayList<>();
        for (Move m : move) {
            //可走格子上的棋子
            int s = chessBoard.getState(m.getTo().getX(), m.getTo().getY());
            //System.out.println(s);
            if (chessBoard.inBoard(m.getFrom().getX(), m.getFrom().getY()) && chessBoard.inBoard(m.getTo().getX(), m.getTo().getY()) && !isSelfState(s, red)) {
                moveList.add(m);
            }
        }
        return moveList;
    }

    //将棋子对象添加到哈希表中
    public void registerWalker(Walker walker) {
        walkerMap.put(walker.getState(), walker);
        //将本对象添加到哈希表中
        walker.setState(this);
    }

    //将棋子对象添加到哈希表中
    public void registerWalker(Walker walker, int... states) {
        for (int state : states) {
            walkerMap.put(state, walker);
        }
        walker.setState(this);
    }

    //red方走了一步后，判断是否将军的情况
    @Override
    public WinEnum hasWin(boolean red) {
        int selfX = red ? chessBoard.getShuai().getX() : chessBoard.getJiang().getX(); //自己这边的将/帅坐标
        int selfY = red ? chessBoard.getShuai().getY() : chessBoard.getJiang().getY();
        int enemyX = red ? chessBoard.getJiang().getX() : chessBoard.getShuai().getX(); //对方的将/帅坐标
        int enemyY = red ? chessBoard.getJiang().getY() : chessBoard.getShuai().getY();
        if (isJiangJun(red, selfX, selfY)) { //先要判断自己是否被将军.
            //走了一步，自己被将军了，那不就输了?
            return red ? WinEnum.RED : WinEnum.BLACK;
        }
        if (isJiangJun(!red, enemyX, enemyY)) { //判断对方是否被将军
            //对方被将军的情况下，需要判断对方是否被将死.为对方生成所有走法，如果每一步都将军，说明将死.
            boolean die = true; //是否将死

            //moves 是对方所有棋子可走的所有位置
            ArrayList<Piece> pieceArrayList = new ArrayList<>();
            ArrayList<Move> moves = new ArrayList<>();
            setPieceArrayList(!red, pieceArrayList);
            setMoves(!red, pieceArrayList, moves);
            for (Move move : moves) {
                //走这样一步
                int fromState = chessBoard.getState(move.getFrom().getX(), move.getFrom().getY());
                int toState = chessBoard.setState(move.getTo().getX(), move.getTo().getY(), fromState);
                chessBoard.setState(move.getFrom().getX(), move.getFrom().getY(), EMPTY);
                if (!isJiangJun(!red, enemyX, enemyY)) { //判断是否将军，如果没有将军 说明没有被将死
                    die = false;
                    System.out.println("解除将军");
                }
                //记得撤回刚才走的这一步
                chessBoard.setState(move.getFrom().getX(), move.getFrom().getY(), fromState);
                chessBoard.setState(move.getTo().getX(), move.getTo().getY(), toState);
                if (!die) break;
            }

            if (die) {
                return red ? WinEnum.BLACK_KILL : WinEnum.RED_KILL;
            } else {
                return red ? WinEnum.BLACK : WinEnum.RED;
            }
        }
        //如果所有人只剩下象士帅，则和棋
        ArrayList<Piece> pieceArrayList = new ArrayList<>();
        setPieceArrayList(!red, pieceArrayList);
        boolean draw = true;

        for (Piece i : pieceArrayList) {
            int state = i.getState();
            if (state != EMPTY && state != redXiang && state != blackXiang && state != redShi && state != blackShi && state != blackJiang && state != redJiang) {
                draw = false;
                break;
            }
        }
        if (draw) {
            return WinEnum.DRAW;
        }
        return WinEnum.NO;
    }

    /*判断是否将军x,y为将或者帅的坐标.
     * (1) 假设帅(将)是车，判断它是否能吃到对方的车和将(帅)(中国象棋中有将帅不能对脸的规则)；
     * (2) 假设帅(将)是炮，判断它是否能吃到对方的炮；
     * (3) 假设帅(将)是马，判断它是否能吃到对方的马，需要注意的是，帅(将)此时使用的马腿不同
     * (4) 假设帅(将)是过河的兵(卒)，判断它是否能吃到对方的卒(兵)。
     */
    private boolean isJiangJun(boolean red, int x, int y) {
        //(x,y)光头的位置
        Walker cheWalker = walkerMap.get(redChe); //假设帅(将)是车
        List<Move> cheMoves = cheWalker.getAllMove(red, x, y);
        for (Move cheMove : cheMoves) {
            int state = chessBoard.getState(cheMove.getTo().getX(), cheMove.getTo().getY());
            if (state == (red ? blackChe : redChe) || state == (red ? blackJiang : redJiang)) {
                return true;
            }
        }
        Walker paoWalker = walkerMap.get(redPao); //假设帅(将)是炮
        List<Move> paoMoves = paoWalker.getAllMove(red, x, y);
        for (Move paoMove : paoMoves) {
            int state = chessBoard.getState(paoMove.getTo().getX(), paoMove.getTo().getY());
            if (state == (red ? blackPao : redPao)) {
                return true;
            }
        }

        List<Move> maMoves = jjMaWalker.getAllMove(red, x, y); //假设帅(将)是马
        for (Move maMove : maMoves) {
            int state = chessBoard.getState(maMove.getTo().getX(), maMove.getTo().getY());
            if (state == (red ? blackMa : redMa)) {
                return true;
            }
        }

        //假设帅(将)是过河的兵(卒)
        List<Move> zuMoves = (red ? jjRedZuWalker : jjBlackZuWalker).getAllMove(red, x, y);
        for (Move zuMove : zuMoves) {
            int state = chessBoard.getState(zuMove.getTo().getX(), zuMove.getTo().getY());
            if (state == (red ? blackZu : redZu)) {
                return true;
            }
        }
        return false;
    }

    //与马走法对立的走法，也就是判断马能否吃到将/帅
    private static class JiangJunMaWalker extends SlashWalker {
        JiangJunMaWalker(WalkState walkState) {
            super(
                    new int[][]{{-33, -31}, {-18, 14}, {-14, 18}, {31, 33}},
                    new int[]{-17, -15, 15, 17} //只是马腿的偏移不同
            );
            setState(walkState);
        }
    }

    private static class JiangJunRedZuWalker extends RedZuWalker {

        JiangJunRedZuWalker(WalkState walkState) {
            setState(walkState);
        }

        @Override
        public boolean isCrossRiver(int x, int y, boolean red) {
            return true;
        }
    }

    private static class JiangJunBlackZuWalker extends BlackZuWalker {
        JiangJunBlackZuWalker(WalkState walkState) {
            setState(walkState);
        }

        @Override
        public boolean isCrossRiver(int x, int y, boolean red) {
            return true;
        }
    }

    //获取当前方可走的所有棋子
    public void setPieceArrayList(boolean red, ArrayList<Piece> pieceArrayList) {

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                int i = 16 * (y + 3) + x + 3;
                int state = chessBoard.getState(i);
                //红方
                if (red && XqWalkState.isRed(state)) {
                    pieceArrayList.add(new Piece(state, i, this));
                }
                //黑方
                if (!red && XqWalkState.isBlack(state)) {
                    pieceArrayList.add(new Piece(state, i, this));
                }
            }
        }

//        for (int i = 0; i < 255; i++) {
//            int state = chessBoard.getState(i);
//            //红方
//            if (red && XqWalkState.isRed(state)) {
//                pieceArrayList.add(new Piece(state, i, this));
//            }
//            //黑方
//            if (!red && XqWalkState.isBlack(state)) {
//                pieceArrayList.add(new Piece(state, i, this));
//            }
//        }
    }

    //获取点的所有走法
    public void setMoves(boolean red, ArrayList<Piece> pieceArrayList, ArrayList<Move> moves) {
        for (Piece piece : pieceArrayList) {
            int fromX = piece.ForX();
            int fromY = piece.ForY();
            List<Move> list = piece.getWalker().getAllMove(red, fromX, fromY);
            moves.addAll(list);
        }
    }


}
