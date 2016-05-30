import java.util.Random;

public class J_03 {
    private static int[][] generate() {
        Random random = new Random();
        int[][] a = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                a[i][j] = random.nextInt(16);
            }
        }
        return a;
    }

    private static void print(int[][] a) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("%-4d", a[i][j]);
            }
            System.out.print('\n');
        }
    }

    private static int sum(int[][] a) {
        int sum = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (0 < i && i < 3 && 0 < j && j < 3) continue;
                sum += a[i][j];
            }
        }
        System.out.printf("总和: %d", sum);
        return sum;
    }

    public static void main(String[] args) {
        int[][] a = generate();
        print(a);
        sum(a);
    }
}
