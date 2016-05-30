import java.util.Random;
import java.util.Scanner;

class question1 {
    private int[] a = new int[5];
    private int[] b = new int[10];
    private int[] c = new int[15];

    int[] input() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            a[i] = scanner.next().charAt(0);
        }
        return a;
    }

    void print(int[] a) {
        for (int i : a) {
            System.out.printf("%4d", i);
        }
        System.out.print("\n");
    }

    void combine() {
        int[] a = this.a;
        int[] b = this.b;
        System.arraycopy(a, 0, this.c, 0, a.length);
        System.arraycopy(b, 0, this.c, a.length, b.length);
    }

    void print2() {

        int number = 0;
        for (int i : c) {
            if (i % 3 == 0) {
                number++;
                System.out.printf("%4d", i);
            }
        }
        System.out.printf("  3的倍数的数字有%d个\n", number);
    }

    void print3() {
        int[] c = this.c;
        int last = c[0];
        System.out.printf("%4d", last);
        for (int i : c) {
            if (i != last) {
                System.out.printf("%4d", i);
                last = i;
            }
        }
    }


    int[] bubble_sort() {
        int[] d = new int[15];
        System.arraycopy(this.c, 0, d, 0, c.length);
//        int[] d = this.c;
        for (int i = 0; i < d.length; i++) {
            for (int j = i + 1; j < d.length; j++) {
                if (d[i] > d[j]) {
                    int tmp = d[i];
                    d[i] = d[j];
                    d[j] = tmp;
                }
            }
        }
        return d;
    }

    int[] generate() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            b[i] = random.nextInt(100);
        }
        return b;
    }
}

public class kaoshimoni1 {
    public static void main(String[] args) {
        question1 q = new question1();
        //要求1:
        System.out.println("要求1:");
        q.print(q.input());
        //要求2:
        q.generate();
        //要求3:
        System.out.println("要求3:");
        q.combine();
        q.print2();
        //要求4:
        System.out.println("要求4:");
        q.print(q.bubble_sort());
        //要求5:
        System.out.println("要求5:");
        q.print3();
    }
}
