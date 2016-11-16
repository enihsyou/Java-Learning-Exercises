package com.enihsyou.shane.bankapp;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 所有卡片的基类
 */
public abstract class BaseCardType {
    BigDecimal fee; //手续费，小数
    BigDecimal quota; //限额，透支额度
    String cardName; //卡片名称

    private long cardNumber; //TODO: 包装成类
    private BigDecimal balance; //余额
    private BigDecimal remain; //剩余可透支
    private ArrayList<CardLog> logArrayList; //操作历史记录

    /*存钱*/
    BigDecimal deposit(BigDecimal amount) {
        if (BuildConfig.DEBUG && amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException();
        BigDecimal moneySubRemain = amount.subtract(quota.subtract(remain)); //存入减去还款剩下的:多余量=存进来—(额度—剩余额度)
        if (moneySubRemain.compareTo(BigDecimal.ZERO) >= 0) { //还款过多
            balance = balance.add(moneySubRemain);
            remain = quota;
        } else {
            remain = quota.add(moneySubRemain); //加一个负数
        }
        logArrayList.add(new CardLog.DepositLog(amount));
        return amount;
    }

    /*取钱*/
    BigDecimal withdraw(BigDecimal amount) {
        BigDecimal thisFee = amount.multiply(fee); //本次操作需要的手续费
        amount = amount.add(thisFee);
        BigDecimal newMoney = balance.subtract(amount); //尝试直接取钱:newMoney=余额—取钱量

        if (newMoney.compareTo(BigDecimal.ZERO) >= 0) { //余额足够
            balance = newMoney;
        } else if (newMoney.add(remain).compareTo(BigDecimal.ZERO) >= 0) { //加上透支 足够
            remain = remain.subtract(amount.subtract(balance));
            balance = BigDecimal.ZERO;
        } else {
            return BigDecimal.ZERO;
        }
        logArrayList.add(new CardLog.WithdrawLog(amount, thisFee));
        return amount;
    }

    /**
     * 取钱 分期
     *
     * @param amount 付款金额
     * @param times  分期次数，times>1
     *
     * @return BigDecimal 返回成功操作的钱数，不为0即成功
     */
    BigDecimal withdraw(BigDecimal amount, int times) {
        BigDecimal withdrawTimes = new BigDecimal(times); //包装一下做除法
        BigDecimal total = amount.divide(withdrawTimes, BigDecimal.ROUND_HALF_UP);
        BigDecimal thisFee = total.multiply(fee); //本次操作需要的手续费
        total = total.add(thisFee);
        /*进行减钱*/
        BigDecimal newMoney = balance.subtract(total); //尝试直接取钱:newMoney=余额—取钱量
        if (newMoney.compareTo(BigDecimal.ZERO) >= 0) { //余额足够
            balance = newMoney;
        } else if (newMoney.add(remain).compareTo(BigDecimal.ZERO) >= 0) { //加上透支 足够
            remain = remain.subtract(amount.subtract(balance));
            balance = BigDecimal.ZERO;
        } else {
            return BigDecimal.ZERO;
        }
        logArrayList.add(new CardLog.PurchaseLog(amount, thisFee, times));
        return total;
    }

    /**
     * @return 当前卡的手续费比例
     */
    public BigDecimal getFee() {
        return fee;
    }

    /**
     * @return 卡的允许限额
     */
    public BigDecimal getQuota() {
        return quota;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }


    public long getCardNumber() {
        return cardNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getRemain() {
        return remain;
    }

    public ArrayList<CardLog> getLogArrayList() {
        return logArrayList;
    }
}

/*借记卡*/
class DebitCard extends BaseCardType {
    DebitCard() {
        fee = BigDecimal.ZERO;
        quota = BigDecimal.ZERO;
        cardName = "借记卡";
    }
}

/*信用卡*/
class CreditCard extends BaseCardType {
    CreditCard() {
        fee = new BigDecimal(0.01);
        quota = new BigDecimal(1000);
        cardName = "信用卡";
    }
}

/*白金卡*/
class PlatinumCard extends BaseCardType {
    PlatinumCard() {
        fee = new BigDecimal(0.05);
        quota = new BigDecimal(10000);
        cardName = "白金卡";
    }
}
