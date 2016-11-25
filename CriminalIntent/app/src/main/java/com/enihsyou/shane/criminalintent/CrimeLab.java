package com.enihsyou.shane.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import com.enihsyou.shane.criminalintent.DBSchema.CrimeTalbe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) sCrimeLab = new CrimeLab(context);
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    }

    public void addCrime(Crime crime) {
        ContentValues values = getContentValues(crime);
        mDatabase.insert(CrimeTalbe.NAME, null, values);
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursorWrapper = queryCrimes(null, null);
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                crimes.add(cursorWrapper.getCrime());
                cursorWrapper.moveToNext();
            }

        } finally {
            cursorWrapper.close();
        }
        return crimes;
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getID().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTalbe.NAME, values, CrimeTalbe.Cols.UUID + " = ?", new String[]{uuidString});
    }

    public Crime getCrime(UUID uuid) {
        CrimeCursorWrapper cursorWrapper = queryCrimes(CrimeTalbe.Cols.UUID + " = ?", new String[]{uuid.toString()});
        try {
            if (cursorWrapper.getCount() == 0) return null;
            cursorWrapper.moveToFirst();
            return cursorWrapper.getCrime();
        } finally {
            cursorWrapper.close();
        }
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTalbe.Cols.UUID, crime.getID().toString());
        values.put(CrimeTalbe.Cols.TITLE, crime.getTitle());
        values.put(CrimeTalbe.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTalbe.Cols.SOLVED, crime.isSolved());
        values.put(CrimeTalbe.Cols.SUSPECT, crime.getSuspect());
        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(CrimeTalbe.NAME, null, whereClause, whereArgs, null, null, null);
        return new CrimeCursorWrapper(cursor);
    }

    public File getPhotoFile(Crime crime) {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (externalFilesDir != null) return new File(externalFilesDir, crime.getPhotoFilename());
        return null;
    }
}
