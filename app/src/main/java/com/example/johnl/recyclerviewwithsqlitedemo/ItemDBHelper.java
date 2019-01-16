package com.example.johnl.recyclerviewwithsqlitedemo;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class ItemDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "itemlist.db";
    public static final int DB_VERSION = 1;

    public ItemDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE = "CREATE TABLE "+
                ItemContract.ItemEntry.TABLE_NAME + " ("+
                ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ItemContract.ItemEntry.COLUMN_NAME + " TEXT NOT NULL, "+
                ItemContract.ItemEntry.COLUMN_AMOUNT + " INTEGER NOT NULL, "+
                ItemContract.ItemEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"+
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ ItemContract.ItemEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
//-A DB Helper class creates a database