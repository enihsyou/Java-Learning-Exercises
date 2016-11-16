package com.enihsyou.shane.bankapp;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;

/**
 * 卡片操作的记录
 */
public class CardLog {
    private Calendar Calendar; //操作时间
    private String operateType; //操作类型
    private BigDecimal amount; //操作数量
    private BigDecimal charge; //操作手续费

    private CardLog() {}

    CardLog(String operateType, BigDecimal amount, BigDecimal charge) {
        Calendar = java.util.Calendar.getInstance();
        this.operateType = operateType;
        this.amount = amount;
        this.charge = charge;
    }

    public static class DepositLog extends CardLog {
        public DepositLog(BigDecimal amount) {
            new CardLog("存款", amount, BigDecimal.ZERO);
        }

    }

    public static class WithdrawLog extends CardLog {
        public WithdrawLog(BigDecimal amount, BigDecimal fee) {
            new CardLog("取款", amount, fee);
        }

    }

    public static class PurchaseLog extends CardLog {
        public PurchaseLog(BigDecimal amount, BigDecimal fee, int times) {
            new CardLog(String.format(Locale.getDefault(), "付款(分%d期)", times), amount, fee);
        }

        public PurchaseLog(BigDecimal amount, BigDecimal fee) {
            new CardLog("付款", amount, fee);
        }
    }

    public java.util.Calendar getCalendar() {
        return Calendar;
    }

    public String getOperateType() {
        return operateType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getCharge() {
        return charge;
    }
}
