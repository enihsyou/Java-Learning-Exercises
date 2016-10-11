package kaoshimoni;

import java.io.*;

class kaoshi

{

    int data1[] = new int[100];
    int data2[] = new int[100];
    int data3[] = new int[100];
    static int n;

    void shuchu1() {
        int i;
        for (i = 0; i <= 4; i++)
            System.out.println(data1[i] + ",");
    }

    void create2() {

        int i;
        for (i = 0; i <= 9; i++) {
            data2[i] = (int) (Math.random() * 1000) % 100 + 1;
        }

    }

    void shuchu2() {
        int i;
        for (i = 0; i <= 9; i++)
            System.out.println(data2[i] + ",");
    }


    void hebing() {
        int i;
        int count = 0;

        for (i = 0; i <= 4; i++)
            data3[i] = data1[i];

        for (i = 5; i <= 14; i++)
            data3[i] = data2[i - 5];

        System.out.println("----------");

        for (i = 0; i <= 14; i++)
            if (data3[i] % 3 == 0) {
                System.out.println(data3[i] + ",");
                count++;
            }
        System.out.println(count);

        n = 14;
    }


    void paixu() {
        int t;
        int i, j;

        for (i = 0; i < n; i++)
            for (j = 0; j <= n - i - 1; j++)
                if (data3[j] > data3[j + 1]) {
                    t = data3[j];
                    data3[j] = data3[j + 1];
                    data3[j + 1] = t;
                }


    }


    void quchong() {
        int i = 0, j = 0;
        for (i = 0; i <= n - 1; i++)
            if (data3[i] == data3[i + 1])
            //�ظ���ɾ��
            {
                shanchu(i);
            }

    }

    int shanchu(int k) {
        int i = 0;


        for (i = k; i <= n - 1; i++)
            data3[i] = data3[i + 1];

        n--;

        return 1;
    }

    void display() {
        int i;
        System.out.println();
        System.out.println("----------");
        for (i = 0; i <= n; i++)
            System.out.print(data3[i] + ",");


    }
}


public class kaoshimoni1_answer {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        int i;
        String str;
        kaoshi kaoshibo = new kaoshi();

        BufferedReader buf;
        buf = new BufferedReader(new InputStreamReader(System.in));

        for (i = 0; i <= 4; i++) {
            str = buf.readLine();
            kaoshibo.data1[i] = (int) str.charAt(0);
        }

        kaoshibo.shuchu1();
        kaoshibo.create2();
        System.out.println("---------");
        kaoshibo.shuchu2();

        kaoshibo.hebing();
        kaoshibo.display();
        kaoshibo.paixu();
        kaoshibo.display();
        kaoshibo.quchong();
        kaoshibo.display();
    }

}
