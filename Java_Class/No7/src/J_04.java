public class J_04 {
    private static void next(int[] a) {
        int i = a.length - 1;
        while (a[i] == 0) i--;
        for (i++; i > 0; i--) {
            a[i] += a[i - 1];
        }
    }

    public static void main(String[] args) {
        int[] a = new int[10];
        a[0] = 1;

        for (int i = 0; i < 8; i++) {
            for (int j : a) {
                if (j == 0) break;
                System.out.printf("%3d", j);
            }
            next(a);
            System.out.printf("\n");
        }
    }
}
