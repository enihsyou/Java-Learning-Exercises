/**
 * 1．实验指导P59页
 * 编程题 1
 */


import java.text.MessageFormat;
import java.util.Date;

class Person {
    private String name;
    private Date birth;
    private boolean otoko;

    Person(String name, Date birth, boolean otoko) {
        this.name = name;
        this.birth = birth;
        this.otoko = otoko;
    }

    Person() {
        this.name = "";
        this.birth = new Date();
        this.otoko = false;
    }

    void print() {
        System.out.println("姓名：" + this.name);
        System.out.println(MessageFormat.format("出生日期：{0,date}", this.birth));
        if (this.otoko) {
            System.out.println("性别：男");
        } else {
            System.out.println("性别：女");
        }

    }
}

public class J01_1 {
    public static void main(String args[]) {
        Person a = new Person();
        Person c = new Person();
        a.print();
        c.print();
    }
}
