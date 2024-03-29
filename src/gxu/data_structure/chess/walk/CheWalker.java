package gxu.data_structure.chess.walk;


import gxu.data_structure.chess.Constants;
import gxu.data_structure.chess.core.Move;

import java.util.ArrayList;

/**
 * 车的走法
 */
public class CheWalker extends AbstractWalker implements Constants {
    @Override
    public ArrayList<Move> getAllMove(boolean red, int x, int y) {
        ArrayList<Move> moves = new ArrayList<>();
        //从4个方向所有情况判断一遍.
        for (int i = x - 1; inBoard(i, y); i--) {
            int state = getState(i, y);
            if (state == EMPTY) {
                moves.add(new Move(x, y, i, y));
            } else if (isSelf(i, y, red)) {
                break;
            } else {
                moves.add(new Move(x, y, i, y));
                break;
            }
        }
        for (int i = x + 1; inBoard(i, y); i++) {
            int state = getState(i, y);
            if (state == EMPTY) {
                moves.add(new Move(x, y, i, y));
            } else if (isSelf(i, y, red)) {
                break;
            } else {
                moves.add(new Move(x, y, i, y));
                break;
            }
        }
        for (int i = y - 1; inBoard(x, i); i--) {
            int state = getState(x, i);
            if (state == EMPTY) {
                moves.add(new Move(x, y, x, i));
            } else if (isSelf(x, i, red)) {
                break;
            } else {
                moves.add(new Move(x, y, x, i));
                break;
            }
        }
        for (int i = y + 1; inBoard(x, i); i++) {
            int state = getState(x, i);
            if (state == EMPTY) {
                moves.add(new Move(x, y, x, i));
            } else if (isSelf(x, i, red)) {
                break;
            } else {
                moves.add(new Move(x, y, x, i));
                break;
            }
        }
        return moves;
    }

    @Override
    protected boolean canMove(boolean red, int x, int y, int tx, int ty) {
        if (isSelf(tx, ty, red) || (x == tx && y == ty)) return false;
        if (x == tx) {
            int p = Math.min(y, ty);
            int q = Math.max(y, ty);
            for (int i = p + 1; i < q; i++) {
                int state = getState(x, i);
                if (state != EMPTY) {
                    return false;
                }
            }
            return true;
        }
        if (y == ty) {
            int p = Math.min(x, tx);
            int q = Math.max(x, tx);
            for (int i = p + 1; i < q; i++) {
                int state = getState(i, y);
                if (state != EMPTY) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
