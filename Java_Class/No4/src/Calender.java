import java.util.Scanner;

/**
 * Created by enihsyou on 16/4/4.
 */
public class Calender {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("正确输入日期 如9月21日 输入0921");
        String string = scanner.next();
        int day = Integer.parseInt(string.substring(2));
        int month = Integer.parseInt((string.substring(0, 2))) - 1;
        int count = 0;
        while (month > 0) {
            switch (month) {
                case 12:
                case 10:
                case 8:
                case 7:
                case 5:
                case 3:
                case 1: {
                    count += 31;
                    break;
                }
                case 11:
                case 9:
                case 6:
                case 4: {
                    count += 30;

                    break;
                }
                case 2: {
                    count += 28;

                    break;
                }
            }
            month--;
        }
        count += day + 2;
        switch ((count) % 7) {
            case 0: {
                System.out.println("星期日");
                break;
            }
            case 1: {
                System.out.println("星期一");
                break;
            }
            case 2: {
                System.out.println("星期二");
                break;
            }
            case 3: {
                System.out.println("星期三");
                break;
            }
            case 4: {
                System.out.println("星期四");
                break;
            }
            case 5: {
                System.out.println("星期五");
                break;
            }
            case 6: {
                System.out.println("星期六");
                break;
            }
        }
    }
}
