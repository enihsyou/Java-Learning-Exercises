import java.text.MessageFormat;

public class J02 {
    static public void main(String[] args) {

        for (int i = 10; i < 100; i++) {
            for (int j = 10; j < 100; j++) {
                if (100 * i + j == (i + j) * (i + j)) {
                    System.out.println(MessageFormat.format("a:{0} b:{1} c:{2} d:{3}",
                            i / 10, i % 10, j / 10, j % 10));

                }
            }
        }
    }
}

