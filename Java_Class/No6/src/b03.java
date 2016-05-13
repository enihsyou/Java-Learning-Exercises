import java.text.MessageFormat;

class MyTime {
    private int hour, minute, second;

    MyTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    MyTime() {}

    void display() {
        System.out.println(MessageFormat.format("{0,number,00}:{1,number,00}:{2,number,00}", hour, minute, second));
    }

    void addSecond(int sec) {
        this.second += sec;
    }

    void addMinute(int min) {
        this.minute += min;
    }

    void addHour(int hou) {
        this.hour += hou;
    }

    void subSecond(int sec) {
        this.second -= sec;
    }

    void subMinute(int min) {
        this.minute -= min;
    }

    void subHour(int hou) {
        this.hour -= hou;

    }

}

public class b03 {
    public static void main(String args[]) {
        MyTime a;
        a = new MyTime(1, 2, 3);
        a.display();
    }
}
