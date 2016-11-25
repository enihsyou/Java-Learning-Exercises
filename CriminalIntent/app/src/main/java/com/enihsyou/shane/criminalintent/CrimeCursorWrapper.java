package com.enihsyou.shane.criminalintent;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.enihsyou.shane.criminalintent.DBSchema.CrimeTalbe;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTalbe.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTalbe.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTalbe.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTalbe.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTalbe.Cols.SUSPECT));
        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setTitle(title);
        crime.setSuspect(suspect);
        return crime;
    }
}
