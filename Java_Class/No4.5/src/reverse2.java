//4.输入10个整数，将前5个数与后5个数的位置互换并输出。

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/4/11.
 */
public class reverse2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList row = new ArrayList();
        ArrayList row2 = new ArrayList();

        for (int i = 0; i < 10; i++) {
            int next = scanner.nextInt();
            row.add(next);
//            if (i < 5) {
//                row.add(i + 5, next);
//            } else {
//                row.add(i - 5, next);
//            }
        }
        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                row2.add(row.get(i + 5));
            } else {
                row2.add(row.get(i - 5));
            }
        }
        for (int i = 0; i < 10; i++) {
            System.out.print(row2.get(i));
            System.out.print(' ');
        }
    }
}