import javax.swing.*;
import java.sql.Time;
import java.util.*;
import java.util.Timer;

public class Han {
    private static class HanoiTower {
        private int Disks = 3;
        private long num = 0;

        HanoiTower(int a) {
            Disks = a;
        }

        void move() {
            moveDish(Disks, 'A', 'B', 'C');
        }

        void setDisks(int a) {
            Disks = a;
        }

        void setDisks() {
            try {
                int a = Integer.parseInt(JOptionPane.showInputDialog(null, "How many disks?"));
                if (a <= 0) {
                    throw new NumberFormatException();
                }
                setDisks(a);
            } catch (NumberFormatException e) {
                setDisks();
            }

        }

        void moveDish(int level, char from, char inter, char to) {

            if (level != 1) {
                moveDish(level - 1, from, to, inter);
                num += 1;
                System.out.println(String.format("%d From '%s' move dish No.%d to '%s'", num, from, level, to));
                moveDish(level - 1, inter, from, to);
            } else {
                num += 1;
                System.out.println(String.format("%d From '%s' move dish No.%d to '%s'", num, from, level, to));
            }
        }
    }

    public static void main(String[] args) {
        HanoiTower tower = new HanoiTower(3);
        tower.setDisks();
        long start = System.nanoTime();

        tower.move();
        long end = System.nanoTime();
        System.out.println((end - start) / 1000000.);
        System.out.println(tower.Disks + " " + tower.num);
    }
}
