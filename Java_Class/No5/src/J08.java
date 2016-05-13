import java.util.Scanner;

public class J08 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str1 = scanner.next();
        String str2 = scanner.next();
        for (int i = 0; i <= str1.length() - str2.length(); i++) {
            String tmp = str1.substring(i, i + str2.length());
            if (tmp.equals(str2)) {
                System.out.println("Yes");
            }
        }
    }
}