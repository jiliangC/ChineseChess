package gxu.data_structure.chess.walk;

import gxu.data_structure.chess.Constants;
import gxu.data_structure.chess.core.Move;

import java.util.ArrayList;


public class BlackZuWalker extends AbstractWalker {

    @Override
    public int getState() {
        return Constants.blackZu;
    }

    @Override
    public ArrayList<Move> getAllMove(boolean red, int x, int y) {
        ArrayList<Move> moveList = new ArrayList<>();
        if (isCrossRiver(x, y, red)) {
            if (x > 0 && !isSelf(x - 1, y, red)) moveList.add(new Move(x, y, x - 1, y));
            if (x < walkState.getChessBoard().getMaxX() - 1 && !isSelf(x + 1, y, red))
                moveList.add(new Move(x, y, x + 1, y));
            if (y < walkState.getChessBoard().getMaxY() - 1 && !isSelf(x, y + 1, red))
                moveList.add(new Move(x, y, x, y + 1));
        } else {
            if (!isSelf(x, y + 1, red))
                moveList.add(new Move(x, y, x, y + 1));
        }
        return moveList;
    }

    @Override
    protected boolean canMove(boolean red, int x, int y, int tx, int ty) {
        return getAllMove(red, x, y).contains(new Move(x, y, tx, ty));
    }
}
