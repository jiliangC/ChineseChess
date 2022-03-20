package gxu.data_structure.chess.robot;

import gxu.data_structure.chess.Constants;
import gxu.data_structure.chess.XqWalkState;
import gxu.data_structure.chess.walk.*;

public class Piece implements Constants {
    int index;
    Integer state;
    Walker walker;
    XqWalkState walkState;

    public Piece(Integer state, int index, XqWalkState xqWalkState) {
        this.walkState = xqWalkState;
        this.index = index;
        this.state = state;
        switch (state) {
            case redJiang, blackJiang -> {
                walker = new JiangWalker();
                walker.setState(walkState);
            }
            case redChe, blackChe -> {
                walker = new CheWalker();
                walker.setState(walkState);
            }
            case redPao, blackPao -> {
                walker = new PaoWalker();
                walker.setState(walkState);
            }
            case redMa, blackMa -> {
                walker = new MaWalker();
                walker.setState(walkState);
            }
            case redShi, blackShi -> {
                walker = new ShiWalker();
                walker.setState(walkState);
            }
            case redXiang, blackXiang -> {
                walker = new XiangWalker();
                walker.setState(walkState);
            }
            case redZu -> {
                walker = new RedZuWalker();
                walker.setState(walkState);
            }
            case blackZu -> {
                walker = new BlackZuWalker();
                walker.setState(walkState);
            }
        }
    }

    public Integer getState() {
        return state;
    }

    public int ForX() {
        return (index - 51) % 16;
    }

    public int ForY() {
        return (index - 51) / 16;
    }

    public Walker getWalker() {
        System.out.println(walker);
        return walker;
    }
}
