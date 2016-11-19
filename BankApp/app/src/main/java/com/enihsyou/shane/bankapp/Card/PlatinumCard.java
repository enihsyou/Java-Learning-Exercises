package com.enihsyou.shane.bankapp.Card;

import java.math.BigDecimal;

/*白金卡*/
public class PlatinumCard extends BaseCard {
    private static final int DEFAULT_QUOTA = 10000;
    private static final double DEFAULT_FEE = 0.05;

    /*初始化设置属性*/
    public static void setProperty(BaseCard card, BigDecimal amount) {
        card.fee = new BigDecimal(DEFAULT_FEE);
        card.quota = new BigDecimal(DEFAULT_QUOTA);
        card.cardName = "白金卡";
        card.setBalance(amount);
        card.setRemain(new BigDecimal(DEFAULT_QUOTA));
    }
}
