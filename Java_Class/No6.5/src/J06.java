/**
 * 6..构造方法与重载练习
 * 定义一个网络用户类，要处理的信息有用户ID、用户密码、email地址。
 * 在建立类的实例时，把以上三个信息都作为构造函数的参数输入，
 * 其中用户ID和用户密码时必须的，
 * 缺省的email地址是用户ID加上字符串"@gameschool.com"
 */

class User {
    private String id;
    private String password;
    private String email;

    User() {
        this.id = "";
        this.password = "";
        this.email = "";
    }

    User(String a, String b) {
        this(a, b, a + "@gameschool.com");
    }

    User(String a, int b) {
        this(a, Integer.toString(b), a + "@gameschool.com");
    }

    User(String a, String b, String c) {
        this.id = a;
        this.password = b;
        this.email = c;
    }

    void print() {
        System.out.println(this.id);
        System.out.println(this.password);
        System.out.println(this.email);
    }
}


public class J06 {
    public static void main(String args[]) {
        User alpha = new User();
        User beta = new User("beta", 123);
        User gamma = new User("gamma", "123aew", "123@234.com");

        alpha.print();
        beta.print();
        gamma.print();
    }
}

