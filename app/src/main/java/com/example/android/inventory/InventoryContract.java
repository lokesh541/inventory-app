package com.example.android.inventory;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lokesh on 1/7/16.
 */
public final class InventoryContract {

    public InventoryContract() {

    }

    public static abstract class InventoryEntry implements BaseColumns {

        public static final String TABLE_NAME = "products";
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_URL = "url";


    }

}

