//2、编写程序，对于输入的字符进行分类，分类标准在程序运行时显示在屏幕上，分类后显示其类别。例如：分类标准为： 第1类 大写英文字母，第2类 小写英文字母，第3类 数字字符，第4类 其他符号；当输入值为1时，屏幕显示信息为： The values you entered are of third types.
//当输入大写字母A时，The values you entered are of first types.

import java.util.Scanner;

public class bunnrui {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String content = scanner.next();
        char[] chr = content.toCharArray();
        if (chr[0] <= 122 && chr[0] >= 97) {
            System.out.println("The values you entered are of second types.");
        } else if (chr[0] <= 90 && chr[0] >= 65) {
            System.out.println("The values you entered are of first types.");
        } else if (chr[0] <= 57 && chr[0] >= 48) {
            System.out.println("The values you entered are of third types.");
        } else {
            System.out.println("The values you entered are of fourth types.");
        }
    }


}
