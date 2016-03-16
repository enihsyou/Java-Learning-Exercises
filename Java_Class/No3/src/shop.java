import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by enihsyou on 16/3/14.
 * 依据输入商品名 单价 数量 打印输出单
 */
public class shop {


    static void addTannka(ArrayList tannka, Scanner scanner) {
        try {
            System.out.print("单价: ");
            tannka.add(Double.parseDouble(scanner.next()));
        } catch (Exception notANumber) {
            System.out.println("需要一个数字");
            addTannka(tannka, scanner);
        }
    }

    static void addRyou(ArrayList ryou, Scanner scanner) {
        try {
            System.out.print("数量: ");
            ryou.add(Integer.parseInt(scanner.next()));
        } catch (Exception notANumber) {
            System.out.println("需要一个整数");
            addRyou(ryou, scanner);
        }
    }

    public static void main(String[] args) {
        Scanner scanner;
        double total;
        int i;
        ArrayList name, tannka, ryou;

        name = new ArrayList();
        tannka = new ArrayList();
        ryou = new ArrayList();

        scanner = new Scanner(System.in);
        i = 0;
        total = 0;
        System.out.println("分别输入商品名，单价和数量。输入q输出结果");

        while (true) {
            System.out.print("商品名: ");
            if (scanner.hasNext("q")) {
                break;
            }
            name.add(scanner.next());

            addTannka(tannka, scanner);
            addRyou(ryou, scanner);

            //add to total
            total += (double) tannka.get(i) * (int) ryou.get(i);
            i++;
        }

        System.out.println();
        System.out.println("序号" + '\t' + "商品名" + '\t' + "单价" + '\t' + "数量" + '\t' + "小计");
        for (int j = 0; j < i; j++) {
            System.out.println(MessageFormat.format("No.{0}'\t'{1}'\t'{2}'\t'{3}'\t'{4}", j + 1, name.get(j),
                    tannka.get(j), ryou.get(j), (double) tannka.get(j) * (int) ryou.get(j)));
        }
        System.out.println();
        System.out.println(MessageFormat.format("总计: {0}", total));
    }
}