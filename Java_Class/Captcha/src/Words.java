import javax.swing.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Words {

    public static void main(String[] args) {
        String text;
        int count = 0;
        text = JOptionPane.showInputDialog("输入英文文字");
        System.out.println(text);
        Pattern pattern = Pattern.compile("\\b([a-zA-Z]+?)\\b");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println(matcher.group());
            count += 1;
        }
        String message = String.format("原句:%n%s%n其中英文单词有%d个", text, count);
        JOptionPane.showMessageDialog(null, message);
    }
}
