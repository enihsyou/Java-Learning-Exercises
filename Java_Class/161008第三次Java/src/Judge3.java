import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Judge3 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("����ת��");
        JPanel panel = new JPanel();

        JLabel label = new JLabel("��������");
        final JTextField field = new JTextField(20);
        JButton button = new JButton("�ύ");

        label.setLabelFor(field);

        class SubmitEvent implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = field.getText();
                if (inputText.isEmpty()) return;
                Pattern pattern1 = Pattern.compile("\\d+(?:\\.\\d+)?");
                Pattern pattern2 = Pattern.compile("[a-zA-Z]+");
                Matcher matcher1 = pattern1.matcher(inputText);
                String result = pattern2.matcher(matcher1.replaceAll("����")).replaceAll("����");

                if (!result.isEmpty()) {
                    JOptionPane.showMessageDialog(null, result, "ת�����", JOptionPane.PLAIN_MESSAGE);
                    field.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "ת��ʧ��", "ת��ʧ��", JOptionPane.OK_OPTION);
                }
            }

        }

        button.addActionListener(new SubmitEvent());
        field.addActionListener(new SubmitEvent());
        /*���Ԫ��*/

        panel.add(label);
        panel.add(field);
        panel.add(button);

        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // ��ʾ����Ļ����
        frame.setSize(320, 100);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);
    }
}
