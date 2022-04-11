package gxu.data_structure.chess.robot;

import gxu.data_structure.chess.Constants;
import gxu.data_structure.chess.XqChessBoard;
import gxu.data_structure.chess.XqWalkState;

public class Evaluation implements Constants {
    private final XqChessBoard xqChessBoard;
    private final XqWalkState xqWalkState;

    public Evaluation(XqWalkState xqWalkState) {
        this.xqWalkState = xqWalkState;
        this.xqChessBoard = xqWalkState.getChessBoard();
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
        int B_Value = 0, B_Position = 0, B_Flexible = 0, B_sum;
        //红
        int R_Value = 0, R_Position = 0, R_Flexible = 0, R_sum;

        int[] map = xqChessBoard.getUcpcSquares();
        for (int i = 0; i < 255; i++) {
            int state = map[i];
            if (isBlack(state)) {
                B_Value += values(state); //黑子价值
                B_Position += position(state, i); //黑子位置价值
                B_Flexible += flexible(state, i, false); //黑子灵活度
            } else if (isRed(state)) {
                R_Value += values(state); //红字价值
                R_Position += position(state, i); //红字位置价值
                R_Flexible += flexible(state, i, true); //红子灵活度
            }
        }
        R_sum = R_Value * K_Value + R_Position * K_Position + R_Flexible * K_Flexible;
        B_sum = B_Value * K_Value + B_Position * K_Position + B_Flexible * K_Flexible;
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

    //计算棋子位置的价值
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

    ///计算棋子的灵活价值
    private int flexible(int state, int p, boolean red) {
        int x = pointForX(p), y = pointForY(p);
        int k;
        switch (state) {
            case redChe, blackChe -> k = k_che;
            case redPao, blackPao -> k = k_pao;
            case redMa, blackMa -> k = k_ma;
            case redShi, blackShi -> k = k_shi;
            case redXiang, blackXiang -> k = k_xiang;
            case redZu, blackZu -> k = k_bing;
            default -> k = 0;
        }
        return xqWalkState.getAllMove(red, x, y).size() * k;
    }
}
