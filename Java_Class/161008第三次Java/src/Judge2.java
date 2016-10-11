import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Judge2 {
    private static String listToString(ArrayList<String> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (string.isEmpty()) continue;
            if (flag) {
                result.append("\n");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public static void main(String[] args) {

        final ArrayList<String> right = new ArrayList<>();
        final ArrayList<String> wrong = new ArrayList<>();

        JFrame frame = new JFrame("邮件地址验证");
        JPanel panel = new JPanel();
        JLabel label = new JLabel("输入电子邮件，可以使用逗号隔开");
        final JTextArea area = new JTextArea(5, 20);
        JButton button = new JButton("提交");

        /*设置属性*/
        area.setLineWrap(true);

        /*事件响应*/
        class SubmitEvent implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = area.getText();
                if (inputText.isEmpty()) return;
                Pattern pattern = Pattern.compile("^((?=[a-zA-Z])[0-9a-zA-Z_]+(\\.[0-9a-zA-Z_]+)*@[0-9a-zA-Z]+\\.[a-zA-Z]{2,4})$");
                for (String text : inputText.split(",")) {
                    text = text.trim();
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.matches()) {
                        right.add(matcher.group(1));
                    } else {
                        wrong.add(text);
                    }

                }
                if (!right.isEmpty()) {
                    String result = listToString(right);
                    JOptionPane.showMessageDialog(null, result, "正确", JOptionPane.PLAIN_MESSAGE);
                    area.setText("");
                } else {
                    String result = listToString(wrong);
                    JOptionPane.showMessageDialog(null, "不正确的有:\n" + result, "不正确", JOptionPane.OK_OPTION);
                }
                right.clear();
                wrong.clear();
            }

        }

        label.setLabelFor(area);
        button.addActionListener(new SubmitEvent());
        /*添加元素*/
        panel.add(label);
        panel.add(area);
        panel.add(button);

        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // 显示在屏幕中心
        frame.setSize(320, 180);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);
    }
}
