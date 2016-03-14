/**
 * Created by enihsyou on 16/3/6.
 */

import java.util.Scanner;
//import java.lang.Character;

public class reverseWordsUsingStringArray {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.next();
//        System.out.print(inputString);
        int length = inputString.length();
        char[] reversedStringArray = new char[100]; //100 digits hope enough
//        for (int i = inputString.length(); i > 0; i--) {
//            reversedString = reversedString + "" + i;
//        }
        char[] StringArray = inputString.toCharArray();
//        System.out.print(StringArray);
        for (char x : StringArray) {
            reversedStringArray[length - 1] = x;
            length--;
        }
        for (char x : reversedStringArray) {
            if (x != '\0') {
                System.out.print(x);
            }
        }
    }
}
