package com.enihsyou.shane.bankapp.Card;

import java.math.BigDecimal;
import java.util.Random;

/*借记卡*/
public class DebitCard extends BaseCard {
    public DebitCard(double amount) {
        fee = BigDecimal.ZERO;
        quota = BigDecimal.ZERO;
        cardName = "借记卡";
        setBalance(new BigDecimal(amount));
        setCardNumber(new Random().nextLong());
    }
}
