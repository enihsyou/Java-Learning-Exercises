package com.enihsyou.shane.bankapp.Card;

import com.enihsyou.shane.bankapp.Account.Account;

import java.util.ArrayList;

public class CardLab {
    private Account mAccount;

    public CardLab(Account account) {
        mAccount = account;
    }

    public ArrayList<BaseCard> getCards() {
        return mAccount.getAccountCards();
    }

    public BaseCard getCard(long cardNumber) {
        for (BaseCard card : mAccount.getAccountCards()) {
            if (card.getCardNumber() == cardNumber) return card;
        }
        return null;
    }

    public void addCard(BaseCard card) {
        mAccount.getAccountCards().add(card);
    }
}
