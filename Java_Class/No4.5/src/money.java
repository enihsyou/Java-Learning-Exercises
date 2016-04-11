//7.一个百万富翁碰到一个陌生人，两人达成换钱的协议，陌生人说：每天我给你10万，第一天你给我1分钱，第二天2分钱，第三天4分钱，以后每天给我的钱都是前一天2倍，直到满三十天，富翁很高兴的同意了。请编程计算30天后，每个人共需要支付多少钱，富翁的盈亏平衡点在哪一天？

public class money {
    public static void main(String[] args) {
        int money1 = 0;
        int money2 = 0;
        int day = 0;
        for (int i = 0; i < 30; i++) {
            money1 += 10000000;
            money2 += 1 * Math.pow(2, i);
            if (money1 <= money2 && day == 0) {
                day = i;
            }
        }
        System.out.println("平衡点: 第" + day + "天");
        System.out.println("陌生人支付: " + money1);
        System.out.println("富翁支付: " + money2);
    }

}
