package com.example.android.inventory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

import com.example.android.inventory.InventoryContract.InventoryEntry;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "database values";

    private InventoryCursorAdapter cursorAdapter;
    private Cursor inventoryCursor;
    private InventoryDbHelper mHelper;
    private ListView listView;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());


        mHelper = new InventoryDbHelper(this);
        sqLiteDatabase = mHelper.getWritableDatabase();
        Cursor inventoryCursor = sqLiteDatabase.rawQuery("SELECT  * FROM  products", null);
        listView = (ListView) findViewById(R.id.list);
        populateList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                String id = cursor.getString(cursor.getColumnIndexOrThrow(InventoryEntry._ID));
                Intent intent = new Intent(MainActivity.this, DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, id);
                startActivity(intent);

            }
        });
        Button addProduct = (Button) findViewById(R.id.add);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new AddProductDialogFragment();
                newFragment.show(getSupportFragmentManager(), "products");

            }

        });

    }

    @Override
    protected void onResume() {

        Cursor newCursor = sqLiteDatabase.rawQuery("SELECT  * FROM  products", null);
        cursorAdapter.swapCursor(newCursor);
        populateList();
        super.onResume();

    }


    void populateList() {
        if (cursorAdapter == null) {
            cursorAdapter = new InventoryCursorAdapter(this, inventoryCursor, 0);
            listView.setAdapter(cursorAdapter);
        } else {
            cursorAdapter.notifyDataSetChanged();
        }
    }
}
