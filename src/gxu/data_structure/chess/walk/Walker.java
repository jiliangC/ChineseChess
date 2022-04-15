package gxu.data_structure.chess.walk;

import gxu.data_structure.chess.XqWalkState;
import gxu.data_structure.chess.core.Move;

import java.util.ArrayList;

/**
 * 表示走法判断，每种棋都有不同的走法判断，因此将其提取成一个接口。
 */
public interface Walker {
    int getState(); //获取状态码，在注册的时候用

    void setState(XqWalkState walkState);

    //判断能否移动
    boolean canMove(boolean red, Move move);

    //生成一颗棋子的所有走法
    ArrayList<Move> getAllMove(boolean red, int x, int y);
}
