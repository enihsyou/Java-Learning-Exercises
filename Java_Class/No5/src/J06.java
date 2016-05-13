import java.text.MessageFormat;
import java.util.Scanner;

public class J06 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n = scanner.nextInt();
        double[] a = new double[50];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextDouble();
        }// Input numbers

        int goukaku = 0, fugoukaku = 0;
        double G1 = 0, G2 = 0;
        for (int i = 0; i < n; i++) {
            if (a[i] >= 60) {
                goukaku++;
                G1 += a[i];
            } else {
                fugoukaku++;
                G2 += a[i];
            }
        }
        System.out.println(MessageFormat.format("总人数：{0}\n及格人数：{1}\t平均分：{2}\n不及格人数：{3}\t平均分：{4}",
                n, goukaku, G1 / goukaku, fugoukaku, G2 / fugoukaku));
    }
}
