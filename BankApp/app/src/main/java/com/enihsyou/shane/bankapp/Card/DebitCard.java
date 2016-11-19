package com.enihsyou.shane.bankapp.Card;

import java.math.BigDecimal;

/*借记卡*/
public class DebitCard extends BaseCard {

    /*初始化设置属性*/
    public static void setProperty(BaseCard card, BigDecimal amount) {
        card.fee = BigDecimal.ZERO;
        card.quota = BigDecimal.ZERO;
        card.cardName = "借记卡";
        card.setBalance(amount);
        card.setRemain(BigDecimal.ZERO);
    }
}
