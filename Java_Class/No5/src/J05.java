import java.text.MessageFormat;
import java.util.Scanner;

public class J05 {
    static void print(int i, String str) {
        for (int j = i; j > 0; j--) {
            System.out.print(str);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char inp = scanner.next().toCharArray()[0];
        System.out.println(MessageFormat.format(" {0}{0}   {0}{0} \n" +
                "{0}  {0} {0}  {0}\n" +
                "{0}   {0}   {0}", inp));
        for (int i = 1; i < 5; i++) {
            print(i, " ");
            System.out.print(inp);
            print(7 - 2 * i, " ");
            if (i != 4) {
                System.out.print(inp);
            }
            print(i, " ");
            System.out.print('\n');
        }
    }
}