package gxu.data_structure.chess;

import gxu.data_structure.chess.util.MP3Player;
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
        panel.add(imageicon = new JLabel());

        imageicon.setBounds(127, 260, choose_double.getWidth(), choose_double.getHeight());
        panel.add(imageicon = new JLabel(new ImageIcon(choose_gameexit)));
        imageicon.setBounds(127, 360, choose_gameexit.getWidth(), choose_gameexit.getHeight());
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

    private void jLabelMouseExited(MouseEvent e) {
        JLabel jLabel = (JLabel) e.getSource();
        jLabel.setForeground(Color.black);
    }

    private void jLabelMouseEntered(MouseEvent e) {
        JLabel jLabel = (JLabel) e.getSource();
        jLabel.setForeground(Color.WHITE);
    }

    private void thisWindowClosing(WindowEvent e) {
        dispose();
    }

}
