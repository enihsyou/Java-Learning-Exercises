/**
 * Created by enihsyou on 16/3/14.
 */

import java.text.MessageFormat;
import java.util.Scanner;

public class sumaverage3 {
    public static void main(String[] args) {
        Scanner scanner;

        double sum = 0;
//        double ave = 0;
        double inputNumber[];
        int i = 0;

        scanner = new Scanner(System.in);
        inputNumber = new double[3];

        while (!scanner.hasNext("q")) {
            double temp;

            try {
                temp = Double.parseDouble(scanner.next());
                inputNumber[i] = temp;
                i++;
                sum += temp;
            } catch (Exception notANumber) {
                System.out.println("需要一个数字");
                continue;
            }

//            switch (i) {
//                case 1:
//                    sum += temp;
//                    break;
//                case 2:
//                    sum += temp;
//                    break;
//                case 3:
//                    sum += temp;
//                    break;
//            }
            if (i == 3) {
//                ave = sum / 3;
                System.out.println(MessageFormat.format("输入: {0} {1} {2}\n总和: {3} 平均值: {4} ", inputNumber[0],
                        inputNumber[1], inputNumber[2], sum, sum / 3));
                i = 0;
                sum = 0;
//                ave = 0;
            }
        }
    }
}
