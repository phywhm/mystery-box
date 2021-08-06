package pers.phy.ui;

import pers.phy.core.FreeMindParser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 主程序
 * @author PHY
 * @date 2021/8/5
 */
public class BoxFactory {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                startUp();
            }
        });
    }

    private static void startUp() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("盲盒生成机");
        frame.setSize(760, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screen.width / 2 - 380, screen.height / 2 - 100);
        JPanel panel = new JPanel();
        frame.add(panel);
        buildPanel(panel);
        frame.setVisible(true);
    }

    private static void buildPanel(final JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(panel4);
        final JFileChooser file1 = new JFileChooser();
        file1.setFileSelectionMode(JFileChooser.FILES_ONLY);
        file1.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                return f.getName().endsWith(".mm");
            }

            @Override
            public String getDescription() {
                return null;
            }
        });
        final JFileChooser file2 = new JFileChooser();
        file2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JLabel label1 = new JLabel("请选择脑图文件（*.mm）", null, SwingConstants.LEFT);
        panel1.add(label1);
        final JTextField field1 = new JTextField(System.getProperty("user.dir"), 40);
        panel1.add(field1);
        JButton cButton1 = new JButton("选择文件");
        cButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = file1.showOpenDialog(null);
                switch (result) {
                    case JFileChooser.CANCEL_OPTION:
                        break;
                    case JFileChooser.APPROVE_OPTION:
                        field1.setText(file1.getSelectedFile().getAbsolutePath());
                        break;
                }
            }
        });
        panel1.add(cButton1);
        JLabel label2 = new JLabel("         请选择盲盒输出目录", null, SwingConstants.LEFT);
        panel2.add(label2);
        final JTextField field2 = new JTextField(System.getProperty("user.dir"), 40);
        panel2.add(field2);
        JButton cButton2 = new JButton("选择目录");
        panel2.add(cButton2);
        cButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = file2.showOpenDialog(null);
                switch (result) {
                    case JFileChooser.CANCEL_OPTION:
                        break;
                    case JFileChooser.APPROVE_OPTION:
                        field2.setText(file2.getSelectedFile().getAbsolutePath());
                        break;
                }
            }
        });
        JButton ok = new JButton("确  认");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fName = field1.getText();
                if (fName == null || !fName.endsWith(".mm")) {
                    JOptionPane.showMessageDialog(panel, "请选择FreeMind思维导图文件(后缀为.mm)");
                    return;
                }
                try {
                    String dir = FreeMindParser.parse(field1.getText(), field2.getText());
                    Desktop.getDesktop().open(new File(dir));
                    JOptionPane.showMessageDialog(panel, "盲盒已生成");
                } catch (Exception ignore) {
                    JOptionPane.showMessageDialog(panel, "盲盒生成失败" + ignore.getMessage());
                }
            }
        });
        panel3.add(ok);
        JLabel labelF = new JLabel("Copyright 2021【作】您了伸手 all right reserved");
        panel4.add(labelF);
    }
}
