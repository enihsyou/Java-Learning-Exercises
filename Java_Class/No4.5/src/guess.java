//6.编写程序，模拟猜数游戏，由计算机随机生成1-100内的整数，然后由用户来猜这个数，计算机每次回答用户猜的大了，小了，答对了。根据用户猜的次数分别给出不同的提示文字。
import java.util.Random;
import java.util.Scanner;

public class guess {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int ran = random.nextInt(100) + 1;

        while (true) {
            int guess = scanner.nextInt();
            if (guess > ran) {
                System.out.println("大了");
            } else if (guess < ran) {
                System.out.println("小了");
            } else {
                System.out.println("答对了");
                break;
            }
        }
        System.out.println(ran);
    }
}

