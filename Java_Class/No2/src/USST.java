import java.util.Random;

/**
 * Created by enihsyou on 16/3/11.
 */
public class USST {
    public static void main(String[] args) {
        String str = new String("上海理工大学");
//1
        System.out.println(str);
        System.out.println();
//2
        char[] strA = str.toCharArray();
        for (int i = 0; i < strA.length; i++) {
            System.out.println(strA[i]);
        }
        System.out.println();
//3
        for (int i = strA.length; i > 0; i--) {
            System.out.print(strA[i - 1]);
        }
    }
}
