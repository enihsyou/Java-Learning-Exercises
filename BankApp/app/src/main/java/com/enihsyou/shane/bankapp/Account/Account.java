package com.enihsyou.shane.bankapp.Account;

import com.enihsyou.shane.bankapp.Card.BaseCard;

import java.util.ArrayList;
import java.util.UUID;

public class Account {
    private String accountName;
    private ArrayList<BaseCard> accountCards;

    public UUID getAccountID() {
        return mAccountID;
    }

    private UUID mAccountID;


    public Account(String accountName) {
        this.accountName = accountName;
        this.accountCards = new ArrayList<>();
        this.mAccountID = UUID.randomUUID();
    }

    public Account() {
        // new Account("");
        this.accountName = "Unnamed Account";
        this.accountCards = new ArrayList<>();
        this.mAccountID = UUID.randomUUID();
    }

    public ArrayList<BaseCard> getAccountCards() {
        return accountCards;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }
}
