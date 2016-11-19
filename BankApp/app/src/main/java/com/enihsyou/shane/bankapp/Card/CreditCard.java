package com.enihsyou.shane.bankapp.Card;

import java.math.BigDecimal;

/*信用卡*/
public class CreditCard extends BaseCard {
    private static final int DEFAULT_QUOTA = 1000;
    private static final double DEFAULT_FEE = 0.01;

    /*初始化设置属性*/
    public static void setProperty(BaseCard card, BigDecimal amount) {
        card.fee = new BigDecimal(DEFAULT_FEE);
        card.quota = new BigDecimal(DEFAULT_QUOTA);
        card.cardName = "信用卡";
        card.setBalance(amount);
        card.setRemain(new BigDecimal(DEFAULT_QUOTA));
    }
}
