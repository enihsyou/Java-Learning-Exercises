/**
 * Created by enihsyou on 16/3/14.
 */
public class sqrtIn10 {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            String str;
            str = "√(" + Integer.toString(i) + ") = " + Double.toString(Math.sqrt(i));
            System.out.println(str);
        }
    }
}
