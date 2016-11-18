package com.enihsyou.shane.bankapp.Card;

import java.math.BigDecimal;
import java.util.Random;

/*白金卡*/
public class PlatinumCard extends BaseCard {
    PlatinumCard(double amount) {
        fee = new BigDecimal(0.05);
        quota = new BigDecimal(10000);
        cardName = "白金卡";
        setBalance(new BigDecimal(amount));
        setCardNumber(new Random().nextLong());
    }
}
