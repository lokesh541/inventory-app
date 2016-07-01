package com.example.android.inventory;

/**
 * Created by lokesh on 30/6/16.
 */
public class Product {

    String name;
    String quantity;
    String price;
    int image;

    public Product(String name, String quantity, String price, int image){
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

}
