import java.util.ArrayList;

public class J_02 {
    private static ArrayList<Integer> prime(int range) {
        ArrayList<Integer> primes = new ArrayList<>();
        int[] a = new int[range + 1];
        for (int i = 2; i < Math.sqrt(range); i++) {
            if (a[i] == 0) {
                int j = 2;
                while (i * j < range + 1) a[i * j++] = 1;
            }
        }
        for (int i = 2; i < range + 1; i++) {
            if (a[i] != 1) primes.add(i);
        }
        return primes;
    }

    public static void main(String[] args) {
        ArrayList<Integer> primes = prime(10000);
        boolean changed = false;
        for (int i = 2000; i <= 10000; i += 2) {
            for (int a : primes) {
                for (int b : primes) {
                    if (a + b == i) {
                        changed = true;
                        break;
//                        if (a > b) break;
//                        System.out.println(MessageFormat.format(
//                                "{2,number,#} = {0,number,#} + {1,number,#}", a, b, i));
                    }
                }
                if (changed) break;
            }
        }
        if (!changed) {
            System.out.println("错误");
        } else {
            System.out.println("正确");
        }
    }
}

