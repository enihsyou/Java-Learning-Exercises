import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class E04 {
    private static int alphabet = 0, space = 0, num = 0;

    private static class AlphaBet {
        private static Pattern alphabetP = Pattern.compile("[a-zA-Z]");

        static void count(String input) {
            Matcher m = alphabetP.matcher(input);
            while (m.find()) {
                alphabet += 1;
            }
        }
    }

    private static class Number {
        static private Pattern NumberP = Pattern.compile("\\d");

        static void count(String input) {
            Matcher m = NumberP.matcher(input);
            while (m.find()) {
                num += 1;
            }
        }
    }

    private static class Space {
        static private Pattern SpaceP = Pattern.compile(" ");

        static void count(String input) {
            Matcher m = SpaceP.matcher(input);
            while (m.find()) {
                space += 1;
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入要统计的文本: ");
        String str = buf.readLine();

        Space.count(str);
        AlphaBet.count(str);
        Number.count(str);

//        for (char i : str.toCharArray()) {
//            if (i >= 97 & i <= 122 | 65 <= i & i <= 90) {
//                alphabet += 1;
//            } else if (48 <= i & i <= 57) {
//                num += 1;
//            } else if (i == 32) {
//                space += 1;
//            }
//        }
        System.out.format("字母有%d个 空格有%d个 数字有%d个", alphabet, space, num);
    }
}
