import java.text.MessageFormat;
import java.util.Scanner;

/**
 * Created by enihsyou on 16/3/30.
 */
public class Piecewise {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextDouble()) {
            double tmp = scanner.nextDouble();
            if (tmp >= 10) {
                tmp = 3 * tmp - 11;
            } else if (tmp >= 1) {
                tmp = 2 * tmp - 1;
            }
            System.out.println(MessageFormat.format("{0,number}", tmp));
        }

    }
}
