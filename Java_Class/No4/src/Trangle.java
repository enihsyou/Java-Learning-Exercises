import com.sun.org.apache.xpath.internal.operations.And;

import java.util.Scanner;

/**
 * Created by enihsyou on 16/3/30.
 */
public class Trangle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double num[] = new double[3];
        int i = 0;
        while (scanner.hasNextDouble()) {
            if (i == 2) {
                break;
            }
            num[i] = scanner.nextDouble();
            i++;
        }
        if (num[0] * num[0] + num[1] * num[1] > num[2] * num[2]) {
            System.out.println(num[0] * num[1] / 2);
        } else if (
                num[2] * num[2] + num[1] * num[1] > num[0] * num[0]) {
            System.out.println(num[2] * num[1] / 2);
        } else if (
                num[2] * num[2] + num[0] * num[0] > num[1] * num[1]) {
            System.out.println(num[0] * num[2] / 2);
        } else {
            System.out.println("不是直角三角形");
        }
    }
}