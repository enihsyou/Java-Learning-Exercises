/**
 * 1．实验指导P59页
 * 编程题 2
 */


class Rectangle {
    private double a;
    private double b;
    private double preimeter;
    private double area;

    Rectangle() {
        this(0, 0);
    }

    Rectangle(int a, int b) {
        this.a = a;
        this.a = b;
        this.area = a * b;
        this.preimeter = 2 * (a + b);
    }

    void perimeter() {
        System.out.println(this.preimeter);
    }

    void area() {
        System.out.println(this.area);
    }

}

public class J01_2 {
    public static void main(String args[]) {
        Rectangle a = new Rectangle();
        Rectangle b = new Rectangle(3,2);
        a.perimeter();a.area();
        b.perimeter();b.area();
    }
}
