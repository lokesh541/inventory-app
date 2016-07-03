package com.example.android.inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventory.InventoryContract.InventoryEntry;

/**
 * Created by lokesh on 1/7/16.
 */
public class InventoryCursorAdapter extends CursorAdapter {
    private  int quantity;
    private  String id;
    private InventoryDbHelper mHelper;
    private SQLiteDatabase sqLiteDatabase;

    public InventoryCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView prodName = (TextView) view.findViewById(R.id.product_name);
        final TextView prod_quantity = (TextView) view.findViewById(R.id.quantity);
        TextView prod_price = (TextView) view.findViewById(R.id.price);

        id = cursor.getString(cursor.getColumnIndexOrThrow(InventoryEntry._ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(InventoryEntry.COLUMN_PRODUCT_NAME));
        quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryEntry.COLUMN_QUANTITY));
        String price = cursor.getString(cursor.getColumnIndexOrThrow(InventoryEntry.COLUMN_PRICE));

        prodName.setText(name);
        prod_quantity.setText("Qty:" + String.valueOf(quantity));
        prod_price.setText("Price:"+String.valueOf(price));

        Button sale = (Button) view.findViewById(R.id.sale);
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity>0)
                    quantity = quantity-1;
                prod_quantity.setText("Qty:" + String.valueOf(quantity));
                mHelper = new InventoryDbHelper(context);
                sqLiteDatabase = mHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, quantity);
                sqLiteDatabase.update(InventoryContract.InventoryEntry.TABLE_NAME,
                        values,
                        InventoryContract.InventoryEntry._ID + "=?",
                        new String[]{id});
            }
        });


    }
}
