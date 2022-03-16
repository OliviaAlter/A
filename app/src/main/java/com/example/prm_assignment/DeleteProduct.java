package com.example.prm_assignment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteProduct extends AppCompatDialogFragment {
    private final int cartId;
    private final int productId;
    private final DatabaseHelper databaseHelper;
    Intent i;

    public DeleteProduct(int _cartId, int _productId, DatabaseHelper _databaseHelper, Intent intent) {
        cartId = _cartId;
        productId = _productId;
        databaseHelper = _databaseHelper;
        i = intent;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Product").setMessage("Are you sure you want to delete this product").setPositiveButton("YES",
                (dialog, which) -> {
                    databaseHelper.deleteProduct(String.valueOf(cartId), String.valueOf(productId));
                    startActivity(i);
                }).setNegativeButton("NO", (dialog, which) -> {

        });
        return builder.create();
    }
}
