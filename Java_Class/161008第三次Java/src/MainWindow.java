import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;


/**
 * ����һ����֤����ַ�
 */
class StringGenerator {
    private StringBuffer captchaString = new StringBuffer();
    private String string = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private Random random = new Random();
    // private boolean generated = false;

    String generate() {
        int length = 6;
        return this.generate(length);
    }

    /**
     * @param length: ���ɵĳ���
     *
     * @return String: ���ɵ���֤��
     */
    String generate(int length) {
        for (int i = 0; i < length; i++) {
            captchaString.append(string.charAt(random.nextInt(string.length())));
        }
        // generated = true;
        return captchaString.toString();
    }

    // /**
    //  * @return String: ��ȡ��֤��ԭ�ĵ�Сд
    //  */
    // String getCaptchaLowerString() {
    //     assert (!generated);
    //     return captchaString.toString().toLowerCase();
    // }

    /**
     * @param string: �������õ��ַ����ɷ�Χ
     */
    void setString(String string) {
        this.string = string;
    }
}

/**
 * ����һ����֤���ͼƬ
 */
class CaptureImage {
    private String captcha = new StringGenerator().generate(); // ��ȡһ����֤��;
    private Random random = new Random();

    BufferedImage captureImage(int width, int height) {
        return this.captureImage(width, height, Color.blue, Color.red, new Color(255, 150, 20));
    }

    /**
     * @param width:      ���ɵ�ͼƬ���
     * @param height:     ���ɵ�ͼƬ�߶�
     * @param firstColor: ��һ������ɫ
     * @param lastColor:  �ڶ�������ɫ
     * @param fontColor:  ������ɫ
     *
     * @return BufferedImage: ��֤��ͼƬ
     */
    private BufferedImage captureImage(int width, int height, Color firstColor, Color lastColor, Color fontColor) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics(); // ����ͼ��
        int fontSize = height * 4 / 7;
        Font font = new Font("Helvetica", Font.BOLD, fontSize); //���ñȽϺ��ʵ������С
        graphics2D.setFont(font);

        GradientPaint gradientPaint = new GradientPaint(0, 0, firstColor, 0, height, lastColor);
        graphics2D.setPaint(gradientPaint); // ���ñ������콥��

        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(fontColor); // ����������ɫ

        int x, y;
        int pr = width / captcha.length(); // ��׼����
        for (int i = 0; i < captcha.length(); i++) {
            x = i * pr + random.nextInt(fontSize);
            y = height / 2 + random.nextInt(height / 2);
            if (x >= width - fontSize) {
                i--;
                continue;
            }
            graphics2D.drawChars(captcha.toCharArray(), i, 1, x, y);
        }
        graphics2D.dispose();

        return bufferedImage;
    }

    String getCaptcha() {
        return captcha.toLowerCase();
    }
}


public class MainWindow extends JFrame {
    // һ����֤��
    private CaptureImage captureImage = new CaptureImage();
    private ImageIcon icon = new ImageIcon(captureImage.captureImage(200, 50));
    // ͼƬ
    private JLabel picture = new JLabel();
    // ��ť
    private JButton submit = new SubmitButton("�ύ");
    private JButton refresh = new RefreshButton("������");
    // �����
    private JTextField input = new InputTextArea();

    //���������
    private static int wrong = 0;

    private MainWindow(int width, int height) {
        super("��֤��");
        new StringGenerator();
        setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*ͼƬ��*/

        picture.setIcon(icon);
        add(picture);

        /*������*/
        JPanel inputPanel = new JPanel(new FlowLayout());

        // ��Ӱ�ť�������
        inputPanel.add(input);
        inputPanel.add(submit);
        inputPanel.add(refresh);
        add(inputPanel, BorderLayout.SOUTH);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // ��ʾ����Ļ����

        setSize(width, height);
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setVisible(true);
    }

    private class SubmitEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputText = input.getText();
            if (inputText.isEmpty()) return;
            if (inputText.toLowerCase().equals(captureImage.getCaptcha())) {
                JOptionPane.showMessageDialog(null, "������ȷ", "��֤ͨ��", JOptionPane.NO_OPTION);
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(null, "����", "�������", JOptionPane.WARNING_MESSAGE);
                input.setText("");
                wrong += 1;
                if (wrong >= 3) {
                    JOptionPane.showMessageDialog(null, "����3��", "�����������", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        }
    }


    private class SubmitButton extends JButton {
        SubmitButton(String title) {
            setText(title);
            setFont(new Font("΢���ź�", 0, 16));
            setSize(40, 20);

            addActionListener(new SubmitEvent());
        }
    }

    private class RefreshButton extends JButton {
        RefreshButton(String title) {
            setText(title);
            setFont(new Font("΢���ź�", 0, 16));
            setSize(40, 20);

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    captureImage = new CaptureImage();
                    icon = new ImageIcon(captureImage.captureImage(200, 50));
                    picture.setIcon(icon);
                }
            });
        }
    }

    private class InputTextArea extends JTextField {
        InputTextArea() {
            setFont(new Font("΢���ź�", 0, 16));
            setColumns(6);
            addActionListener(new SubmitEvent());
        }
    }

    public static void main(String[] args) {
        new MainWindow(280, 140);
    }

}

