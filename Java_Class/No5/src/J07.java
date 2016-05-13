import java.text.MessageFormat;
import java.util.Scanner;

public class J07 {
    private static double sumnum(double[] inp) {
        double sum = 0;
        for (double i : inp) sum += i;
        return sum;
    }

    public static void main(String[] args) {
        double[][] score = new double[15][7];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.println("输入第" + (i + 1) + "个同学的第" + (j + 1) + "次成绩");
                score[i][j] = scanner.nextDouble();
            }
        }//Input
        for (int i = 0; i < 15; i++) {
            System.out.print("No." + i + "\t");
            for (int j = 0; j < 7; j++) {
                System.out.print(score[i][j] + "\t");
            }
            System.out.print('\n');
        }//Output
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 15; j++) {
                for (int k = j; k < 15; k++) {
                    if (score[i][j] < score[i][k]) {
                        double tmp = score[i][j];
                        score[i][j] = score[i][k];
                        score[i][k] = tmp;
                    }
                }//Sort
            }
            System.out.print(MessageFormat.format(
                    "第{0}模块前三名成绩: {1} {2} {3}",
                    i + 1, score[i][0], score[i][1], score[i][2]));
        }
        double[] sum = new double[15];
        for (int i = 0; i < 15; i++) {
            sum[i] = sumnum(score[i]);
            for (int j = 0; j < 2; j++) {
                for (int k = j; k < 2; k++) {
                    if (sum[j] < sum[k]) {
                        double tmp = sum[j];
                        sum[j] = sum[k];
                        sum[k] = tmp;
                    }
                }//Sort
            }
            System.out.print(MessageFormat.format(
                    "总成绩前三名成绩: {0} {1} {2}",
                    sum[0], sum[1], sum[2]));
        }
    }
}
