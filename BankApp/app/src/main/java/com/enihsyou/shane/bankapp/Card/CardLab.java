package com.enihsyou.shane.bankapp.Card;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class CardLab {
    private static CardLab sCardLab; //作为单例
    private ArrayList<BaseCard> mCards;

    private CardLab(Context context) {
        mCards = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mCards.add(new BaseCard());
        }
    }

    public static CardLab get(Context context) {
        if (sCardLab == null) sCardLab = new CardLab(context);
        return sCardLab;
    }

    public ArrayList<BaseCard> getCards() {
        return mCards;
    }

    public BaseCard getCard(UUID uuid) {
        for (BaseCard card : mCards) {
            if (card.getID().equals(uuid)) return card;
        }
        return null;
    }
}
