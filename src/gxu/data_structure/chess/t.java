/*
 * Created by JFormDesigner on Fri Mar 18 16:56:22 AWST 2022
 */

package gxu.data_structure.chess;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author unknown
 */
public class t extends JFrame {
    public t() {
        initComponents();
    }

    private void label1MouseEntered(MouseEvent e) {
        System.out.println(1);
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("text");
        label1.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 48));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label1MouseEntered(e);
            }
        });
        contentPane.add(label1);
        label1.setBounds(190, 110, 175, 100);

        contentPane.setPreferredSize(new Dimension(640, 480));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel label1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
