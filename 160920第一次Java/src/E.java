/*
 * Author: 黄春翔
 * ID: 1517440121
*/

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;


class Factorial {
    private int input = 0;
    private BigInteger result = BigInteger.ONE;

    Factorial(int input) throws Exception {
        if (input < 0) {
            throw new Exception("负数阶乘未实现");
        }
        this.input = input;
    }

    BigInteger calculate() {
        for (int i = 2; i <= input; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public String toString() {
        return calculate().toString();
    }
}

public class E {
    private int n = 0;
    private Factorial down;
    private Scanner scanner = new Scanner(System.in);
    private BigDecimal result = BigDecimal.ZERO;

    E() throws Exception {
        inputPrecession();
    }

    private void inputPrecession() throws Exception {
        System.out.print("输入精度\n>>>");
        n = scanner.nextInt();
    }

    private BigDecimal calculate() throws Exception {
        int a = 0;
        while (true) {
            down = new Factorial(a);//分母部分 阶乘数字
            /*整个分数*/
            BigDecimal part = new BigDecimal(BigInteger.valueOf(a).divide(down.calculate()));
            result = result.add(part);
            if (result.precision() >= n) {
                break;
            } else {
                a += 1;
                System.out.println(a);
                System.out.println(part);
                System.out.println(result);
                System.out.println(result.precision());
                System.out.println();

            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        E e = new E();
        System.out.print(e.calculate());
    }


}
