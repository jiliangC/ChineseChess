package gxu.data_structure.chess;

import gxu.data_structure.chess.core.ChessBoard;
import gxu.data_structure.chess.core.Point;

import java.util.Arrays;


public class XqChessBoard implements ChessBoard, Constants {
    //我们预置一个常量数组 ccInBoard[256]，表示哪些格子在棋盘外(填0)，哪些格子在棋盘内(填1)
    //这样，要判断是否在棋子内就非常简单了，只需要ccInBoard[sq] != 0。
    private static final int[] ccInBoard = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };
    /**
     * 所有在棋盘上的棋子都放在数组 ucpcSquares[256] 中
     * 数组指标代表格子的编号，匈牙利标记 uc 表示每个元素占用一个字节，pc 表示棋子标识。棋子标识的含义如下：
     * A. 0表示空格(没有棋子)；
     * B. 8~14依次表示红方的帅、仕、相、马、车、炮和兵；
     * C. 16~22依次表示黑方的将、士、象、马、车、炮和卒。
     * 这样做的好处是判断棋子的颜色非常简单——(pc & 8) != 0 表示红方的棋子，(pc & 16) != 0 表示黑方的棋子。
     */
    private final int[] ucpcSquares = new int[256];
    private Point jiang;
    private Point shuai;


    public void cloneSquares(int[] ucp) {
        System.arraycopy(ucp, 0, ucpcSquares, 0, 256);
    }

    public void setJiang(Point jiang) {
        this.jiang = jiang;
    }

    public void setShuai(Point shuai) {
        this.shuai = shuai;
    }

    @Override
    public int getMaxX() {
        return 9;
    }

    @Override
    public int getMaxY() {
        return 10;
    }

    @Override
    public int getState(int x, int y) {
        return ucpcSquares[indexFor(x, y)];
    }

    public int getState(int index) {
        return ucpcSquares[index];
    }

    @Override
    public int setState(int x, int y, int state) {
        int index = indexFor(x, y);
        int val = ucpcSquares[index];
        ucpcSquares[index] = state;
        if (state == redJiang) {
            shuai.setX(x);
            shuai.setY(y);
        }
        if (state == blackJiang) {
            jiang.setX(x);
            jiang.setY(y);
        }
        //返回原来（x,y）上的棋子
        return val;
    }

    @Override
    public void clear() {
        Arrays.fill(ucpcSquares, EMPTY);
    }

    public void setDefault() {
        ucpcSquares[0x33] = ucpcSquares[0x3b] = blackChe;
        ucpcSquares[0x34] = ucpcSquares[0x3a] = blackMa;
        ucpcSquares[0x35] = ucpcSquares[0x39] = blackXiang;
        ucpcSquares[0x36] = ucpcSquares[0x38] = blackShi;
        ucpcSquares[0x37] = blackJiang;
        ucpcSquares[0x54] = ucpcSquares[0x5a] = blackPao;
        ucpcSquares[0x63] = ucpcSquares[0x65] = ucpcSquares[0x67] = ucpcSquares[0x69] = ucpcSquares[0x6b] = blackZu;

        ucpcSquares[0xc3] = ucpcSquares[0xcb] = redChe;
        ucpcSquares[0xc4] = ucpcSquares[0xca] = redMa;
        ucpcSquares[0xc5] = ucpcSquares[0xc9] = redXiang;
        ucpcSquares[0xc6] = ucpcSquares[0xc8] = redShi;
        ucpcSquares[0xc7] = redJiang;
        ucpcSquares[0xa4] = ucpcSquares[0xaa] = redPao;
        ucpcSquares[0x93] = ucpcSquares[0x95] = ucpcSquares[0x97] = ucpcSquares[0x99] = ucpcSquares[0x9b] = redZu;

        shuai = new Point(4, 9);
        jiang = new Point(4, 0);
    }

    public boolean inBoard(int index) {
        return ccInBoard[index] != 0;
    }

    @Override
    public boolean inBoard(int x, int y) {
        return x >= 0 && x < getMaxX() && y >= 0 && y < getMaxY();
    }

    @Override
    public Point getJiang() {
        return jiang;
    }

    @Override
    public Point getShuai() {
        return shuai;
    }


    //二维坐标转一维坐标
    protected int indexFor(int x, int y) {
        if (x < 0 || x >= getMaxX() || y < 0 || y >= getMaxY()) {
            throw new IndexOutOfBoundsException("x:" + x + ",y:" + y);
        }
        return 16 * (y + 3) + x + 3;
    }

    //返回棋盘数组
    public int[] getUcpcSquares() {
        return ucpcSquares;
    }

}
