package com.enihsyou.shane.bankapp.Account;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class AccountLab {
    private static AccountLab sAccountLab; //作为单例
    private ArrayList<Account> mAccounts;

    // private Context mContext;
    // private SQLiteDatabase mDatabase;

    public static AccountLab get(Context context) {
        if (sAccountLab == null) sAccountLab = new AccountLab(context);
        return sAccountLab;
    }

    private AccountLab(Context context) {
        mAccounts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Account account = new Account("#" + i);
            mAccounts.add(account);
        }

        // mContext = context.getApplicationContext();
        // mDatabase = new DatabaseHelper(mContext).getWritableDatabase();

    }

    // private static ContentValues getContentValues(Account account) {
    //     ContentValues values = new ContentValues();
    //     values.put(DBSchema.AccountDBTable.Cols.UUID, account.getAccountID().toString());
    //     values.put(DBSchema.AccountDBTable.Cols.NAME, account.getAccountName());
    //     return values;
    // }

    public ArrayList<Account> getAccounts() {
        return new ArrayList<>();
    }

    public Account getAccount(UUID uuid) {
        for (Account account : mAccounts) {
            if (account.getAccountID() == uuid) return account;
        }
        return null;
    }

    public void addAccount(Account account) {
        mAccounts.add(account);

        // ContentValues values = getContentValues(account);

        // mDatabase.insert(DBSchema.AccountDBTable.NAME, null, values);
    }

    // public void updateAccount(Account account) {
    //     String uuidString = account.getAccountID().toString();
    //     ContentValues values = getContentValues(account);
    //
    //     mDatabase.update(
    //             DBSchema.AccountDBTable.NAME,
    //             values,
    //             DBSchema.AccountDBTable.Cols.UUID + " = ?",
    //             new String[]{uuidString});
    // }
}
