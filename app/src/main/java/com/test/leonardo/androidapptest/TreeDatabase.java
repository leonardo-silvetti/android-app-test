package com.test.leonardo.androidapptest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class TreeDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "riparodb.db";
    private static final int DATABASE_VERSION = 1;

    public TreeDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor getTreeByCode(String code) {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String [] sqlSelect = {"nome", "descrizione"};
        String sqlTables = "piante";
        String where = "id="+code;

        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect, where, null,
                null, null, null);

        if(c!=null) {
            c.moveToFirst();
        }
        return c;
    }
}
