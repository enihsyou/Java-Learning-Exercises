import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class Maker implements Runnable {
    private final AtomicInteger currentForSell; //当前库存量
    private final AtomicInteger totalSold; //当前销售量
    private final Random random;
    private final Semaphore semaphore;
    private static final int MAX_CAPACITY = 3;//库存量
    private static final int MAX_TODAY_SOLD = 20; //最大销售量

    public Maker(Semaphore signal) {
        semaphore = signal;
        currentForSell = new AtomicInteger(0);
        totalSold = new AtomicInteger(0);
        random = new Random();
        new Thread(this, "卖蛋饼的");
    }

    @Override
    public void run() {
        try {
            while (totalSold.get() <= MAX_TODAY_SOLD) {
                if (currentForSell.get() < MAX_CAPACITY) {
                    System.out.printf("库存%d个，制作1个蛋饼，现在库存%d个\n", currentForSell.get(), currentForSell.getAndIncrement());
                    Thread.sleep(random.nextLong());
                }
            }
            System.out.println("卖完收工！");
        } catch (InterruptedException e) {
            System.out.println("城管来了！");
        }
    }
}

class BaseBuyer implements Runnable {
    private final Random random;
    private final Semaphore semaphore;

    public BaseBuyer(Semaphore signal) {
        semaphore = signal;
        random = new Random();
        new Thread(this, "买蛋饼的");
    }

    @Override
    public void run() {
        try {
            while (true) {
                semaphore.acquire();

            System.out.println("没人卖了！");
            }
        } catch (InterruptedException e) {
            System.out.println("不让卖了！");

        }
    }
}

public class PanCake {
    public static void main(String[] args) {
        // ExecutorService maker = Executors.newCachedThreadPool();
        // ExecutorService buyer = Executors.newCachedThreadPool();
        // maker.execute(new Maker());
        // buyer.execute(new BaseBuyer());
    }
}
