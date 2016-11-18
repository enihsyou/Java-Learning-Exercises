package com.enihsyou.shane.bankapp.Card;

import android.content.Context;

import java.util.ArrayList;

public class CardLab {
    private static CardLab sCardLab; //作为单例

    private ArrayList<BaseCard> mCards;

    public static CardLab getCardLab(Context context) {
        if (sCardLab == null) sCardLab = new CardLab(context);
        return sCardLab;
    }

    private CardLab(Context context) {
        mCards = new ArrayList<>();
        for (int i = 0; i < 100; i++) { // TODO: 16/11/18 018 创建卡片的界面 
            BaseCard card = new DebitCard(i);
            mCards.add(card);
        }
    }

    public ArrayList<BaseCard> getCards() {
        return mCards;
    }

    public BaseCard getCard(long cardNumber) {
        for (BaseCard card : mCards) {
            if (card.getCardNumber() == cardNumber) return card;
        }
        return null;
    }
}
