import javax.swing.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Words {

    public static void main(String[] args) {
        String text;
        int count = 0;
        text = JOptionPane.showInputDialog("����Ӣ������");
        System.out.println(text);
        Pattern pattern = Pattern.compile("\\b([a-zA-Z]+?)\\b");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println(matcher.group());
            count += 1;
        }
        String message = String.format("ԭ��:%n%s%n����Ӣ�ĵ�����%d��", text, count);
        JOptionPane.showMessageDialog(null, message);
    }
}
