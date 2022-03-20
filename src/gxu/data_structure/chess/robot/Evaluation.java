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


    public int eva() {
        int B_Value = 0;
        int R_Value = 0;
        //评估黑色的棋子的棋力价值；
        int[] map = xqChessBoard.getUcpcSquares();
        for (int i = 0; i < 255; i++) {
            int state = map[i];
            if (isBlack(state)) {
                B_Value += values(state, i);
            } else {
                R_Value += values(state, i);
            }
        }
        return (R_Value - B_Value);
    }

    //计算每个棋子的价值
    private int values(int state, int position) {
        int v = 0;
        switch (state) {
            case blackJiang -> v = Jiang;
            case blackChe -> v = Che;
            case blackMa -> v = Ma(position);
            case blackPao -> v = Pao(position);
            case blackShi -> v = Shi;
            case blackXiang -> v = Xiang;
            case blackZu -> v = Bing(position);
        }
        return v;
    }

    // 兵
    private int Bing(int position) {
        int x = pointForX(position), y = pointForY(position);
        if (y < 5) return uncrossBing; //未过河兵
        else if (x == 4 && y == 8) return inNineBing; //窝心兵
        else if (y < 9) return crossBing; //过河兵
        else return underBing; //底线
    }

    //马
    private int Ma(int position) {
        int x = pointForX(position), y = pointForY(position);
        if ((x == 0 && y == 0) || (x == 0 && y == 9) || (x == 8 && y == 0) || (x == 8 && y == 9)) return cornerMa; //角落马
        else if (x == 0 || x == 8) return boundaryMa; //边马
        else if ((x == 4 && y == 1) || (x == 4 && y == 8)) return inNineMa; //窝心马
        else return Ma;
    }

    //炮
    private int Pao(int position) {
        int x = pointForX(position), y = pointForY(position);
        if (y == 9) return underPao;
        else if (x == 4) return centerPao;
        else return Pao;
    }
}
