import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.Format;

public class Rec {
    private static class Rectangle {
        private BigDecimal length;
        private BigDecimal width;
        private BigDecimal area;

        // Rectangle(double a) {
        //     new Rectangle(a, a);
        // }

        Rectangle(double length, double width) {
            setAttr(length, width);
        }

        Rectangle(String length, String width) {
            setAttr(Double.parseDouble(length), Double.parseDouble(width));
        }

        private void setAttr(double length, double width) {
            try {
                if (length < 0 || width < 0) {
                    throw new Exception("输入的值包含负数");
                } else {
                    this.length = BigDecimal.valueOf(length);
                    this.width = BigDecimal.valueOf(width);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        String calArea() {
            area = length.multiply(width);
            return area.toPlainString();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("矩形面积计算");
        frame.setLayout(new FlowLayout());
        final JTextField field1 = new JTextField(6);
        final JTextField field2 = new JTextField(6);
        final JTextField field3 = new JTextField(6);
        JLabel label1 = new JLabel("长");
        JLabel label2 = new JLabel("宽");
        JLabel label3 = new JLabel("面积");

        label1.setLabelFor(field1);
        label2.setLabelFor(field2);
        label3.setLabelFor(field3);

        JButton button = new JButton("计算");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                field3.setText(new Rectangle(field1.getText(), field2.getText()).calArea());
            }

        });

        JPanel panel1 = new JPanel(new FlowLayout());
        JPanel panel2 = new JPanel(new FlowLayout());
        JPanel panel3 = new JPanel(new FlowLayout());

        panel1.add(label1);
        panel1.add(field1);

        panel2.add(label2);
        panel2.add(field2);

        panel3.add(label3);
        panel3.add(field3);

        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(button);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // 显示在屏幕中心
        frame.setSize(240, 110);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
