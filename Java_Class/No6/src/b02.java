import java.text.MessageFormat;

class Vehicle {
    private float speed = 0;
    private double size = 0;
    private int interval = 1;
    private double coordinate;


    Vehicle(float speed, double size) {
        this.speed = speed;
        this.size = size;
        coordinate = 0;
    }

    Vehicle() {}

    void move() {
        this.coordinate += speed;
        System.out.println(MessageFormat.format("坐标: {0}, 速度: {1}, 尺寸: {2}", this.coordinate, this.speed, this.size));
    }

    void setSpeed(int speed) {
        this.speed = speed;
    }

    void speedUp() {
        this.speed += interval;
    }

    void speedDown() {
        this.speed -= interval;
    }
}

public class b02 {
    public static void main(String args[]) {
        Vehicle car;
        car = new Vehicle(10, 10);
        car.move();
        car.speedUp();
        car.move();
        car.speedDown();
        car.setSpeed(100);
        car.move();
    }
}
