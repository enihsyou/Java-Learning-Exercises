import java.util.Scanner;

class Student {
    private double score;
    private static int[] grade = new int[6];

    Student(double score) {
        this.score = score;
        grade();
    }

    private void grade() throws IndexOutOfBoundsException {
        if (score > 100) throw new IndexOutOfBoundsException();
        int temp = (int) Math.floor(score / 10 - 5);
        if (temp >= -5 && temp < 0) {
            temp = 0;
        }
        grade[temp] += 1;
    }

    static String print() {
        return String.format("100分\t%d人%n90-99分\t%d人%n80-89分\t%d人%n70-79分\t%d人%n60-69分\t%d人%n不及格\t%d人",
                grade[5], grade[4], grade[3], grade[2], grade[1], grade[0]);

    }
}

public class E02 {
    private static Scanner scanner = new Scanner(System.in);

    private static void input(int i) {
        try {
            System.out.format("请输入第%d个学生的成绩：", i);
            new Student(scanner.nextDouble());

        } catch (IndexOutOfBoundsException e) {
            input(i);
        }
    }

    public static void main(String[] args) {
        System.out.println("学生总数：");
        int sum = scanner.nextInt();
        for (int i = 1; i <= sum; i++) {
            input(i);
        }
        System.out.println(Student.print());
    }

}
