package com.example.android.inventory;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductDialogFragment extends DialogFragment {

    private InventoryDbHelper mDbHelper;
    private EditText nameStr, quantityStr, priceStr, imageUrlStr;
    private String productName,imageUrl;
    private int quantity, price;


    public AddProductDialogFragment() {
        // Required empty public constructor
    }


    public Dialog onCreateDialog(Bundle bundle) {
        mDbHelper = new InventoryDbHelper(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View layout = (View) inflater.inflate(R.layout.add_product_dialogue, null);
        builder.setView(layout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nameStr = (EditText) layout.findViewById(R.id.product_name);
                        quantityStr = (EditText) layout.findViewById(R.id.quanntity);
                        priceStr = (EditText) layout.findViewById(R.id.price);
                        imageUrlStr = (EditText) layout.findViewById(R.id.image_url);
                      //  String pattern = "(http(s?):/)(/[^/]+)+" + "\.(?:jpg|gif|png)";
                        if (nameStr.getText().toString().trim().length() != 0) {
                            productName = nameStr.getText().toString();
                            quantity = Integer.parseInt(quantityStr.getText().toString().trim());
                            price = Integer.parseInt(priceStr.getText().toString().trim());
                            imageUrl = imageUrlStr.getText().toString();
                            mDbHelper.insertData(productName, price, quantity,imageUrl);
                            ((MainActivity) getActivity()).onResume();
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });


        return builder.create();

    }

}


