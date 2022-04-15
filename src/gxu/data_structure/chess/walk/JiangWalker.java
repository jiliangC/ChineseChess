package gxu.data_structure.chess.walk;

import gxu.data_structure.chess.core.Move;

import java.util.ArrayList;

/**
 * 将帅的走法
 */
public class JiangWalker extends AbstractWalker {
    @Override
    public ArrayList<Move> getAllMove(boolean red, int x, int y) {
        ArrayList<Move> moveList = new ArrayList<>();
        if (isInNine(x, y - 1, red) && !isSelf(x, y - 1, red))
            moveList.add(new Move(x, y, x, y - 1));
        if (isInNine(x, y + 1, red) && !isSelf(x, y + 1, red))
            moveList.add(new Move(x, y, x, y + 1));
        if (isInNine(x - 1, y, red) && !isSelf(x - 1, y, red))
            moveList.add(new Move(x, y, x - 1, y));
        if (isInNine(x + 1, y, red) && !isSelf(x + 1, y, red))
            moveList.add(new Move(x, y, x + 1, y));
        return moveList;
    }

    @Override
    protected boolean canMove(boolean red, int x, int y, int tx, int ty) {
        if (!isInNine(tx, ty, red)) {
            return false; //老将在家里待久了 想出门逛逛
        }
        if (isSelf(tx, ty, red))
            return false; //老帅也不能杀自己人
        if (x == tx) {
            return y - ty == 1 || y - ty == -1;
        } else if (y == ty) {
            return x - tx == 1 || x - tx == -1;
        }
        return false;
    }
}
