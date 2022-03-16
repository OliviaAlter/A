package com.example.prm_assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class ShoppingCart extends AppCompatActivity {
    String username;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseHelper databaseHelper;
    String[] productName;
    String[] productPrice;
    String[] productQuantity;
    ListView cartListView;
    Button btnPlaceOrder, backFromCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        sharedPreferences = getSharedPreferences("rememberLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        username = getIntent().getExtras().getString("username");
        backFromCart = findViewById(R.id.btnBackToCategoryFromCart);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        databaseHelper = new DatabaseHelper(this);

        int customerId = databaseHelper.getcustomerID(username);
        int cartId = databaseHelper.getCustomerCartId(String.valueOf(customerId));
        if (cartId > 0) {
            Cursor cursorIdQuantity = databaseHelper.getCartProduct(String.valueOf(cartId));
            productName = new String[cursorIdQuantity.getCount()];
            productPrice = new String[cursorIdQuantity.getCount()];
            productQuantity = new String[cursorIdQuantity.getCount()];

            int i = 0;
            while (!cursorIdQuantity.isAfterLast()) {
                Cursor cursor_name_price = databaseHelper.getProductDataInCart(cursorIdQuantity.getString(0));
                productName[i] = cursor_name_price.getString(0);
                productPrice[i] = cursor_name_price.getString(1);
                productQuantity[i] = cursorIdQuantity.getString(1);
                cursorIdQuantity.moveToNext();
                i++;
            }
            cartListView = findViewById(R.id.cartListView);
            ShoppingCartAdapter adapter = new ShoppingCartAdapter(this, productName, productPrice, productQuantity, databaseHelper, username);
            cartListView.setAdapter(adapter);
        }

        backFromCart.setOnClickListener(v -> {
            Intent i = new Intent(ShoppingCart.this, Category.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        btnPlaceOrder.setOnClickListener(v -> openDialog(productPrice, productQuantity));

    }

    private void openDialog(String[] price, String[] quantity) {
        float result = databaseHelper.calculateTotal(price, quantity);
        Intent i = new Intent(ShoppingCart.this, PlaceOrder.class);
        i.putExtra("username", username);
        OrderConfirm orderConfirm = new OrderConfirm(result, i);
        orderConfirm.show(getSupportFragmentManager(), "confirm");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                editor.clear();
                editor.apply();
                Intent i = new Intent(ShoppingCart.this, Login.class);
                startActivity(i);
                return true;
            case R.id.ShoppingCart:
                Intent i2 = new Intent(ShoppingCart.this, ShoppingCart.class);
                i2.putExtra("username", username);
                startActivity(i2);
                return true;
        }
        return false;
    }

}

