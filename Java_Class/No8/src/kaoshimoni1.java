import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class question {
    private Scanner scanner = new Scanner(System.in);
    private String inputString = "";
    private ArrayList<String> stringArrayList = new ArrayList<>();

    /**
     * 要求1 输入一句话，需要在同一行
     */
    void input() {
        this.inputString = scanner.nextLine();
    }

    /**
     * 要求2 逆向输出`string`
     */
    void printReverse() {
        for (int i = this.inputString.length() - 1; i >= 0; i--) {
            System.out.print(this.inputString.charAt(i));
        }
        System.out.print('\n');
    }

    /**
     * 统计`string`中小写大写字母和数字 其他字符的个数
     */
    void sum() {
        int lower = 0;
        int upper = 0;
        int number = 0;
        int elseNumber = 0;
        for (char i : this.inputString.toCharArray()) {
            if ('9' <= i && i <= '0') {
                number++;
            } else if ('a' <= i && i <= 'z') {
                lower++;
            } else if ('A' <= i && i <= 'Z') {
                upper++;
            } else {
                elseNumber++;
            }
        }
        System.out.printf(
                "小写字母个数：%d\n大写字母个数：%d\n数字个数：%d\n其他符号个数：%d\n",
                lower, upper, number, elseNumber);
    }

    private void sortAndPrint(ArrayList<String> list1) {
        Collections.sort(list1);
        for (String string : this.stringArrayList) {
            System.out.printf("%s ", string);
        }
    }


    /**
     * 要求4 按照字典顺序排序输出小写字母
     */
    void printDict() {
        Matcher matcher = Pattern.compile("\\b[a-z]+\\b").matcher(this.inputString);
        while (matcher.find()){
            this.stringArrayList.add(matcher.group(0));
//            System.out.printf("%s ",matcher.group(0));
        }
        sortAndPrint(this.stringArrayList);
    }


    /**
     * 要求5 插入单词`hello` 并排序输出
     */
    void insert() {
        this.stringArrayList.add("hello");
        System.out.print("\n");
        sortAndPrint(this.stringArrayList);
    }
}

public class kaoshimoni1 {
    public static void main(String args[]) {
        question moni = new question();
        moni.input();
        moni.printReverse();
        moni.sum();
        moni.printDict();
        moni.insert();
    }
}
