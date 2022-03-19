package gxu.data_structure.chess;

import gxu.data_structure.chess.util.MP3Player;
import gxu.data_structure.chess.util.Res;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class PrimaryPagePanel extends JPanel implements Constants, Res {
    private MP3Player.LoopPlay loopPlay;
    private BufferedImage image;

    public PrimaryPagePanel() {
        super();
        setSize(558, 620);

        start();
    }

    private Graphics2D resetBackground() {
        image = new BufferedImage(imageChessBoard.getWidth(), imageChessBoard.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = image.createGraphics();
        g.drawImage(imageChessBoard, 0, 0, null);
        return g;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }
    public void start(){
        Graphics2D g = resetBackground();
    }
}
