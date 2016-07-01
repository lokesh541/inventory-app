package com.example.android.inventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Intent.EXTRA_TEXT);
        TextView view =  (TextView) findViewById(R.id.product_details);
        view.setText(message);


    }
}
