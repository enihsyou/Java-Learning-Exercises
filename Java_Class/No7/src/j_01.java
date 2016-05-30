import java.util.Random;

public class J_01 {
    private static int[] generate(int[] a) {
        Random random = new Random();
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(100);
        }
        return a;
    }

    private static int[] selection_sort(int[] data) {
        int length = data.length;
        for (int i = 0; i < length; i++) {
            int index = i;
            for (int j = i + 1; j < length; j++) {
                if (data[j] < data[index]) index = j;
            }
            if (index == i) continue;
            int tmp = data[index];
            data[index] = data[i];
            data[i] = tmp;
        }
        return data;
    }

    public static void main(String[] args) {
        int[] a = selection_sort(generate(new int[20]));
        System.out.println("二十个数字的顺序是: ");
        for (int i : a) {
            System.out.printf("%4d", i);
        }
    }
}

