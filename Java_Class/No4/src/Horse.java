import java.text.MessageFormat;

/**
 * Created by enihsyou on 16/4/4.
 */
public class Horse {
    public static void main(String[] args) {
        int count = 0;
        for (int small = 0; small <= 100; small++) {
            for (int mid = 0; mid <= 100 - small; mid++) {
                for (int big = 0; big <= 100 - small - mid; big++) {
                    if (small + 2 * mid + 3 * big == 100) {
                        count++;
                        System.out.println(MessageFormat.format(
                                "大马{0}匹，中马{1}匹，小马{2}匹", big, mid, small));
                    }
                }
            }
        }
        System.out.println(MessageFormat.format("总共{0}种方案", count));
    }
}