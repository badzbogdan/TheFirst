package com.example.bogdan.thefirst;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class AccountDB {
    static final String DB_NAME = "AccountDB";
    static final String TABLE_NAME = "Accounts";

    private DBHelper dbHelper;

    AccountDB(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    void addAccount(String email, String md5Pass) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email", email);
        cv.put("password", md5Pass);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    String getPass(String email) {
        String result = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT password FROM " + TABLE_NAME + " WHERE email=?; ";
        Cursor cursor = db.rawQuery(sql, new String[] {email});
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex("password"));
        }
        db.close();
        cursor.close();
        return result;
    }

    boolean containsEmail(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT email FROM " + TABLE_NAME + " WHERE email=?; ";
        Cursor cursor = db.rawQuery(sql, new String[] {email});
        boolean result = cursor.moveToFirst();
        db.close();
        cursor.close();
        return result;
    }

}
