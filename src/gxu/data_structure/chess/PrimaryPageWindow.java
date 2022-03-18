package gxu.data_structure.chess;

import gxu.data_structure.chess.util.MP3Player;
import gxu.data_structure.chess.util.MyOptionPane;
import gxu.data_structure.chess.util.Res;
import gxu.data_structure.chess.util.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PrimaryPageWindow extends JFrame implements Res {
    private MP3Player.LoopPlay loopPlay;
    private PrimaryPagePanel panel;
    private JLabel imageicon;

    public PrimaryPageWindow(){
        initPrimaryPage();

        MP3Player bgm = new MP3Player(Resource.getStream("game.mp3"));
        loopPlay = bgm.loopPlay();

    }

    @Override
    //关闭窗口体，停止音乐
    public void dispose() {
        loopPlay.stopPlay();
        super.dispose();
    }

    private void initPrimaryPage() {
        panel = new PrimaryPagePanel();

        setResizable(false);
        setTitle("中国象棋");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        var contentPane = getContentPane();
        contentPane.setLayout(null);
        panel.setBounds(0, 0, 558, 620);
        panel.setLayout(null);
        JLabel  jLabel1 = new JLabel();
        jLabel1.setText("单人游戏");
        panel.add(jLabel1);
        jLabel1.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 60));
        jLabel1.setBounds(127, 160, choose_single.getWidth(), choose_single.getHeight());
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jLabelMouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jLabelMouseExited(e);
            }
        });
        JLabel  jLabel2 = new JLabel();
        jLabel2.setText("双人对战");
        panel.add(jLabel2);
        jLabel2.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 60));
        jLabel2.setBounds(127, 260, choose_single.getWidth(), choose_single.getHeight());
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jLabelMouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jLabelMouseExited(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                DoubleGame();
            }
        });

        JLabel  jLabel3 = new JLabel();
        jLabel3.setText("退出游戏");
        panel.add(jLabel3);
        jLabel3.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 60));
        jLabel3.setBounds(127, 360, choose_single.getWidth(), choose_single.getHeight());
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jLabelMouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jLabelMouseExited(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                GameExit();
            }
        });
        contentPane.add(panel);
        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(null);
    }

    private void DoubleGame() {
        ChessPanel pnl = new ChessPanel();
        var contentPane = getContentPane();

        contentPane.setLayout(null);
        contentPane.add(pnl);
        pnl.setBounds(0, 0, 558, 620);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(null);

    }

    private void GameExit() {
        if (MyOptionPane.showConfirmDialog(this, "提示", "你是否要退出？", "是", "否")) {
            dispose();
        }
    }

    private void jLabelMouseExited(MouseEvent e) {
        JLabel jLabel = (JLabel) e.getSource();
        jLabel.setForeground(new Color(51,51,51));
    }

    private void jLabelMouseEntered(MouseEvent e) {
        JLabel jLabel = (JLabel) e.getSource();
        jLabel.setForeground(Color.WHITE);
    }

    private void thisWindowClosing(WindowEvent e) {
        dispose();
    }

}
