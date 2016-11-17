package com.enihsyou.shane.bankapp.Card;

import java.math.BigDecimal;
import java.util.Random;

/*信用卡*/
public class CreditCard extends BaseCard {
    CreditCard(double amount) {
        fee = new BigDecimal(0.01);
        quota = new BigDecimal(1000);
        cardName = "信用卡";
        setBalance(new BigDecimal(amount));
        setCardNumber(new Random().nextLong());

    }
}
