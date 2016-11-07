import java.util.Date;
import java.util.Random;

class Pan {
    private final Random random;
    private int totalSold; //当前销售量
    private int currentForSell;//当前库存量
    private static final int MAX_CAPACITY = 3;//库存量
    private static final int MAX_TODAY_SOLD = 20; //最大销售量
    private boolean soldOut;

    Pan() {
        totalSold = 0;
        currentForSell = 1;
        soldOut = false;
        random = new Random();

        System.out.println("盘古开天辟地留下了一个蛋饼");
    }

    void makeOne() throws InterruptedException {
        Thread.sleep(random.nextInt(3000) + 1000);
        synchronized (this) {
            if (totalSold >= MAX_TODAY_SOLD) return;
            if (currentForSell < MAX_CAPACITY) {
                System.out.printf("%tH:%<tM:%<tS %s制作了一个，现在有%d个蛋饼了\n", new Date(), Thread.currentThread().getName(),
                        ++currentForSell);
            } else System.out.printf("%tH:%<tm:%<tS 等啊等，没人来买···\n", new Date());
        }
    }

    void soldOne() throws InterruptedException {
        Thread.sleep(random.nextInt(3000) + 1000);
        synchronized (this) {
            if (currentForSell > 0) {
                System.out.printf("%tH:%<tM:%<tS %s来买了一个，盘子里剩下%d个，已经卖出%d个\n", new Date(), Thread.currentThread().getName(),
                        --currentForSell, ++totalSold);
            } else System.out.printf("%tH:%<tm:%<tS %s来了没买到，饿着离开了\n", new Date(), Thread.currentThread().getName());
            if (totalSold >= MAX_TODAY_SOLD) soldOut = true;
        }
    }


    boolean isSoldOut() {
        return soldOut;
    }
}

class Maker implements Runnable {
    private Pan pan;

    Maker(Pan pan) {
        this.pan = pan;
        new Thread(this, "卖蛋饼的").start();
    }

    @Override
    public void run() {
        try {
            while (!pan.isSoldOut()) {
                pan.makeOne();
            }
            System.out.println("卖完收工！");
        } catch (InterruptedException e) {
            System.out.println("城管来了！不让卖了！");
        }
    }
}

class BaseBuyer implements Runnable {
    private Pan pan;

    BaseBuyer(Pan pan) {
        this.pan = pan;
        new Thread(this, "买蛋饼的").start();
    }

    @Override
    public void run() {
        try {
            while (!pan.isSoldOut()) {
                pan.soldOne();
            }
        } catch (InterruptedException e) {
            System.out.println("不让买了！");
        }
    }
}

public class PanCake {
    public static void main(String[] args) {
        Pan pan = new Pan();
        new Maker(pan);
        new BaseBuyer(pan);
    }
}
