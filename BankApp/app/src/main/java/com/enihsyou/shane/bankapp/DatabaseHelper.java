package com.enihsyou.shane.bankapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "Bank.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    private static final String CREATE_TABLE_ACCOUNT = String.format(
            "CREATE TABLE %s (_id INTEGER PRIMARY KEY AUTOINCREMENT, %s, %s)",
            DBSchema.AccountDBTable.NAME,
            DBSchema.AccountDBTable.Cols.UUID,
            DBSchema.AccountDBTable.Cols.NAME);
    private static final String CREATE_TABLE_CARD = String.format(
            "CREATE TABLE %s (_id INTEGER PRIMARY KEY AUTOINCREMENT, %s, %s, %s, %s, %s)",
            DBSchema.CardDBTable.NAME,
            DBSchema.CardDBTable.Cols.UUID,
            DBSchema.CardDBTable.Cols.NUMBER,
            DBSchema.CardDBTable.Cols.TYPE,
            DBSchema.CardDBTable.Cols.BALANCE,
            DBSchema.CardDBTable.Cols.REMAIN);
    private static final String CREATE_TABLE_ACCOUNT_CARD = String.format(
            "CREATE TABLE %s (_id INTEGER PRIMARY KEY AUTOINCREMENT, %s, %s)",
            DBSchema.AccountCard.NAME,
            DBSchema.AccountCard.Cols.KEY_ACCOUNT,
            DBSchema.AccountCard.Cols.KEY_CARD);

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT);
        sqLiteDatabase.execSQL(CREATE_TABLE_CARD);
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT_CARD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
}
