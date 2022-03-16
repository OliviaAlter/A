package com.example.prm_assignment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteProductInCart extends AppCompatDialogFragment {
    private final int shoppingCart_Id;
    private final int productId;
    private final DatabaseHelper databaseHelper;
    Intent i;

    public DeleteProductInCart(int shoppingCart, int product, DatabaseHelper _databaseHelper, Intent intent) {
        shoppingCart_Id = shoppingCart;
        productId = product;
        databaseHelper = _databaseHelper;
        i = intent;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Product").setMessage("Are you sure you want to delete this product").setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.deleteFromCart(String.valueOf(shoppingCart_Id), String.valueOf(productId));
                startActivity(i);
            }
        }).setNegativeButton("NO", (dialog, which) -> {
            // TODO: This return the dialog to the cart
        });
        return builder.create();

    }
}
