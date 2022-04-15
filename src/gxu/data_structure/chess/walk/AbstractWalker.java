package gxu.data_structure.chess.walk;

import gxu.data_structure.chess.Constants;
import gxu.data_structure.chess.XqWalkState;
import gxu.data_structure.chess.core.Move;
import gxu.data_structure.chess.core.Point;


/**
 * 抽象的Walker，提供了一些非常方便的方法
 */
public abstract class AbstractWalker implements Walker {

    protected XqWalkState walkState;

    protected int state;

    //index转（x,y）
    public static Point pointFor(int index) {
        return new Point((index - 51) % 16, (index - 51) / 16);
    }

    public static int pointForX(int index) {
        return (index - 51) % 16;
    }

    public static int pointForY(int index) {
        return (index - 51) / 16;
    }

    public static boolean isRed(int state) {
        return (state & 8) != 0;
    }

    public static boolean isBlack(int state) {
        return (state & 16) != 0;
    }

    @Override
    public int getState() {
        return state;
    }


    public final void setState(XqWalkState walkState) {
        this.walkState = walkState;
    }

    //判断是否在棋盘内 （x,y）
    public boolean inBoard(int x, int y) {
        return walkState.getChessBoard().inBoard(x, y);
    }

    //判断是否在棋盘内 index
    public boolean inBoard(int index) {
        return walkState.getChessBoard().inBoard(index);
    }

    //获取（x,y）上的棋子状态
    public final int getState(int x, int y) {
        return walkState.getChessBoard().getState(x, y);
    }

    //判断棋盘（x,y）上是否为空
    public final boolean isEmpty(int x, int y) {
        return getState(x, y) == 0;
    }

    //设置棋盘（x,y）上棋子的状态
    public final int setState(int x, int y, int s) {
        return walkState.getChessBoard().setState(x, y, s);
    }

    //将（x,y）转为 index
    public final int indexFor(int x, int y) {
        if (x < 0 || x >= walkState.getChessBoard().getMaxX() || y < 0 || y >= walkState.getChessBoard().getMaxY()) {
            throw new IndexOutOfBoundsException("x:" + x + ",y:" + y);
        }
        return 16 * (y + 3) + x + 3;
    }

    //获取棋子状态 index
    public final int getState(int index) {
        return walkState.getUcpcSquares()[index];
    }

    //是否已经过河
    public boolean isCrossRiver(int x, int y, boolean red) {
        if (red) {
            return y < Constants.riverYR;
        } else {
            return y > Constants.riverYb;
        }
    }

    //判断是否为自己的棋子 （x,y）
    public final boolean isSelf(int x, int y, boolean red) {
        int state = getState(x, y);
        return state != Constants.EMPTY && (red ? isRed(state) : isBlack(state));
    }

    //判断是否为自己的棋子 （index）
    public final boolean isSelf(int x, boolean red) {
        int state = getState(x);
        return state != Constants.EMPTY && (red ? isRed(state) : isBlack(state));
    }

    //判断是否为敌方棋子 （x,y）
    public final boolean isEnemy(int x, int y, boolean red) {
        int state = getState(x, y);
        return state != Constants.EMPTY && (red ? isBlack(state) : isRed(state));
    }

    //判断是否为敌方棋子 (index)
    public final boolean isEnemy(int x, boolean red) {
        int state = getState(x);
        return state != Constants.EMPTY && (red ? isBlack(state) : isRed(state));
    }

    //是否在自己的九宫格内.
    public final boolean isInNine(int x, int y, boolean red) {
        if (red) {
            return inBoard(x, y) && x >= 3 && x <= 5 && y >= 7;
        } else {
            return inBoard(x, y) && x >= 3 && x <= 5 && y <= 2;
        }
    }

    @Override
    public final boolean canMove(boolean red, Move move) {
        int x = move.getFrom().getX();
        int y = move.getFrom().getY();
        int tx = move.getTo().getX();
        int ty = move.getTo().getY();
        return canMove(red, x, y, tx, ty);
    }

    protected abstract boolean canMove(boolean red, int x, int y, int tx, int ty);

}
