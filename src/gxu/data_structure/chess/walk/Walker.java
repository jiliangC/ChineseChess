package gxu.data_structure.chess.walk;

import gxu.data_structure.chess.core.Move;
import gxu.data_structure.chess.core.WalkState;

import java.util.List;

/**
 * 表示走法判断，每种棋都有不同的走法判断，因此将其提取成一个接口。
 */
public interface Walker {
    int getState(); //获取状态码，在注册的时候用

    void setState(WalkState walkState);

    //判断能否移动
    boolean canMove(boolean red, Move move);

    //生成一颗棋子的所有走法
    List<Move> getAllMove(boolean red, int x, int y);
}
