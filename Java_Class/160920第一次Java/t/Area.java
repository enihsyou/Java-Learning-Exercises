import java.util.Scanner;

abstract class Object {
    String name;
    double area;
    double PI = Math.PI;

    abstract double calArea();

    public abstract String toString();

    String getArea() {
        return String.format("图形'%s'的面积为'%.3f'%n", this, calArea());
    }
}

class Circle extends Object {
    private double radius;

    Circle(double radius) {
        setAttr(radius);
        name = "圆";
    }

    Circle() {
        new Circle(0);
    }

    private void setAttr(double radius) {
        try {
            if (radius < 0) {
                throw new Exception("输入的半径为负数");
            } else {
                this.radius = radius;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    double calArea() {
        area = radius * radius * PI;
        return area;
    }


    public String toString() {
        return name;
    }
}

class Rectangle extends Object {
    private double length;
    private double width;

    Rectangle() {
        new Rectangle(0, 0);
    }

    Rectangle(double a) {
        new Rectangle(a, a);
    }

    Rectangle(double length, double width) {
        setAttr(length, width);
        name = "矩形";
    }

    private void setAttr(double length, double width) {
        try {
            if (length < 0 || width < 0) {
                throw new Exception("输入的值包含负数");
            } else {
                this.length = length;
                this.width = width;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public double calArea() {
        area = length * width;
        return area;
    }

    public String toString() {
        return name;
    }
}

public class Area {
    private static Object getChoice(int args) throws Exception {
        switch (args) {
            case 1:

                return new Circle(0);
            case 2:
                return new Rectangle(0);
            default:
                throw new Exception("错误的输入");
        }
    }

    public static void main(String[] args) {
        System.out.print(String.format("1. 圆%n2. 矩形%n>>> "));
        Scanner scanner = new Scanner(System.in);
        try {
            Object object = getChoice(scanner.nextInt());
            System.out.println(object.getArea());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
