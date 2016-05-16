/**
 * 5. Static关键字练习
 * 创建一个名称为StaticDemo的类，并声明一个静态变量和一个普通变量。
 * 对变量分别赋予10和5的初始值。在main()方法中输出变量值。
 */

class StaticDemo {
    static int a = 10;
    int b = 5;
}

public class J05 {
    public static void main(String args[]) {
        StaticDemo a = new StaticDemo();
        System.out.println(StaticDemo.a);
        System.out.println(a.b);
    }
}
