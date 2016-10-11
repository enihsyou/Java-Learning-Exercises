/*
 * Author: 黄春翔
 * ID: 1517440121
*/

import java.math.BigInteger;
import java.util.Scanner;

public class Reduce {
    private Scanner scanner = new Scanner(System.in);
    private BigInteger up = BigInteger.ZERO;
    private BigInteger down = BigInteger.ONE;

    private Reduce() throws Exception {
        setUp();
        setDown();
    }

    private void setUp() throws Exception {
        System.out.print("输入分子\n>>>");
        String input = scanner.next();
        up = new BigInteger(input);
    }

    private void setDown() throws Exception {
        System.out.print("输入分母\n>>>");
        String input = scanner.next();
        down = new BigInteger(input);
    }

    private String reducte() {
        BigInteger gcd = down.gcd(up);
        up = up.divide(gcd);
        down = down.divide(gcd);
        return String.format("%s/%s", up, down);
    }

    public static void main(String[] args) throws Exception {
        Reduce reducte = new Reduce();
        System.out.format("%s/%s=%s%n", reducte.up, reducte.down, reducte.reducte());
    }
}
