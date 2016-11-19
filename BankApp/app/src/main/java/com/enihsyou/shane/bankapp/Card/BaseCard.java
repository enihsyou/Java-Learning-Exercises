package com.enihsyou.shane.bankapp.Card;

import com.enihsyou.shane.bankapp.BuildConfig;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.UUID;

/**
 * 所有卡片的基类
 */
public abstract class BaseCard {
    BigDecimal fee; //手续费，小数
    BigDecimal quota; //限额，透支额度
    String cardName; //卡片名称

    private UUID accountID; //隶属于哪个账户
    private long cardNumber; //TODO: 包装成类
    private BigDecimal balance; //余额
    private BigDecimal remain; //剩余可透支
    private ArrayList<CardLog> logArrayList; //操作历史记录

    private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(); // TODO: 16/11/18 018 添加地区 币种

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

    /*获取手续费比例 小数*/
    public BigDecimal getFee() {
        return fee;
    }

    /*获取卡限额*/
    public BigDecimal getQuota() {
        return quota;
    }

    /*获取卡类名*/
    public String getCardName() {
        return cardName;
    }

    /*获取卡号*/
    public long getCardNumber() {
        return cardNumber;
    }

    /*获取当前余额*/
    public BigDecimal getBalance() {
        return balance;
    }

    /*设置卡号*/
    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    /*设置余额*/
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /*获取剩余可用额度*/
    public BigDecimal getRemain() {
        return remain;
    }

    /*获取操作记录*/
    public ArrayList<CardLog> getLogArrayList() {
        return logArrayList;
    }

    /*格式化货币数字*/
    public static String format(BigDecimal number) {
        return currencyFormatter.format(number);
    }
}

