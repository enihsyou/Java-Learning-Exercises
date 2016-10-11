package kaoshimoni;

import java.io.*;

class kaoshi2

{

    String str;
    char strc[];
    int len;
    String strs[] = new String[100];
    int strslen;

    kaoshi2(String a) {
        str = a;
        strc = a.toCharArray();
        len = a.length();

        //System.out.println(str.substring(0, 2));
    }

    void nixu() {

        int i = 0;
        System.out.println("�����ַ�����");
        for (i = len - 1; i >= 0; i--)
            System.out.print(strc[i]);
        System.out.println();


    }

    void countcap() {

        int i = 0;
        int count = 0;
        System.out.println();
        for (i = 0; i <= len - 1; i++)
            if (strc[i] >= 'A' && strc[i] <= 'Z') {
                count++;
                System.out.print((int) strc[i] + "  ");
            }
        System.out.println();
        System.out.print("the cap char number is " + count);

    }

    void countchar() {
        int i = 0;
        int count = 0;
        int j = 0;
        for (i = 1; i <= len - 2; i++, j++) {
            if (strc[j] == ' ' && strc[i] != ' ' && (strc[i] >= 'a' && strc[i] <= 'z')) count++;
        }
        System.out.println();
        System.out.print("the cap char number is");
        System.out.println(count);


    }

    void cutstring() {
        int i = 0, j = 0;
        int endpos, startpos = 0;
        int temp = 0;

        for (i = 1; i <= len - 1; i++, j++) {
            if (strc[j] == ' ' && strc[i] != ' ') startpos = j + 1;

            if (strc[j] != ' ' && strc[i] == ' ') {
                endpos = i;

                if (strc[startpos] >= 'A' && strc[startpos] <= 'Z') continue;
                if (strc[endpos - 1] == ',') endpos--;

                strs[temp] = str.substring(startpos, endpos);
                temp++;
            }


        }

        strs[temp] = str.substring(startpos, j);

        strslen = temp;
        System.out.println();
        for (i = 0; i <= temp; i++)
            System.out.println(strs[i]);
    }

    void paixu() {
        int i, j;
        for (i = 0; i <= strslen; i++)
            for (j = 0; j <= strslen - 1; j++)
                if (strs[j].compareTo(strs[j + 1]) > 0) {
                    str = strs[j];
                    strs[j] = strs[j + 1];
                    strs[j + 1] = str;
                }
        System.out.println();
        System.out.println("the result is:");
        for (i = 0; i <= strslen; i++)
            System.out.print(strs[i] + " ");


    }


}


public class kaoshimoni2_answer {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        int i;
        String str;


        BufferedReader buf;
        buf = new BufferedReader(new InputStreamReader(System.in));
        str = buf.readLine();
        kaoshi2 kaoshibo = new kaoshi2(str);
        kaoshibo.nixu();
        kaoshibo.countcap();
        kaoshibo.countchar();
        kaoshibo.cutstring();
        kaoshibo.paixu();
    }

}

