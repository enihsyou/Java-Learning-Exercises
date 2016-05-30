import java.util.Random;

public class J_05 {
    private static int[] generate(int n) {
        Random random = new Random();
        int[] a = new int[n];
        while (n > 0) {
            int b = random.nextInt(50);
            boolean repeat = false;
            for (int k : a) {
                if (b == k) {
                    repeat = true;
                    break;
                }
            }
            if (repeat) continue;
            a[--n] = b;
        }
        return a;
    }

    private static void bubble_sort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[i]) {
                    int tmp = a[j];
                    a[j] = a[i];
                    a[i] = tmp;
                }
            }
        }
    }

    private static int sum(int[] a) {
        int sum = 0;
        for (int i : a) sum += i;
        return sum;
    }

    public static void main(String[] args) {
        int[] a = generate(20);
        bubble_sort(a);
        for (int i : a) {
            System.out.printf("%4d", i);
        }
        System.out.printf("\n平均值: %d", sum(a) / a.length);
    }
}
