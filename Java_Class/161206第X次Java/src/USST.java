import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class USST {
    private static void do1() throws IOException {
        for (int i = 379; i > 0; i--) {
            URL url = new URL(
                    String.format("http://www.usst.edu.cn/s/1/t/517/p/2/i/%d/list.htm", i));
            Pattern pattern = Pattern.compile("<font color=''>(.+?)</font>");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            reader.lines().forEach(s -> {
                Matcher matcher = pattern.matcher(s);
                while (matcher.find()) System.out.println(matcher.group(1));
            });
        }
    }

    private static void do2() throws IOException {
        for (int i = 379; i > 0; i--) {
            Jsoup.connect(String.format("http://www.usst.edu.cn/s/1/t/517/p/2/i/%d/list.htm", i))
                    .get()
                    .getElementsByClass("columnStyle")
                    .select("font")
                    .forEach(element -> System.out.println(element.text()));
        }
    }

    public static void main(String[] args) throws IOException {
        do1();
        do2();
    }
}
