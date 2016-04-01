import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Created by enihsyou on 16/3/16.
 */
public class changeOrder {
    static void test1(Scanner sca) {
        String list[] = new String[2];
        for (int i = 0; i < 2; i++) {
            list[1 - i] = sca.next();
        }

        for (String i : list) {
            System.out.print(i + ' ');
        }
        System.out.println();
    }

    static void test2(Scanner sca) {
        String inp1 = sca.next();
        String inp2 = sca.next();
        System.out.println(inp2 + ' ' + inp1);
    }

    static void test3(Scanner sca) {
        BigDecimal inp1 = sca.nextBigDecimal();
        BigDecimal inp2 = sca.nextBigDecimal();
        BigDecimal tmp;
        tmp = inp1;
        inp1 = inp2;
        inp2 = tmp;

        System.out.println(inp1.toString() + ' ' + inp2.toString());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        test1(scanner);
        test2(scanner);
        test3(scanner);
    }
}
