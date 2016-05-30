import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;

class question2 {
    String a;
    ArrayList<String> d = new ArrayList<String>();

    void input() {
        Scanner scanner = new Scanner(System.in);
        this.a = scanner.nextLine();
    }

    void print_r() {
        for (int i = this.a.length() - 1; i >= 0; i--) {
            System.out.print(this.a.charAt(i));
        }
        System.out.print('\n');
    }

    void cal() {
        for (char i : this.a.toCharArray()) {
            if (65 <= (int) i && (int) i <= 90) {
                System.out.printf("%4d", (int) i);
            }
        }
        System.out.print("\n");
    }

    void n3() {
        String[] b = this.a.split(" ");
        int number = 0;
        for (String i : b) {
            if (i.matches("[a-z]+")) {
                number++;
                this.d.add(i);
            };
        }


        System.out.printf("有%d个小写单词\n",number);
    }
    void n4(){
        Collections.sort(this.d);
        System.out.println(this.d.toString());
    }
    void n5(){
        System.out.println(this.a.replaceAll("a","A"));
    }
}

public class kaoshimoni2 {
    public static void main(String[] args) {
        question2 q = new question2();
        //要求1:
        System.out.println("要求1:");
        q.input();
        q.print_r();
        //要求2:
        System.out.println("要求2:");
        q.cal();
        //要求3:
        System.out.println("要求3:");
        q.n3();
        //要求4:
        System.out.println("要求4:");
        q.n4();
        //要求5:
        System.out.println("要求5:");
        q.n5();
    }

}
