/**
 * Created by enihsyou on 16/3/30.
 */

import java.util.Random;
import java.util.Scanner;
import java.text.MessageFormat;
import java.text.DecimalFormat;

public class Hongbao2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        System.out.println("输入1: 一般红包\n输入2: 拼手气红包\n输入3: 口令红包");
        switch (scanner.nextInt()) {
            case 1:
                double sumNumber;
                int number;
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
                int total = (int) (scanner.nextDouble() * 100); //总数
                System.out.println("多少个红包: ");
                number = scanner.nextInt(); //个数
                int money[] = new int[number];
                Random random = new Random();

                System.out.println();
                for (int i = 0; i < total; i++) {
                    int choice = (int)(10000000*random.nextFloat()) % number;
                    random.setSeed(random.nextLong());
                    money[choice] += 1;
                }
                for (int i = 0; i < number; i++) {
                    System.out.println(MessageFormat.format("红包{0}: {1}元", i + 1, decimalFormat.format(money[i] / 100.0)));
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
