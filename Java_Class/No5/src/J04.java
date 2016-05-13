import java.util.Scanner;

public class J04 {
    public static void main(String[] args) {
        double[] score = new double[8];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 8; i++) {
            score[i] = scanner.nextDouble();
        }

        for (int i = 0; i < 8; i++) {
            for (int j = i; j < 8; j++) {
                if (score[i] > score[j]) {
                    double temp = score[i];
                    score[i] = score[j];
                    score[j] = temp;
                }
            }
        }
        double sum = 0;
        for (int i = 1; i < 7; i++) {
            sum += score[i];
        }
        System.out.println("平均分：" + sum / 6);
    }
}
