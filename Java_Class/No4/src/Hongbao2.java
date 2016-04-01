/**
 * Created by enihsyou on 16/3/30.
 */

import java.util.Scanner;
import java.text.MessageFormat;
import java.text.DecimalFormat;

public class Hongbao2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        switch (scanner.nextInt()) {
            case 1:
                String name;
                double sumNumber;
                int number = 0;
                double averageNumber;

                System.out.println("多少钱: ");
                sumNumber = scanner.nextDouble();
                System.out.println("多少个红包: ");
                number = scanner.nextInt();

                averageNumber = sumNumber / number;

                System.out.println(MessageFormat.format("红包总共{0}元", sumNumber));
                System.out.println(MessageFormat.format("每个红包: {0}元", decimalFormat.format(averageNumber)));

                break;
            case 2:
                System.out.println("多少钱: ");
                sumNumber = scanner.nextDouble();
                double money = sumNumber;
                System.out.println("多少个红包: ");
                number = scanner.nextInt();
                for (int i = 0; i < number; i++) {
                    int a = (int) (Math.random() * number * 10 * (money / sumNumber));
                    if (i == number - 1) {
                        a = (int) money;

                    }
                    System.out.println(MessageFormat.format("红包{0}: {1}元", i, decimalFormat.format(a)));

                    money -= a;
                }
                break;
            case 3:
                System.out.println("输入口令: ");
                String cipher = scanner.next();
                System.out.println("多少钱: ");
                sumNumber = scanner.nextDouble();
                System.out.println("输入口令: ");
                if (scanner.next().equals(cipher)) {
                    System.out.println(MessageFormat.format("口令正确！红包: {0}元", sumNumber));

                } else {
                    System.out.println("口令错误！");
                }
                break;

        }


    }
}
