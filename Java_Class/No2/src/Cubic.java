/**
 * Created by enihsyou on 16/3/8.
 */

import java.text.MessageFormat;
import java.util.Scanner;

public class Cubic {
    public static void main(String[] args) {
        System.out.println("分别输入长宽高，输入q退出: ");
        System.out.println("例如: 1 2 3");
        System.out.print(">>> ");
        Scanner scanner = new Scanner(System.in);
//initialize some variables
        double height = 0;
        double width = 0;
        double length = 0;
        double volume = 0;
        int i = 0;
//type "q" to escape this loop
        while (!scanner.hasNext("q")) {
            double temp = 0;

            try {
                temp = Double.parseDouble(scanner.next());
                i++;
            } catch (Exception e) {
                System.out.print("需要一个数字");
                continue;
            }

            switch (i) {
                case 1:
                    height = temp;
                    break;
                case 2:
                    width = temp;
                    break;
                case 3:
                    length = temp;
                    break;
            }
            if (i == 3) {
                volume = height * width * length;
                i = 0;
                System.out.println(MessageFormat.format("长为{0} 宽为{1} 高为{2} 的长方体的体积为: ", height, width, length) +
                        volume);
            }
        }
    }
}

