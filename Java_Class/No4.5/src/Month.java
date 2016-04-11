//5.P40   9
import java.util.Scanner;

public class Month {
    public static void main(String[] args) {
        System.out.println("输入月份的数字");
        Scanner scanner = new Scanner(System.in);
        String[] mon = {"January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"};
        int next = scanner.nextInt();
        if (next < 12 && 0 <= next) {
            System.out.println(mon[next - 1]);
        } else {
            System.out.println("月份的数字都不知道了啊");
        }
    }
}
