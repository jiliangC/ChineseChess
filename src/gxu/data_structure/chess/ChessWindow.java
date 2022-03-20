package gxu.data_structure.chess;

import gxu.data_structure.chess.util.MP3Player;
import gxu.data_structure.chess.util.MyOptionPane;
import gxu.data_structure.chess.util.Resource;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ChessWindow extends JFrame implements Constants {

    private MP3Player.LoopPlay loopPlay;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem4;
    private JMenuItem menuItem7;
    private JMenuItem menuItem2;
    private JMenu menu3;
    private JMenuItem menuItem5;
    private JMenuItem menuItem6;
    private JMenu menu2;
    private JMenuItem menuItem3;
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
            new PrimaryPageWindow().setVisible(true);
        }
    }

    //保存棋谱
    private void menuGameSave(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileFilter fileFilter = new FileNameExtensionFilter("中国象棋棋谱文件（.cnchess）", "cnchess");
        chooser.setFileFilter(fileFilter);
        chooser.setMultiSelectionEnabled(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            String fname = chooser.getName(file);
            if (!fname.endsWith(".cnchess")) {
                file = new File(chooser.getCurrentDirectory(), fname = fname + ".cnchess");
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                panel.save(fileOutputStream);
                MyOptionPane.showMessageDialog(this, "成功保存棋谱到" + fname, "提示");
            } catch (Exception ex) {
                ex.printStackTrace();
                MyOptionPane.showMessageDialog(this, "加载棋谱失败！", "错误");
            }
        }
    }

    //加载棋谱
    private void menuGameLoad(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileFilter fileFilter = new FileNameExtensionFilter("中国象棋棋谱文件（.cnchess）", "cnchess");
        chooser.setFileFilter(fileFilter);
        chooser.setMultiSelectionEnabled(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                panel.load(fileInputStream);
                MyOptionPane.showMessageDialog(this, "加载棋谱成功！", "提示");
            } catch (Exception ex) {
                ex.printStackTrace();
                MyOptionPane.showMessageDialog(this, "加载棋谱失败！", "错误");
            }
        }
    }

    //假悔棋
    private void menuBackChess(ActionEvent e) {
        MyOptionPane.showMessageDialog(this, "落子无悔大丈夫！", "提示");
    }

    //About
    private void menuAbout(ActionEvent e) {
        MyOptionPane.showMessageDialog(this, "数据结构课设\n陈计良and林世鸿", "提示");

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menuItem7 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menu3 = new JMenu();
        menuItem5 = new JMenuItem();
        menuItem6 = new JMenuItem();
        menu2 = new JMenu();
        menuItem3 = new JMenuItem();
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

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("\u6e38\u620f");

                //---- menuItem1 ----
                menuItem1.setText("\u91cd\u65b0\u5f00\u5c40\uff08\u5148\u624b\uff09");
                menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
                menuItem1.addActionListener(e -> menuGameStartFirst(e));
                menu1.add(menuItem1);

                //---- menuItem4 ----
                menuItem4.setText("\u91cd\u65b0\u5f00\u5c40\uff08\u540e\u624b\uff09");
                menuItem4.addActionListener(e -> menuGameStartLast(e));
                menu1.add(menuItem4);

                //---- menuItem7 ----
                menuItem7.setText("\u6094\u68cb");
                menuItem7.addActionListener(e -> menuBackChess(e));
                menu1.add(menuItem7);

                //---- menuItem2 ----
                menuItem2.setText("\u9000\u51fa");
                menuItem2.addActionListener(e -> menuConfirmExit(e));
                menu1.add(menuItem2);
                menu1.addSeparator();
            }
            menuBar1.add(menu1);

            //======== menu3 ========
            {
                menu3.setText("\u68cb\u8c31");

                //---- menuItem5 ----
                menuItem5.setText("\u4fdd\u5b58\u68cb\u5c40");
                menuItem5.addActionListener(e -> menuGameSave(e));
                menu3.add(menuItem5);

                //---- menuItem6 ----
                menuItem6.setText("\u52a0\u8f7d\u68cb\u5c40");
                menuItem6.addActionListener(e -> menuGameLoad(e));
                menu3.add(menuItem6);
            }
            menuBar1.add(menu3);

            //======== menu2 ========
            {
                menu2.setText("\u5173\u4e8e");

                //---- menuItem3 ----
                menuItem3.setText("\u6570\u636e\u7ed3\u6784");
                menuItem3.addActionListener(e -> menuAbout(e));
                menu2.add(menuItem3);
            }
            menuBar1.add(menu2);
        }
        setJMenuBar(menuBar1);
        contentPane.add(panel);
        panel.setBounds(0, 0, 558, 620);

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
