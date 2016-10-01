/*
 * Author: 黄春翔
 * ID: 1517440121
*/

import java.math.BigInteger;
import java.util.Scanner;

public class GCD {
    private BigInteger numberOne = BigInteger.ZERO;
    private BigInteger numberTwo = BigInteger.ONE;

    private Scanner scanner = new Scanner(System.in);

    private GCD() throws Exception {
        setNumberOne();
        setNumberTwo();
    }

    private void setNumberOne() throws Exception {
        System.out.print("输入第一个数字\n>>>");
        String input = scanner.next();
        numberOne = new BigInteger(input);
    }

    private void setNumberTwo() throws Exception {
        System.out.print("输入第二个数字\n>>>");
        String input = scanner.next();
        numberTwo = new BigInteger(input);
    }

    private BigInteger chopDown() {
        while (!numberTwo.equals(BigInteger.ZERO)) {
            BigInteger mod = numberOne.remainder(numberTwo);
            numberOne = numberTwo;
            numberTwo = mod;
        }
        return numberOne;
    }

    public static void main(String[] args) throws Exception {
        //BigInteger.gcd();

        GCD gcd = new GCD();
        System.out.format("'%s'与'%s'的最大公约数（GCD)为'%s'%n", gcd.numberOne, gcd.numberTwo, gcd.chopDown());
    }
}
