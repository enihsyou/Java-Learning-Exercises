//3.输入一组整数，以-1表示结束，将这组数以输入顺序的倒序方式输出。

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/4/11.
 */
public class reverse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList row = new ArrayList();
        int i = 0;
        while (true) {
            int next = scanner.nextInt();
            if (next != -1) {
                row.add(next);
            } else {
                break;
            }
        }
        for (int j = row.size() - 1; j >= 0; j--) {
            System.out.print(row.get(j));
            System.out.print(' ');
        }
    }
}
