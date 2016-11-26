import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class MainGUI extends JPanel {
    private JTextField stockIDTextField;
    private JTextField stockNameTextField;
    private JButton addButton;
    private JButton fetchButton;
    private JButton importButton;
    private JButton saveButton;
    private JTextArea stockText;

    JPanel GUI;
    private Path openFile;
    private StockList stocks;

    private static String lineSeparator = System.getProperty("line.separator");

    public MainGUI() {
        /*初始化元素*/
        this.stocks = new StockList();
        //        this.orderedStocks = new SortedList();
        stockText.setEditable(false); //禁止编辑
        /*添加监听器*/
        //代码输入框 直接进入下一个输入框
        stockIDTextField.addActionListener(e -> {
            stockNameTextField.grabFocus();//转到下一个输入框
        });
        class JTextFieldLimit extends PlainDocument {
            /*限制输入内容 并提供不怎么样的自动完成*/
            private int limit;

            private JTextFieldLimit(int limit) {
                super();
                this.limit = limit;
            }

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null) return;
                if (getLength() + str.length() < limit) { //长度没超过6
                    if (!str.matches("\\d+")) return; //输入的不是数字
                }
                stockNameTextField.setText(""); //如果下面不匹配的话 名字设置空
                String input = stockIDTextField.getText() + str;
                System.out.println(input);
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
        }
        stockIDTextField.setDocument(new JTextFieldLimit(6));

        //名字输入框 确认操作 添加
        stockNameTextField.addActionListener(e -> {
            addButton.getActionListeners()[0].actionPerformed(e);//调用动作
        });
        //导入按钮 打开文件选择面板 导入文件到显示区域
        importButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser(
                    openFile == null ? Paths.get(System.getProperty("user.dir")).toFile() : openFile.toFile());
            int returnValue = chooser.showOpenDialog(MainGUI.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                openFile = chooser.getSelectedFile().toPath();
                System.out.println("打开选择了:" + openFile.getFileName());
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
                StockList stockList = jsonParser.parse();
                stockText.setText(""); //先清空
                for (StockItem item : stockList.getStocks()) {
                    stockText.append(item.toString());
                    stockText.append(lineSeparator);
                }
            } catch (IOException ignored) {}
        }).start());
        //添加按钮
        addButton.addActionListener(e -> {
            String inputStockID = stockIDTextField.getText();
            String inputStockName = stockNameTextField.getText();

            try {
                StockItem newItem = new StockItem(inputStockID, inputStockName); //创建对象
                stocks.addStock(newItem); //添加对象
                //                orderedStocks.add()
                stockText.append(newItem.toString()); //修改文本域
                stockText.append(lineSeparator);
            } catch (NumberFormatException ignored) {
                JOptionPane.showMessageDialog(null, "输入代码有误，请重新输入", "输入错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        //保存按钮 如果没有打开过文件 就进行文件选择
        saveButton.addActionListener(e -> {
            if (openFile == null) {//在没有打开文件的情况下 选择新建
                JFileChooser chooser = new JFileChooser(Paths.get(System.getProperty("user.dir")).toFile());
                int returnValue = chooser.showSaveDialog(MainGUI.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    openFile = chooser.getSelectedFile().toPath();
                    System.out.println("保存选择了:" + openFile.getFileName());
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
                JOptionPane.showMessageDialog(MainGUI.this, "保存成功");
            } catch (IOException ignored) {}
        });
    }

    {
        // GUI initializer generated by IntelliJ IDEA GUI Designer
        // >>> IMPORTANT!! <<<
        // DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        GUI = new JPanel();
        GUI.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(4, 4, 4, 4), -1, -1));
        GUI.setFont(new Font(GUI.getFont().getName(), GUI.getFont().getStyle(), GUI.getFont().getSize()));
        GUI.setMinimumSize(new Dimension(250, 250));
        GUI.setPreferredSize(new Dimension(250, 300));
        GUI.setRequestFocusEnabled(true);
        GUI.setVisible(true);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, 6));
        GUI.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null, 0, false));
        stockIDTextField = new JFormattedTextField();
        stockIDTextField.setColumns(6);
        panel2.add(stockIDTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0,
                false));
        final JLabel label1 = new JLabel();
        label1.setText("股票代码");
        panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setInheritsPopupMenu(true);
        label2.setText("股票名字");
        panel3.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                com.intellij.uiDesigner.core.GridConstraints.FILL_NONE,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stockNameTextField = new JFormattedTextField();
        stockNameTextField.setColumns(0);
        panel3.add(stockNameTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0,
                false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, 4));
        panel4.setAutoscrolls(false);
        GUI.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, true));
        fetchButton = new JButton();
        fetchButton.setFont(new Font(fetchButton.getFont().getName(), fetchButton.getFont().getStyle(),
                fetchButton.getFont().getSize()));
        fetchButton.setHorizontalTextPosition(0);
        fetchButton.setMargin(new Insets(0, 0, 0, 0));
        fetchButton.setText("获取");
        panel4.add(fetchButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        importButton = new JButton();
        importButton.setFont(new Font(importButton.getFont().getName(), importButton.getFont().getStyle(),
                importButton.getFont().getSize()));
        importButton.setHorizontalTextPosition(0);
        importButton.setMargin(new Insets(0, 0, 0, 0));
        importButton.setText("导入");
        panel4.add(importButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setFont(
                new Font(addButton.getFont().getName(), addButton.getFont().getStyle(), addButton.getFont().getSize()));
        addButton.setHorizontalTextPosition(0);
        addButton.setMargin(new Insets(0, 0, 0, 0));
        addButton.setText("添加");
        panel4.add(addButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setFont(new Font(saveButton.getFont().getName(), saveButton.getFont().getStyle(),
                saveButton.getFont().getSize()));
        saveButton.setHorizontalTextPosition(0);
        saveButton.setMargin(new Insets(0, 0, 0, 0));
        saveButton.setText("保存");
        panel4.add(saveButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setAutoscrolls(true);
        GUI.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1,
                com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER,
                com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW,
                com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW,
                null, null, null, 0, false));
        stockText = new JTextArea();
        scrollPane1.setViewportView(stockText);
        label1.setLabelFor(stockIDTextField);
        label2.setLabelFor(stockNameTextField);
    }

    /** @noinspection ALL */
    public JComponent $$$getRootComponent$$$() { return GUI; }
}



