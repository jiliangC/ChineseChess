package gxu.data_structure.chess.walk;

/**
 * 士的走法
 */
public class ShiWalker extends SlashWalker {
    public ShiWalker() {
        super(
                new int[][]{{17}, {15}, {-15}, {-17}},
                null
        );
    }

    @Override
    protected boolean filter(int x, int y, boolean red) {
        return isInNine(x, y, red);
    }
}
