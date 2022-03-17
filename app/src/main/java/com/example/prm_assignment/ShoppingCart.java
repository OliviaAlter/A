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
            ShoppingCartAdapter adapter = new ShoppingCartAdapter(this, productName, productPrice, productQuantity);
            cartListView.setAdapter(adapter);
        }

        backFromCart.setOnClickListener(v -> {
            Intent i = new Intent(ShoppingCart.this, Category.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        btnPlaceOrder.setOnClickListener(v -> openDialog(productPrice, productQuantity));

    }

    class ShoppingCartAdapter extends ArrayAdapter<String> {
        Context context;
        String[] aproduct_name;
        String[] aproduct_price;
        String[] aproduct_quantity;

        ShoppingCartAdapter(Context c, String[] n, String[] p, String[] q) {
            super(c, R.layout.rowsc, n);
            this.context = c;
            this.aproduct_name = n;
            this.aproduct_price = p;
            this.aproduct_quantity = q;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.rowsc, parent, false);

            final TextView proname = row.findViewById(R.id.txtProductInCartName);
            TextView proprice = row.findViewById(R.id.txtProductInCartPrice);
            final TextView proquant = row.findViewById(R.id.txtProductInCartQuantity);
            final EditText setquantity = row.findViewById(R.id.txtQuantityField);
            Button update_quant = row.findViewById(R.id.btnUpdateQuantity);
            Button delete_product = row.findViewById(R.id.btnRemoveProductInCart);

            update_quant.setOnClickListener(v -> {
                int custid = databaseHelper.getcustomerID(username);
                int sc_id = databaseHelper.getCustomerCartId(String.valueOf(custid));
                int proid = databaseHelper.getProductID(aproduct_name[position]);
                if (!setquantity.getText().toString().equals("")) {
                    try {
                        int q = Integer.parseInt(setquantity.getText().toString());
                        if (q < 1 || q > 100) {
                            Toast.makeText(getApplicationContext(), "Enter valid number", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseHelper.updateProductQuantityInCart(String.valueOf(sc_id), String.valueOf(proid), String.valueOf(q));
                            proquant.setText("Quantity: " + q);
                            Toast.makeText(getApplicationContext(), "Quantity updated", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        setquantity.getText().clear();
                        Toast.makeText(getApplicationContext(), "You should enter a number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enter quantity of product you want to change to", Toast.LENGTH_SHORT).show();
                }
            });

            delete_product.setOnClickListener(v -> {
                int custid = databaseHelper.getcustomerID(username);
                int sc_id = databaseHelper.getCustomerCartId(String.valueOf(custid));
                int proid = databaseHelper.getProductID(aproduct_name[position]);

                Intent i = new Intent(ShoppingCart.this, ShoppingCart.class);
                i.putExtra("username", username);
                DeleteProduct d = new DeleteProduct(sc_id, proid, databaseHelper, i);
                d.show(getSupportFragmentManager(), "delete");

            });

            proname.setText(aproduct_name[position]);
            proprice.setText("Price: " + aproduct_price[position] + " $");
            proquant.setText("Quantity: " + aproduct_quantity[position]);
            return row;
        }
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

