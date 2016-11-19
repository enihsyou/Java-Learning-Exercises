package com.enihsyou.shane.bankapp.Account;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class AccountLab {
    private static AccountLab sAccountLab; //作为单例

    private ArrayList<Account> mAccounts;

    public static AccountLab get(Context context) {
        if (sAccountLab == null) sAccountLab = new AccountLab(context);
        return sAccountLab;
    }

    private AccountLab(Context context) {
        mAccounts = new ArrayList<>();
        for (int i = 0; i < 5; i++) { // TODO: 16/11/18 018 创建账户的界面
            Account account = new Account("#" + i);
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

    public void addAccount(Account account) {
        mAccounts.add(account);
    }
}
