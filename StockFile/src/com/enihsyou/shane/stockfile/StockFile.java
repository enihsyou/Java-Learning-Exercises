package com.enihsyou.shane.stockfile;

import javax.swing.*;

public class StockFile {
    public static void main(String[] args){
        /*启动GUI线程*/
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("StockFile");
            frame.setContentPane(new MainGUI().GUI); //设置内容面板
            try {//设置样式
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            SwingUtilities.updateComponentTreeUI(frame);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //关闭按钮
            frame.pack(); //设置大小
            frame.setLocationRelativeTo(null); //居中
            frame.setVisible(true);
        });
    }

}

