/**
 * Created by enihsyou on 16/3/14.
 */
public class squareIn10 {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            String str;
            str = Integer.toString(i) + "^2 = " + Integer.toString(i * i);
            System.out.println(str);
        }
    }
}
