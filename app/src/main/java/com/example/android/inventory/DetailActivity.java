package com.example.android.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.net.URL;

public class DetailActivity extends AppCompatActivity {


    private SQLiteDatabase sqLiteDatabase;
    private InventoryDbHelper mHelper;
    private static final String TAG = "MyActivity";
    private String name;
    private String price;
    private int quantity;
    Intent intent;
    String id;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        intent = getIntent();
        id = intent.getStringExtra(Intent.EXTRA_TEXT);


        mHelper = new InventoryDbHelper(getApplicationContext());
        sqLiteDatabase = mHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(InventoryContract.InventoryEntry.TABLE_NAME, null,
                InventoryContract.InventoryEntry._ID + "=?"
                , new String[]{id}, null, null, null);




        if (cursor.moveToFirst()) {
            String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_URL));
            imageView = (ImageView) findViewById(R.id.product_image);
            Glide.with(this).load(imageUrl).into(imageView);
            String name = cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME));
            TextView tvName = (TextView) findViewById(R.id.product_name);
            tvName.setText(name);
            quantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_QUANTITY));
            displayQuantity(quantity);
            price = cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.InventoryEntry.COLUMN_PRICE));
            TextView tvPrice = (TextView) findViewById(R.id.price);
            tvPrice.setText(price);


        }


        Button delete = (Button) findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mHelper = new InventoryDbHelper(getApplicationContext());
                sqLiteDatabase = mHelper.getWritableDatabase();
                sqLiteDatabase.delete(InventoryContract.InventoryEntry.TABLE_NAME,
                        InventoryContract.InventoryEntry._ID + "=?"
                        , new String[]{id});
                intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


        Button decrease = (Button) findViewById(R.id.decrease);
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quantity > 1) {
                    quantity = quantity - 1;
                    update(quantity);
                }
                displayQuantity(quantity);
            }
        });

        Button increase = (Button) findViewById(R.id.increase);
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = quantity + 1;
                displayQuantity(quantity);
                update(quantity);

            }
        });

        Button order = (Button) findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_SENDTO)
                        .setData(Uri.parse("mailto:"))
                        .putExtra(Intent.EXTRA_SUBJECT, "Order for" + name)
                        .putExtra(Intent.EXTRA_TEXT, "quantity " + quantity + "\n price " + price);

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


    }


    void displayQuantity(int quantity) {
        TextView tvQuantity = (TextView) findViewById(R.id.quanntity);
        tvQuantity.setText(String.valueOf(quantity));

    }

    void update(int quantity) {
        mHelper = new InventoryDbHelper(getApplicationContext());
        sqLiteDatabase = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY, quantity);
        sqLiteDatabase.update(InventoryContract.InventoryEntry.TABLE_NAME,
                values,
                InventoryContract.InventoryEntry._ID + "=?",
                new String[]{id});
    }

}
