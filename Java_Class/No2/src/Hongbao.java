/**
 * Created by enihsyou on 16/3/8.
 */

import java.util.Scanner;
import java.text.MessageFormat;
import java.text.DecimalFormat;

public class Hongbao {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String name;
        double sumNumber;
        int number;
        double averageNumber;

        System.out.println("什么红包: ");
        name = scanner.nextLine();

        System.out.println("多少钱: ");
        sumNumber = scanner.nextDouble();

        System.out.println("多少个红包: ");
        number = scanner.nextInt();
        averageNumber = sumNumber / number;

        System.out.println(MessageFormat.format("{0}红包总共{1}元", name, sumNumber));
        System.out.println(MessageFormat.format("每个红包: {0}元", decimalFormat.format(averageNumber)));

    }
}
