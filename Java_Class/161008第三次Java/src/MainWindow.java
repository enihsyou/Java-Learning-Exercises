import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;


/**
 * 生成一段验证码的字符
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
     * @param length: 生成的长度
     *
     * @return String: 生成的验证码
     */
    String generate(int length) {
        for (int i = 0; i < length; i++) {
            captchaString.append(string.charAt(random.nextInt(string.length())));
        }
        // generated = true;
        return captchaString.toString();
    }

    // /**
    //  * @return String: 获取验证码原文的小写
    //  */
    // String getCaptchaLowerString() {
    //     assert (!generated);
    //     return captchaString.toString().toLowerCase();
    // }

    /**
     * @param string: 打算设置的字符生成范围
     */
    void setString(String string) {
        this.string = string;
    }
}

/**
 * 生成一张验证码的图片
 */
class CaptureImage {
    private String captcha = new StringGenerator().generate(); // 获取一个验证码;
    private Random random = new Random();

    BufferedImage captureImage(int width, int height) {
        return this.captureImage(width, height, Color.blue, Color.red, new Color(255, 150, 20));
    }

    /**
     * @param width:      生成的图片宽度
     * @param height:     生成的图片高度
     * @param firstColor: 第一个渐变色
     * @param lastColor:  第二个渐变色
     * @param fontColor:  字体颜色
     *
     * @return BufferedImage: 验证码图片
     */
    private BufferedImage captureImage(int width, int height, Color firstColor, Color lastColor, Color fontColor) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics(); // 创建图形
        int fontSize = height * 4 / 7;
        Font font = new Font("Helvetica", Font.BOLD, fontSize); //设置比较合适的字体大小
        graphics2D.setFont(font);

        GradientPaint gradientPaint = new GradientPaint(0, 0, firstColor, 0, height, lastColor);
        graphics2D.setPaint(gradientPaint); // 设置背景蓝红渐变

        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setColor(fontColor); // 设置字体颜色

        int x, y;
        int pr = width / captcha.length(); // 基准比例
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
    // 一个验证码
    private CaptureImage captureImage = new CaptureImage();
    private ImageIcon icon = new ImageIcon(captureImage.captureImage(200, 50));
    // 图片
    private JLabel picture = new JLabel();
    // 按钮
    private JButton submit = new SubmitButton("提交");
    private JButton refresh = new RefreshButton("看不清");
    // 输入框
    private JTextField input = new InputTextArea();

    //错误计数器
    private static int wrong = 0;

    private MainWindow(int width, int height) {
        super("验证码");
        new StringGenerator();
        setLayout(new FlowLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*图片域*/

        picture.setIcon(icon);
        add(picture);

        /*输入域*/
        JPanel inputPanel = new JPanel(new FlowLayout());

        // 添加按钮和输入框
        inputPanel.add(input);
        inputPanel.add(submit);
        inputPanel.add(refresh);
        add(inputPanel, BorderLayout.SOUTH);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // 显示在屏幕中心

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
                JOptionPane.showMessageDialog(null, "输入正确", "验证通过", JOptionPane.NO_OPTION);
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(null, "错误", "输入错误", JOptionPane.WARNING_MESSAGE);
                input.setText("");
                wrong += 1;
                if (wrong >= 3) {
                    JOptionPane.showMessageDialog(null, "错误3次", "错误次数过多", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            }
        }
    }


    private class SubmitButton extends JButton {
        SubmitButton(String title) {
            setText(title);
            setFont(new Font("微软雅黑", 0, 16));
            setSize(40, 20);

            addActionListener(new SubmitEvent());
        }
    }

    private class RefreshButton extends JButton {
        RefreshButton(String title) {
            setText(title);
            setFont(new Font("微软雅黑", 0, 16));
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
            setFont(new Font("微软雅黑", 0, 16));
            setColumns(6);
            addActionListener(new SubmitEvent());
        }
    }

    public static void main(String[] args) {
        new MainWindow(280, 140);
    }

}

