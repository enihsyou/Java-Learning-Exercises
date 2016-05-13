import java.util.*;

class A {
    int v = 100;
}

public class b01 {
    public static void main(String args[]) {
        A a = new A();
        Scanner intInput = new Scanner(System.in);
        int intInputValue = intInput.nextInt();
        if (intInputValue > a.v)
            System.out.println("你的输入大于这个值");
        if (intInputValue < a.v)
            System.out.println("你的输入小于这个值");
        if (intInputValue == a.v)
            System.out.println("猜测成功");
    }
}
