import java.util.Random;

public class J_06 {
    private static int[][] generate() {
        Random random = new Random();
        int[][] a = new int[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                a[i][j] = random.nextInt(36);
            }
        }
        return a;
    }

    private static void print(int[][] a) {
        for (int[] i : a) {
            for (int j = 0; j < a.length; j++) {
                System.out.printf("%-3d", i[j]);
            }
            System.out.print('\n');
        }
        System.out.print('\n');
    }

    /**
     * 判断是否是该行最大的数字
     *
     * @param a   数组
     * @param i   第几行
     * @param num 数组点上的数字值
     *
     * @return bool 是否是该行最大的数字
     */
    private static boolean ismax(int[][] a, int i, int num) {

        for (int q : a[i]) {
            if (q > num) return false;
        }
        return true;
    }

    /**
     * 判断是否是该列最小的数字
     *
     * @param a   数组
     * @param i   第几列
     * @param num 数组点上的数字值
     *
     * @return bool 是否是该列最小的数字
     */
    private static boolean ismin(int[][] a, int i, int num) {
        for (int k = 0; k < a[i].length; k++) {
            if (a[k][i] < num) return false;
        }
        return true;
    }

    public static void main(String[] args) {

        boolean found = false;
        while (!found) {
            int[][] a = generate();
            print(a);
            for (int i = 0; i < a.length; i++) {//行循环
                for (int j = 0; j < a[i].length; j++) {//列循环
                    if (ismax(a, i, a[i][j]) && ismin(a, j, a[i][j])) {
                        System.out.printf("第%d行 第%d个 数字: %d 是鞍点\n", i + 1, j + 1, a[i][j]);
                        found = true;
                    }
                }
            }
        }
    }
}
