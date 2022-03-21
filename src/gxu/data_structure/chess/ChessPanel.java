package gxu.data_structure.chess;


import gxu.data_structure.chess.core.ChessBoardItem;
import gxu.data_structure.chess.core.Move;
import gxu.data_structure.chess.core.Point;
import gxu.data_structure.chess.core.WinEnum;
import gxu.data_structure.chess.robot.MaxMinTree;
import gxu.data_structure.chess.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;


/**
 * 象棋游戏的界面，提供如下功能：
 * A、棋盘的绘制
 * B、棋子的绘制、事件的触发；走棋后，事件传递给GameState去处理
 * C、获胜后的UI
 */
public class ChessPanel extends JPanel implements Constants, Res {

    private BufferedImage image; //panel的image,所有的画图操作都在这个image上进行，然后调用repaint方法重会。
    private boolean red = true; //默认是红先手
    private boolean playing = false; //游戏是否正在进行
    private XqChessBoard chessBoard = new XqChessBoard();
    private XqWalkState walkState = new XqWalkState(chessBoard);
    private MaxMinTree maxMinTree;
    private boolean robot;
    private MP3Player bgm = new MP3Player(Resource.getStream("go.mp3"));


    //评估函数
    //private final Evaluation evaluation = new Evaluation(chessBoard);


    //资源图片：
    private Point selectPoint; //选中的棋子

    public ChessPanel(boolean robot) {
        super();
        setSize(558, 620);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.robot = robot;
        //添加鼠标事件
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = floorDiv(e.getX() - x0);
                    int y = floorDiv(e.getY() - y0);
                    if (x >= 0 && x <= 8 && y >= 0 && y <= 9) {
                        System.out.print("选择了坐标为（" + x + "," + y + "）\n");
                        onClick(x, y);

                    }
                }
            }
        });
        //先手
        gameStartFirst();
    }

    //四舍五入除法，在涉及到点击点时，使用四舍五入能有效提高用户体验！
    private static int floorDiv(int x) {
        float div = x * 1f / lineHeight;
        int z = (int) div; //整数部分
        float point = div - z; //小数部分
        return point < 0.5 ? z : z + 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g.setColor(new Color(220,190,135));
        g.drawRect(558, 0, 186, 620);
        g.fillRect(558, 0, 186, 620);
    }

    //把背景图片画在棋盘上
    private Graphics2D resetBackground() {
        image = new BufferedImage(imageChessBoard.getWidth(), imageChessBoard.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = image.createGraphics();
        g.drawImage(imageChessBoard, 0, 0, null);


        return g;
    }

    /**
     * 在指定位置处画棋子
     *
     * @param num 棋子编号，取Constants中的常量
     * @param x   横坐标，取值0-8
     * @param y   纵坐标，取值0-9
     */
    private void drawChess(Graphics g, int num, int x, int y) {
        int px = x0 + x * lineHeight - chessSize / 2;
        int py = y0 + y * lineHeight - chessSize / 2;
        Image image = null;
        switch (num) {
            case redJiang:
                image = redJiangImg;
                break;
            case redShi:
                image = redShiImg;
                break;
            case redXiang:
                image = redXiangImg;
                break;
            case redMa:
                image = redMaImg;
                break;
            case redChe:
                image = redCheImg;
                break;
            case redPao:
                image = redPaoImg;
                break;
            case redZu:
                image = redZuImg;
                break;
            case blackJiang:
                image = blackJiangImg;
                break;
            case blackShi:
                image = blackShiImg;
                break;
            case blackXiang:
                image = blackXiangImg;
                break;
            case blackMa:
                image = blackMaImg;
                break;
            case blackChe:
                image = blackCheImg;
                break;
            case blackPao:
                image = blackPaoImg;
                break;
            case blackZu:
                image = blackZuImg;
                break;
            case EMPTY:
                break;
            case select:
                image = selectedImg;
                break;
            default:
                throw new IllegalStateException();

        }
        g.drawImage(image, px, py, null);
    }

    private void drawSelect(Graphics g, int x, int y) {

        int px = x0 + x * lineHeight - chessSize / 2;
        int py = y0 + y * lineHeight - chessSize / 2;
        g.drawImage(selectedImg, px, py, null);
    }

    private void drawCursor(Graphics g, int x, int y) {
        List<Move> list = walkState.getAllMove(red, x, y);
        for (Move move1 : list) {
            Point point = move1.getTo();
            int px = 20 + x0 + point.getX() * lineHeight - chessSize / 2;
            int py = 20 + y0 + point.getY() * lineHeight - chessSize / 2;
            g.drawImage(cursorImg, px, py, null);
        }

    }

    /**
     * 当用户在棋盘上点击时，触发
     *
     * @param x 横坐标，取值0-8
     * @param y 纵坐标，取值0-9
     */
    private void onClick(int x, int y) {
        if (!playing) {
            return;
        }

        if (walkState.canSelect(red, x, y)) { //如果可以选中这个棋子的话，则选中

            Graphics2D g = resetBackground();
            drawChessBoard(g);
            drawSelect(g, x, y); //绘制选择
            drawCursor(g, x, y);  //绘制可走点的光标
            g.dispose();
            repaint();
            selectPoint = new Point(x, y);
        } else if (selectPoint != null) {    //走棋了
            //原来点的棋子的坐标
            int tx = selectPoint.getX();
            int ty = selectPoint.getY();
            Move move = new Move(tx, ty, x, y);

            if (walkState.canMove(red, move)) { //判断可否走棋
                int state = chessBoard.getState(tx, ty);
                chessBoard.setState(tx, ty, EMPTY);
                int old = chessBoard.setState(x, y, state); //更新棋盘上的状态
                repaintBoard();
                selectPoint = null;
                hasWin(old);

                //bgm.play();
                red = !red;

                //到机器人走棋子
                if (!red && robot && playing) {
                    //这里最大最小搜索
                    maxMinTree = new MaxMinTree(chessBoard);
                    //System.out.println(maxMinTree.Max_min_tree(1,red));
                    Move m = maxMinTree.rootSearch(3, red);
                    System.out.println(m.getFrom() + "\n" + m.getTo());
                    int fx = m.getFrom().getX(), fy = m.getFrom().getY();
                    int tox = m.getTo().getX(), toy = m.getTo().getY();
                    int r_state = chessBoard.getState(fx, fy);
                    chessBoard.setState(fx, fy, EMPTY);
                    int r_old = chessBoard.setState(tox, toy, r_state); //更新棋盘上的状态
                    repaintBoard();
                    hasWin(r_old);
                    red = !red;
                }

            } else {
                System.out.println("不可走棋:" + move);
            }
        }
        //System.out.println("黑棋子的总价值为：" + maxMinTree.e());
        //System.out.println("棋力为："+maxMinTree.Max_min_tree(3,red));
    }

    private void hasWin(int state) {
        if (state == redJiang) {
            playing = false;


            MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "黑胜！", "提示");


        }
        if (state == blackJiang) {
            playing = false;
            MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "红胜！", "提示");
        }
        WinEnum winEnum = walkState.hasWin(red);
        switch (winEnum) {
            case RED:
                if (red) {
                    playing = false;

                    MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "黑胜！", "提示");
                } else {
                    MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "红棋被将军", "提示");
                    System.out.println("红棋被将军！");
                }
                break;
            case BLACK:
                if (!red) {
                    playing = false;
                    MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "红胜！", "提示");
                } else {
                    System.out.println("黑棋被将军！");
                    MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "黑棋被将军！", "提示");
                }
                break;
            case RED_KILL:
                playing = false;
                MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "绝杀！黑胜！", "提示");
                break;
            case BLACK_KILL:
                playing = false;
                MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "绝杀！红胜！", "提示");
                break;
            case DRAW:
                playing = false;
                MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "和棋！", "提示");
                break;
        }
    }

    private void repaintBoard() {
        Graphics2D g = resetBackground();
        drawChessBoard(g);
        g.dispose();
        repaint();
    }

    private void drawChessBoard(Graphics2D g) {
        for (ChessBoardItem chessBoardItem : chessBoard) {
            drawChess(g, chessBoardItem.getState(), chessBoardItem.getX(), chessBoardItem.getY());
        }
    }

    //游戏开局（先手）
    public void gameStartFirst() {
        playing = true;
        red = true;
        chessBoard.clear();
        chessBoard.setDefault();


        //绘制背景图片
        Graphics2D g = resetBackground();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawChessBoard(g);
    }

    //游戏开局 后手
    public void gameStartLast() {
        gameStartFirst();
        red = false;
    }

    //保存棋局
    public void save(OutputStream outputStream) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        GameSave gameSave = new GameSave(chessBoard, red, playing);
        objectOutputStream.writeObject(gameSave);
    }

    //加载棋局
    public void load(InputStream inputStream) throws Exception {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        GameSave gameSave = (GameSave) objectInputStream.readObject();
        chessBoard = (XqChessBoard) gameSave.getChessBoard();
        red = gameSave.isRed();
        playing = gameSave.isPlaying();
        selectPoint = null;
        walkState.setChessBoard(chessBoard);
        repaintBoard();
    }


}
