import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.text.MessageFormat;

class Person {
    private String name;
    private int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    Person() {}

    void dispaly() {
        System.out.println(MessageFormat.format("姓名:{0}, 年龄:{1}", name, age));
    }
}

public class b05 {
    public static void main(String args[]) {
        Person a1 = new Person("abc", 100);
        a1.dispaly();
    }
}
