/**
 * Created by enihsyou on 16/4/4.
 */
public class Number {
    public static void main(String[] args) {
        for (int i = 100; i < 1000; i++)
            if (i % 3 == 2 && i % 5 == 3 && i % 7 == 2)
                System.out.println(i);
    }

}
