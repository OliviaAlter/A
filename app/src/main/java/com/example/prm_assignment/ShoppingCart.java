package com.example.prm_assignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
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

        int customerId = databaseHelper.getCustomerId(username);
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
            ShoppingCartAdapter adapter = new ShoppingCartAdapter(this, productName, productPrice, productQuantity);
            cartListView.setAdapter(adapter);
        }

        backFromCart.setOnClickListener(v -> {
            Intent i = new Intent(ShoppingCart.this, Category.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        btnPlaceOrder.setOnClickListener(v -> {
            Intent i = new Intent(ShoppingCart.this, ProcessOrder.class);
            i.putExtra("username", username);
            startActivity(i);
        });
    }


    class ShoppingCartAdapter extends ArrayAdapter<String> {
        Context context;
        String[] adapterProductName;
        String[] adapterProductPrice;
        String[] adapterProductQuantity;

        ShoppingCartAdapter(Context c, String[] n, String[] p, String[] q) {
            super(c, R.layout.cart_product_row, n);
            this.context = c;
            this.adapterProductName = n;
            this.adapterProductPrice = p;
            this.adapterProductQuantity = q;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.cart_product_row, parent, false);

            final TextView productName = row.findViewById(R.id.txtCartProductName);
            TextView productPrice = row.findViewById(R.id.txtCartProductPrice);
            final TextView productQuantity = row.findViewById(R.id.txtCartProductQuantity);
            final EditText setQuantity = row.findViewById(R.id.txtCartQuantityField);
            Button updateQuantity = row.findViewById(R.id.btnUpdateQuantityCart);
            Button deleteProduct = row.findViewById(R.id.btnRemoveProductInCart);

            updateQuantity.setOnClickListener(v -> {
                int getCustomerId = databaseHelper.getCustomerId(username);
                int cartId = databaseHelper.getCustomerCartId(String.valueOf(getCustomerId));
                int productId = databaseHelper.getProductID(adapterProductName[position]);
                if (!setQuantity.getText().toString().equals("")) {
                    try {
                        int q = Integer.parseInt(setQuantity.getText().toString());
                        if (q < 1 || q > 100) {
                            Toast.makeText(getApplicationContext(), "Enter valid number", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseHelper.updateProductQuantityInCart(String.valueOf(cartId), String.valueOf(productId), String.valueOf(q));
                            productQuantity.setText("Quantity: " + q);
                            Toast.makeText(getApplicationContext(), "Quantity updated", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        setQuantity.getText().clear();
                        Toast.makeText(getApplicationContext(), "You should enter a number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enter quantity of product you want to change to", Toast.LENGTH_SHORT).show();
                }
            });

            deleteProduct.setOnClickListener(v -> {
                int customerId = databaseHelper.getCustomerId(username);
                int cartId = databaseHelper.getCustomerCartId(String.valueOf(customerId));
                int productId = databaseHelper.getProductID(adapterProductName[position]);

                Intent i = new Intent(ShoppingCart.this, ShoppingCart.class);
                i.putExtra("username", username);
                DeleteProduct d = new DeleteProduct(cartId, productId, databaseHelper, i);
                d.show(getSupportFragmentManager(), "delete");

            });

            productName.setText(adapterProductName[position]);
            productPrice.setText("Price: " + adapterProductPrice[position] + " $");
            productQuantity.setText("Quantity: " + adapterProductQuantity[position]);
            return row;
        }
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

