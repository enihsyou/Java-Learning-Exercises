import java.text.MessageFormat;
import java.util.IdentityHashMap;
import java.util.Scanner;

/**
 * Created by enihsyou on 16/3/30.
 */
public class Compare {
    static void in(Scanner scanner, double price[], int i) {
        try {
            price[i] = Double.parseDouble(scanner.next());
        } catch (Exception e) {
            System.out.println("需要数字:");
            in(scanner, price, i);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double price[] = new double[10];
        String names[] = new String[10];

        System.out.println("输入商品名:");
        String name = scanner.next();
        int i = 0;
        while (true) {
            if (i == 10) {
                break;
            }
            System.out.println("输入电商名:");
            names[i] = scanner.next();

            System.out.println("输入该电商价格:");
            in(scanner, price, i);
            i++;
        }
        double minNum = price[0];
        i = 0;
        for (int j = 0; j < 10; j++) {
            if (price[j] < minNum) {
                minNum = price[j];
                i = j;
            }
        }

        System.out.println(MessageFormat.format("{0}的最低价格在{1} {2,number}", name, names[i], price[i]));

    }
}
