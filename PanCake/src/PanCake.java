import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class Pan {
    private final Random random;
    private AtomicInteger totalSold; //当前销售量
    private AtomicInteger currentForSell;//当前库存量
    private static final int MAX_CAPACITY = 3;//库存量
    private static final int MAX_TODAY_SOLD = 20; //最大销售量
    private boolean soldOut;

    Pan() {
        totalSold = new AtomicInteger(0);
        currentForSell = new AtomicInteger(1);
        soldOut = false;
        random = new Random();

        System.out.println("盘古开天辟地留下了一个蛋饼");
    }

    void makeOne() throws InterruptedException {
        int waitTime = random.nextInt(1000) + 1000;
        Thread.sleep(waitTime);
        if (totalSold.get() >= MAX_TODAY_SOLD) return;
        if (currentForSell.get() < MAX_CAPACITY) {
            System.out.printf("过了%ds %s制作了一个，现在有%d个蛋饼了\n", waitTime / 1000, Thread.currentThread().getName(),
                    currentForSell.incrementAndGet());
        } else {
            System.out.printf("等了%ds，没人来买···\n", waitTime / 1000);
        }
    }

    void soldOne() throws InterruptedException {
        int waitTime = random.nextInt(3000) + 1000;
        Thread.sleep(waitTime);
        if (currentForSell.get() > 0) {
            System.out.printf("过了%ds %s来买了一个，盘子里剩下%d个，已经卖出%d个\n", waitTime / 1000, Thread.currentThread().getName(),
                    currentForSell.decrementAndGet(), totalSold.incrementAndGet());
        } else System.out.printf("%s白等了%ds，饿着离开了\n", Thread.currentThread().getName(), waitTime / 1000);
        if (totalSold.get() >= MAX_TODAY_SOLD) soldOut = true;
    }


    boolean isSoldOut() {
        return soldOut;
    }
}

class Maker implements Runnable {
    private Pan pan;
    Thread thread;

    Maker(Pan pan) {
        this.pan = pan;

        thread = new Thread(this, "卖蛋饼的");
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
    Thread thread;

    BaseBuyer(Pan pan) {
        this.pan = pan;

        thread = new Thread(this, "买蛋饼的");
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
        Maker maker = new Maker(pan);
        BaseBuyer buyer = new BaseBuyer(pan);
        maker.thread.start();
        buyer.thread.start();
        try {
            maker.thread.join();
            buyer.thread.join();
        } catch (InterruptedException ignore) {
        }
    }
}
