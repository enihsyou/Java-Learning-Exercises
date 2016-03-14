/**
 * Created by enihsyou on 16/3/14.
 */
public class a2zASCII2 {
    public static void main(String[] args) {
        for (int i = 97; i < 97 + 26; i++) {
            String str;
            str = i + "\t" + Character.toString((char) i);
            System.out.println(str);
        }
    }
}
