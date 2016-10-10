/**
 * 4. 构造方法与重载练习：
 * 为“无名的粉”写一个类：class WuMingFen 要求：
 * a.有三个属性：面码:String theMa  粉的分量(两)：int quantity
 * 是否带汤：boolean likeSoup
 * b.写一个构造方法，以便于简化初始化过程，如：
 * WuMingFen f1 = new WuMingFen("牛肉",3,true);
 * c.重载构造方法，使得初始化过程可以多样化：
 * WuMingFen f2 = new WuMingFen("牛肉",2);
 * d.如何使得下列语句构造出来的粉对象是酸辣面码、2两、带汤的？
 * WuMingFen f3 = new WuMingFen();
 * e.写一个普通方法：check()，用于查看粉是否符合要求。即：将对象的三个属性打印在控制台上。
 */


class WuMingFen {
    private String theMa;
    private int quantity;
    private boolean likeSoup;

    WuMingFen() {
        this("酸辣", 2, true);
    }

    WuMingFen(String a, int b) {
        this(a, b, false);
    }

    WuMingFen(String a, int b, boolean c) {
        this.theMa = a;
        this.quantity = b;
        this.likeSoup = c;
    }

    void check() {
        System.out.println(this.theMa);
        System.out.println(this.quantity);
        System.out.println(this.likeSoup);
    }
}

public class J04 {

}
