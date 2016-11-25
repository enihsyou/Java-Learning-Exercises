package com.enihsyou.shane.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.enihsyou.shane.criminalintent.DBSchema.CrimeTalbe;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "crimeBase.db";
    private static final int VERSION = 1;

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                String.format("CREATE TABLE %s (id INTEGER PRIMARY KEY AUTOINCREMENT,%s ,%s ,%s ,%s, %s)",
                        CrimeTalbe.NAME, CrimeTalbe.Cols.UUID, CrimeTalbe.Cols.TITLE, CrimeTalbe.Cols.DATE,
                        CrimeTalbe.Cols.SOLVED, CrimeTalbe.Cols.SUSPECT));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE %s", CrimeTalbe.NAME));
    }
}
