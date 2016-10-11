import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InputTextArea extends JPanel {
    private JTextField textField = new JTextField(16);

    private class SubmitEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputText = textField.getText();
            if (inputText.isEmpty()) return;
            Pattern pattern1 = Pattern.compile("(?:(?![!\\d])[0-9a-zA-Z$_]+)");
            Pattern pattern2 = Pattern.compile("abstract|boolean|break|byte|case|const|goto|catch|char|class|continue|default|do|double|else|extends|false|final|finally|float|for|if|implements|import|instanceof|int|interface|long|native|new|null|package|private|protected|public|return|short|static|super|switch|synchronized|this|throw|throws|transient|true|try|void|volatile|while");
            Matcher matcher = pattern1.matcher(inputText);

            if (matcher.matches()) {
                if (!pattern2.matcher(inputText).matches()) {
                    JOptionPane.showMessageDialog(null, inputText + " �ǺϷ��ı�ʾ��", "�Ϸ��ı�ʾ��", JOptionPane.PLAIN_MESSAGE);
                    textField.setText("");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "���Ϸ��ı�ʾ��", "���Ϸ��ı�ʾ��", JOptionPane.OK_OPTION);
        }
    }

    InputTextArea(String text) {
        setLayout(new FlowLayout());
        JLabel label = new JLabel();
        JButton button = new JButton("�ύ");
        label.setText(text);
        label.setDisplayedMnemonic(KeyEvent.VK_N);
        label.setLabelFor(this.textField);
        button.addActionListener(new SubmitEvent());
        textField.addActionListener(new SubmitEvent());


        add(label, BorderLayout.WEST);
        add(textField, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);
    }
}

class Window extends JFrame {

    Window(int width, int height) {
        setTitle("�Ϸ���ʶ��");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(new InputTextArea("��ʶ��"));

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // ��ʾ����Ļ����
        setSize(width, height);
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setVisible(true);
    }

}


public class Judge1 extends JFrame {
    public static void main(String[] args) {
        new Window(320, 80);
    }
}

