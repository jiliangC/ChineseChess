package gxu.data_structure.chess.robot;

import gxu.data_structure.chess.Constants;
import gxu.data_structure.chess.XqChessBoard;

public class Evaluation implements Constants {
    private final XqChessBoard xqChessBoard;

    public Evaluation(XqChessBoard xq) {
        this.xqChessBoard = xq;
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

    private static int R_index(int x, int y) {
        return x * 10 + 9 - y;
    }

    private static int B_index(int x, int y) {
        return x * 10 + y;
    }


    public int eva() {
        //黑
        int B_Value = 0, B_Position = 0, B_sum;
        //红
        int R_Value = 0, R_Position = 0, R_sum;

        int[] map = xqChessBoard.getUcpcSquares();
        for (int i = 0; i < 255; i++) {
            int state = map[i];
            if (isBlack(state)) {
                B_Value += values(state); //黑子价值
                B_Position += position(state, i); //黑子位置价值
            } else if (isRed(state)) {
                R_Value += values(state); //红字价值
                R_Position += position(state, i); //红字位置价值
            }
        }
        R_sum = R_Value + R_Position * 3;
        B_sum = B_Value + B_Position * 3;
        return R_sum - B_sum;
        //return (R_Value - B_Value);
    }

    //计算每个棋子的价值
    private int values(int state) {
        int v = 0;
        switch (state) {
            case blackJiang, redJiang -> v = Jiang;
            case blackChe, redChe -> v = Che;
            case blackMa, redMa -> v = Ma;
            case blackPao, redPao -> v = Pao;
            case blackShi, redShi -> v = Shi;
            case blackXiang, redXiang -> v = Xiang;
            case blackZu, redZu -> v = Bing;
        }
        return v;
    }

    private int position(int state, int p) {
        int x = pointForX(p), y = pointForY(p);
        int index = isBlack(state) ? B_index(x, y) : R_index(x, y);
        switch (state) {
            case redJiang, blackJiang -> {
                return PositionValues[1][index];
            }
            case redShi, blackShi -> {
                return PositionValues[2][index];
            }
            case redXiang, blackXiang -> {
                return PositionValues[3][index];
            }
            case redMa, blackMa -> {
                return PositionValues[4][index];
            }
            case redChe, blackChe -> {
                return PositionValues[5][index];
            }
            case redPao, blackPao -> {
                return PositionValues[6][index];
            }
            case redZu, blackZu -> {
                return PositionValues[7][index];
            }
        }
        return 0;
    }
}
