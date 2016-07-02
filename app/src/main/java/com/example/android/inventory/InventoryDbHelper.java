package com.example.android.inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.inventory.InventoryContract.InventoryEntry;


/**
 * Created by lokesh on 1/7/16.
 */
public class InventoryDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "inventory-app-db.db";

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_PRODUCT_TABLE = "CREATE TABLE " + InventoryEntry.TABLE_NAME + " (" +
                InventoryEntry._ID + " INTEGER PRIMARY KEY ," +
                InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT UNIQUE NOT NULL, " +
                InventoryEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                InventoryEntry.COLUMN_PRICE + " INTEGER NOT NULL, " +
                InventoryEntry.COLUMN_URL + " TEXT NOT NULL" +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + InventoryEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public boolean insertData(String name,int price, int quantity,String imageUrl) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME,name);
        values.put(InventoryEntry.COLUMN_QUANTITY, price);
        values.put(InventoryEntry.COLUMN_PRICE, quantity);
        values.put(InventoryEntry.COLUMN_URL, imageUrl);

        long rowId = sqLiteDatabase.insert(InventoryEntry.TABLE_NAME, null, values);
        return rowId != -1;
    }
}
