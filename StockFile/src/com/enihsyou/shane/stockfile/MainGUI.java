package com.enihsyou.shane.stockfile;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Stream;

public class MainGUI extends JPanel {
    private static final String lineSeparator = System.getProperty("line.separator");
    private static final Logger LOGGER = Logger.getLogger(MainGUI.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler(MainGUI.class.getName() + ".log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
        } catch (IOException ignored) {}
        LOGGER.setLevel(Level.ALL);
    }

    JPanel GUI;
    private JTextField stockIDTextField;
    private JTextField stockNameTextField;
    private JButton addButton;
    private JButton fetchButton;
    private JButton importButton;
    private JButton saveButton;
    private JTextArea stockText;
    private Path openFile;//当前打开文件
    private StockList stocks;//对应对象

    MainGUI() {
        /*初始化元素*/
        this.stocks = new StockList();
        stockText.setEditable(false); //禁止编辑

        /*添加监听器*/
        //代码输入框 直接进入下一个输入框
        stockIDTextField.addActionListener(e -> {
            LOGGER.fine("确认代码输入框");
            stockNameTextField.grabFocus();//转到下一个输入框
        });
        stockIDTextField.setDocument(new PlainDocument() {  //限制输入内容 并提供不怎么样的自动完成
            private int limit = 6;

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) return;
                // LOGGER.log(Level.FINE, "输入总长度{0}, 限制{1}", new Object[]{getLength() + str.length(), limit});
                if (getLength() + str.length() <= limit) { //长度没超过6
                    if (!str.matches("\\d+")) return; //输入的不是数字
                } else return;
                stockNameTextField.setText(""); //如果下面不匹配的话 名字设置空
                String input = stockIDTextField.getText() + str;
                LOGGER.log(Level.FINE, "输入内容: {0}", input);
                for (StockItem stock : stocks.getStocks()) {
                    if (stock.getId().startsWith(input)) {
                        int selectionStart = input.length();
                        super.insertString(offs, stock.getId().substring(offs), a);
                        stockNameTextField.setText(stock.getName());
                        stockIDTextField.setSelectionStart(selectionStart);
                        return;
                    }
                }
                super.insertString(offs, str, a);
            }
        });

        //名字输入框 确认操作 执行添加按钮动作
        stockNameTextField.addActionListener(e -> {
            LOGGER.fine("确认名字输入框");
            addButton.getActionListeners()[0].actionPerformed(e);//调用动作
        });

        //导入按钮 打开文件选择面板 导入文件到显示区域
        importButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(
                    openFile == null ? Paths.get(System.getProperty("user.dir")).toFile() : openFile.toFile());
            int returnValue = chooser.showOpenDialog(MainGUI.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                openFile = chooser.getSelectedFile().toPath();
                LOGGER.info("打开选择了:" + openFile.getFileName());
                //先清空
                stocks = new StockList();
                stockText.setText("");
                //在创建新的
                try (Stream<String> lines = Files.lines(openFile)) {
                    lines.forEach(s -> {
                        stockText.append(s + lineSeparator);
                        String[] read = s.split("\t");
                        stocks.addStock(new StockItem(read[0], read[1]));
                    });
                    LOGGER.fine(String.format("导入了%d个", stocks.getStocks().size()));
                } catch (IOException ignored) {}
            }
        });

        //获取按钮 新建线程从API获取所有股票列表
        fetchButton.addActionListener(e -> new Thread(() -> {
            URL url;
            StockJsonParser jsonParser;
            try {
                url = new URL("http://ctxalgo.com/api/stocks");
                jsonParser = new StockJsonParser(url);
                long startTime = System.currentTimeMillis();
                StockList stockList = jsonParser.parse();
                stockText.setText(""); //先清空
                for (StockItem item : stockList.getStocks()) {
                    stockText.append(item.toString());
                    stockText.append(lineSeparator);
                    stocks.addStock(item);
                }
                LOGGER.fine(String.format("获取了%d个，用时%dms", stockList.getStocks().size(),
                        System.currentTimeMillis() - startTime));
            } catch (IOException ignored) {}
        }).start());

        //添加按钮
        addButton.addActionListener(e -> {
            String inputStockID = stockIDTextField.getText();
            String inputStockName = stockNameTextField.getText();

            try {
                StockItem newItem = new StockItem(inputStockID, inputStockName); //创建对象
                boolean added = stocks.addStock(newItem); //添加对象
                if (added) {
                    stockText.append(newItem.toString()); //修改文本域
                    stockText.append(lineSeparator);
                } else {
                    JOptionPane.showMessageDialog(GUI, "输入代码重复，请使用其他的", "重复输入", JOptionPane.ERROR_MESSAGE);
                    LOGGER.info(String.format("输入ID:%s, NAME:%s重复 请求重新输入", inputStockID, inputStockName));
                }
            } catch (NumberFormatException e1) {
                LOGGER.info(String.format("输入ID:%s, NAME:%s无效 请求重新输入", inputStockID, inputStockName));
                JOptionPane.showMessageDialog(GUI, "输入代码有误，请重新输入", "输入错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        //保存按钮 如果没有打开过文件 就进行文件选择
        saveButton.addActionListener(e -> {
            if (openFile == null) {//在没有打开文件的情况下 选择新建
                JFileChooser chooser = new JFileChooser(Paths.get(System.getProperty("user.dir")).toFile());
                int returnValue = chooser.showSaveDialog(MainGUI.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    openFile = chooser.getSelectedFile().toPath();
                    LOGGER.info("保存选择了:" + openFile.getFileName());
                } else return;
            }
            /*开始写入*/
            try (BufferedWriter writer = Files.newBufferedWriter(openFile)) {
                //因为现在列表使用了TreeSet是有序的，所以使用TextArea内容写入
                //for (StockItem stock : stocks.getStocks()) {
                //    writer.write(stock.toString());
                //    writer.newLine();
                //}
                stockText.getText().chars().forEach((c) -> {
                    try {
                        writer.write(c);
                    } catch (IOException ignored) {}
                });
                JOptionPane.showMessageDialog(GUI, "保存成功");
            } catch (IOException ignored) {}
        });
    }
}



