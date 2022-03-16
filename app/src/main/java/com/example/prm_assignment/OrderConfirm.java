package com.example.prm_assignment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class OrderConfirm extends AppCompatDialogFragment {
    private final float price;

    private final Intent intent;

    public OrderConfirm(float p, Intent i) {
        this.price = p;
        intent = i;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Your shopping cart")
                .setMessage("The total amount is " + price + " $. Do you want to place the order?")
                .setPositiveButton("OK",
                        (dialog, which) -> startActivity(intent)).setNegativeButton("Cancel",
                        (dialog, which) -> {
                            // nothing
                        });
        return builder.create();
    }
}
