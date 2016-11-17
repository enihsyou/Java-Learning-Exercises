package com.enihsyou.shane.bankapp.Card;

import android.content.Context;

import java.util.ArrayList;

public class CardLab {
    private static CardLab sCardLab;

    private ArrayList<BaseCard> mCards;

    public static CardLab getCardLab(Context context) {
        if (sCardLab == null) sCardLab = new CardLab(context);
        return sCardLab;
    }

    private CardLab(Context context) {
        mCards = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            BaseCard card = new DebitCard(i);
            mCards.add(card);
        }
    }
}
