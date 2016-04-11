//8.请在小于100000的整数内编程验证哥德巴赫猜想：任何大于2的整数可以表示为2个质数的和，例如：4=2+2，6=3+3，8=3+5.。。。。程序实现输入一个偶数，可以被分解成2个质数。

import java.util.Scanner;

public class Goldbachconjecture {
    static int[] prime() {
        int[] prime = new int[100000];
        int n = 0;
        for (int i = 2; i < 100000; i++) {
            prime[i] = i;
        }
        for (int i = 0; i < 100000; i++) {
            if (prime[i] == 0) {
                continue;
            }
            int k = 0;
            for (int j = i; j < 100000; k++) {
                j += i;
                if (j < 100000 && prime[j] != 0) {
                    prime[j] = 0;
                    continue;
                }

            }
        }
        int[] res = new int[9592];
        for (int i = 0; i < 100000; i++) {
            if (prime[i] != 0) {
                res[n++] = prime[i];
            }

        }
        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        int[] primes = prime();
        for (int i = 0; i < 9592; i++) {
            for (int j = 0; j < i; j++) {
                if (primes[i] + primes[j] == num) {
                    System.out.println(num + " = " + primes[i] + " + " + primes[j]);
                    break;
                }
            }

        }
    }
}
