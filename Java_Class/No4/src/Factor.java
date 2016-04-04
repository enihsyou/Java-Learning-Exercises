import java.util.Scanner;

/**
 * Created by enihsyou on 16/4/4.
 */
public class Factor {
    public static void main(String[] args) {
        int i = 2;
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            double next = scanner.nextInt();
            String string;
            while (i <= next) {
                double mod = next / i;
                if (mod - (int) mod != 0) {
                    i++;
                    continue;
                }

                System.out.print(i);
                next = mod;
                if (next != 1){
                    System.out.print("*");
                }
            }
        }
    }
}
