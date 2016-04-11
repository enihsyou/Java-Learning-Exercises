//1、编写程序，实现抢口令红包，预先规定红包的口令，清屏幕后再输入希望口令，口令正确可以开红包，红包的内容可以自己规定。
import java.util.Scanner;
import java.text.MessageFormat;

public class HongBao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入口令: ");
        String cipher = scanner.next();
        System.out.println("输入内容: ");
        String content = scanner.next();
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
        System.out.println("输入口令: ");
        if (scanner.next().equals(cipher)) {
            System.out.println(MessageFormat.format("口令正确！红包内容: \n{0}", content));
        } else {
            System.out.println("口令错误！");
        }
    }
}
