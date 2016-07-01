package com.example.android.inventory;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventory.InventoryContract.InventoryEntry;

/**
 * Created by lokesh on 1/7/16.
 */
public class InventoryCursorAdapter extends CursorAdapter {

    public InventoryCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView prodName = (TextView) view.findViewById(R.id.product_name);
        TextView prod_quantity = (TextView) view.findViewById(R.id.quantity);
        TextView prod_price = (TextView) view.findViewById(R.id.price);

        String name = cursor.getString(cursor.getColumnIndexOrThrow(InventoryEntry.COLUMN_PRODUCT_NAME));
        String quantity = cursor.getString(cursor.getColumnIndexOrThrow(InventoryEntry.COLUMN_QUANTITY));
        String price = cursor.getString(cursor.getColumnIndexOrThrow(InventoryEntry.COLUMN_PRICE));

        prodName.setText(name);
        prod_quantity.setText(String.valueOf(quantity));
        prod_price.setText(String.valueOf(price));


    }
}
