package gxu.data_structure.chess.walk;

import gxu.data_structure.chess.Constants;
import gxu.data_structure.chess.core.Move;

import java.util.ArrayList;

/**
 * 红方兵的走法
 */
public class RedZuWalker extends AbstractWalker {

    @Override
    public int getState() {
        return Constants.redZu;
    }

    @Override
    public ArrayList<Move> getAllMove(boolean red, int x, int y) {
        ArrayList<Move> moveList = new ArrayList<>();
        if (isCrossRiver(x, y, red)) {
            if (y > 0 && !isSelf(x, y - 1, red)) moveList.add(new Move(x, y, x, y - 1));
            if (x > 0 && !isSelf(x - 1, y, red)) moveList.add(new Move(x, y, x - 1, y));
            if (x < walkState.getChessBoard().getMaxX() - 1 && !isSelf(x + 1, y, red))
                moveList.add(new Move(x, y, x + 1, y));
        } else if (y > 0) {
            if (!isSelf(x, y - 1, red))
                moveList.add(new Move(x, y, x, y - 1));
        }
        return moveList;
    }

    @Override
    protected boolean canMove(boolean red, int x, int y, int tx, int ty) {
        //因为兵最多只有三种走法，所以可以每走一步就生成全部走法.然后判断是否存在.
        return getAllMove(red, x, y).contains(new Move(x, y, tx, ty));
    }
}
