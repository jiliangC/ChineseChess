package gxu.data_structure.chess;

import gxu.data_structure.chess.util.MP3Player;
import gxu.data_structure.chess.util.MyOptionPane;
import gxu.data_structure.chess.util.Res;
import gxu.data_structure.chess.util.Resource;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ChessWindow extends JFrame implements Constants, Res {

    private MP3Player.LoopPlay loopPlay;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu_file;
    private JMenuItem menuItem_save;
    private JMenuItem menuItem_open;
    private JMenu menu_help;
    private JMenuItem menuItem_about;
    private JMenuItem menuItem_more;
    private ChessPanel panel;
    private boolean robot;

    public ChessWindow(boolean robot) {
        this.robot = robot;
        initComponents();

        //循环播放音乐
        MP3Player bgm = new MP3Player(Resource.getStream("game.mp3"));
        loopPlay = bgm.loopPlay();
    }

    @Override
    //关闭窗口体，停止音乐
    public void dispose() {
        loopPlay.stopPlay();
        super.dispose();
    }

    //点击菜单的退出
    private void menuConfirmExit(ActionEvent e) {
        if (MyOptionPane.showConfirmDialog(this, "提示", "你是否要退出？", "是", "否")) {
            dispose();
            new PrimaryPageWindow().setVisible(true);
        }
    }

    //先手
    private void menuGameStartFirst(ActionEvent e) {
        if (MyOptionPane.showConfirmDialog(this, "提示", "你是否要重新开局（先手）？", "是", "否")) {
            panel.gameStartFirst();
            panel.repaint();
        }

    }

    //后手
    private void menuGameStartLast(ActionEvent e) {
        if (MyOptionPane.showConfirmDialog(this, "提示", "你是否要重新开局（后手）？", "是", "否")) {
            panel.gameStartLast();
            panel.repaint();
        }
    }

    //点击右上角的退出
    private void thisWindowClosing(WindowEvent e) {
        if (MyOptionPane.showConfirmDialog(this, "提示", "你是否要退出？", "是", "否")) {
            dispose();
            /*new PrimaryPageWindow().setVisible(true);*/
        }
    }
    //假悔棋
    private void menuBackChess(ActionEvent e) {
        MyOptionPane.showMessageDialog(this, "落子无悔大丈夫！", "提示");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu_file = new JMenu();
        menuItem_save = new JMenuItem();
        menuItem_open = new JMenuItem();
        menu_help = new JMenu();
        menuItem_about = new JMenuItem();
        menuItem_more = new JMenuItem();
        panel = new ChessPanel(robot);

        //======== this ========
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


        contentPane.add(panel);
        panel.setBounds(0, 0, 744, 620);
        panel.setLayout(null);

        JLabel jLabel_return = new JLabel("返回首页");
        panel.add(jLabel_return);
        jLabel_return.setFont(new Font("隶书", Font.BOLD, 34));
        jLabel_return.setForeground(new Color(200,40,20));
        jLabel_return.setBounds(578, 560, 148, 50);
//        jLabel_return.setBorder(BorderFactory.createLineBorder(new Color(51,51,51)));
        jLabel_return.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_return.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JLabel jLabel = (JLabel) e.getSource();
                jLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JLabel jLabel = (JLabel) e.getSource();
                jLabel.setForeground(new Color(200,40,20));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new PrimaryPageWindow().setVisible(true);
                dispose();
            }
        });

        //黑方
        JLabel jLabel_restartblack = new JLabel("重新开始");
        panel.add(jLabel_restartblack);
        jLabel_restartblack.setFont(new Font("隶书", Font.BOLD, 28));
        jLabel_restartblack.setBounds(578, 90, 148, 30);
//        jLabel_restartblack.setBorder(BorderFactory.createLineBorder(new Color(51,51,51)));
        jLabel_restartblack.setHorizontalAlignment(SwingConstants.CENTER);
        //黑方名称
        JLabel jLabel_blackname = new JLabel("黑方");
        jLabel_blackname.setFont(new Font("隶书", Font.BOLD, 16));
        jLabel_blackname.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_blackname.setBounds(558+186/2-25, panel.getHeight()/2-40-30, 50, 30);
        panel.add(jLabel_blackname);
        //黑方头像
        JLabel jLabel_black = new JLabel();
        jLabel_black.setIcon(new ImageIcon(blackJiangImg));
        jLabel_black.setBounds((186-blackJiangImg.getWidth())/2+558, panel.getHeight()/2-blackJiangImg.getHeight()-80, blackJiangImg.getWidth(),blackJiangImg.getHeight());
        panel.add(jLabel_black);

        //红方
        JLabel jLabel_restartred = new JLabel("重新开始");
        panel.add(jLabel_restartred);
        jLabel_restartred.setFont(new Font("隶书", Font.BOLD, 28));
        jLabel_restartred.setBounds(578, 500, 148, 30);
//        jLabel_restartred.setBorder(BorderFactory.createLineBorder(new Color(51,51,51)));
        jLabel_restartred.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_restartred.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.gameStartFirst();
                panel.repaint();

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JLabel jLabel = (JLabel) e.getSource();
                jLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JLabel jLabel = (JLabel) e.getSource();
                jLabel.setForeground(new Color(51,51,51));
            }

        });
        //红方头像
        JLabel jLabel_red = new JLabel();
        jLabel_red.setIcon(new ImageIcon(redJiangImg));
        jLabel_red.setBounds((186-redJiangImg.getWidth())/2+558, panel.getHeight()/2+80, redJiangImg.getWidth(),redJiangImg.getHeight());
        panel.add(jLabel_red);
        //红方名称
        JLabel jLabel_redname = new JLabel("红方");
        jLabel_redname.setFont(new Font("隶书", Font.BOLD, 16));
        jLabel_redname.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel_redname.setBounds(558+186/2-25, panel.getHeight()/2+40, 50, 30);
        panel.add(jLabel_redname);

        if(robot){
            jLabel_restartblack.setText("难度一");
            jLabel_restartblack.setBounds(578, 50, 148, 30);
            jLabel_restartred.setText("难度二");
            jLabel_restartred.setBounds(578, 90, 148, 30);
            jLabel_restartblack.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    panel.gameStartFirst();
                    panel.repaint();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    JLabel jLabel = (JLabel) e.getSource();
                    jLabel.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    JLabel jLabel = (JLabel) e.getSource();
                    jLabel.setForeground(new Color(51,51,51));
                }

            });
        }
        else{
            jLabel_restartred.setFont(new Font("隶书", Font.BOLD, 20));
            jLabel_restartblack.setFont(new Font("隶书", Font.BOLD, 20));
            jLabel_restartblack.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    panel.gameStartLast();
                    panel.repaint();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    JLabel jLabel = (JLabel) e.getSource();
                    jLabel.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    JLabel jLabel = (JLabel) e.getSource();
                    jLabel.setForeground(new Color(51,51,51));
                }

            });
        }

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
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }


    // JFormDesigner - End of variables declaration  //GEN-END:variables

}
