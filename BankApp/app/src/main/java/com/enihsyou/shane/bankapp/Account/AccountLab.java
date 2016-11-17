package com.enihsyou.shane.bankapp.Account;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class AccountLab {
    private static AccountLab sAccountLab;

    private ArrayList<Account> mAccounts;

    public static AccountLab getAccountLab(Context context) {
        if (sAccountLab == null) sAccountLab = new AccountLab(context);
        return sAccountLab;
    }

    private AccountLab(Context context) {
        mAccounts = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Account account = new Account("账户 #" + i);
            mAccounts.add(account);
        }
    }

    public ArrayList<Account> getAccounts() {
        return mAccounts;
    }

    public Account getAccount(UUID uuid) {
        for (Account account : mAccounts) {
            if (account.getAccountID() == uuid) return account;
        }
        return null;
    }
}
