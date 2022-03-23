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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProcessOrder extends AppCompatActivity {
    String username;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseHelper databaseHelper;
    String[] productName;
    String[] productPrice;
    String[] productQuantity;
    ListView orderListView;
    Button btnPlaceOrder, backFromCart;
    RadioGroup radioGroup;
    RadioButton rbCreditCard, rbCod;
    String date;
    EditText address, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processing_order_activity);

        sharedPreferences = getSharedPreferences("rememberLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        username = getIntent().getExtras().getString("username");
        radioGroup = findViewById(R.id.radioGroup);
        rbCreditCard = findViewById(R.id.rbCreditCard);
        rbCod = findViewById(R.id.rbCod);
        backFromCart = findViewById(R.id.btnBackToCategoryFromProcessingOrder);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrderFromProcessingOrder);
        address = findViewById(R.id.txtAddressProcessing);
        phone = findViewById(R.id.txtPhoneNumber);

        date = getDate();

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
            orderListView = findViewById(R.id.orderListView);
            ProcessOrderAdapter adapter = new ProcessOrderAdapter(this, productName, productPrice, productQuantity);
            orderListView.setAdapter(adapter);
        }
        btnPlaceOrder.setOnClickListener(v -> {
            if (!(address.getText().toString().isEmpty()) && !(phone.getText().toString().isEmpty())) {
                openDialog(productPrice, productQuantity);
            } else {
                Toast.makeText(getApplicationContext(), "You have to fill all the fields", Toast.LENGTH_SHORT).show();
            }
        });
        backFromCart.setOnClickListener(v -> {
            Intent i = new Intent(ProcessOrder.this, ShoppingCart.class);
            i.putExtra("username", username);
            startActivity(i);
        });
    }

    private void openDialog(String[] price, String[] quantity) {
        float result = databaseHelper.calculateTotal(price, quantity);
        Intent i = new Intent(ProcessOrder.this, Category.class);
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
                Intent i = new Intent(ProcessOrder.this, Login.class);
                startActivity(i);
                return true;
            case R.id.ShoppingCart:
                Intent i2 = new Intent(ProcessOrder.this, ShoppingCart.class);
                i2.putExtra("username", username);
                startActivity(i2);
                return true;
        }
        return false;
    }

    private String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        return sdf.format(now);
    }

    class ProcessOrderAdapter extends ArrayAdapter<String> {
        Context context;
        String[] adapterProductName;
        String[] adapterProductPrice;
        String[] adapterProductQuantity;

        ProcessOrderAdapter(Context c, String[] n, String[] p, String[] q) {
            super(c, R.layout.process_order_row, n);
            this.context = c;
            this.adapterProductName = n;
            this.adapterProductPrice = p;
            this.adapterProductQuantity = q;
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.process_order_row, parent, false);

            final TextView productName = row.findViewById(R.id.txtProductNameProcessing);
            TextView productPrice = row.findViewById(R.id.txtProductPriceProcessing);
            final TextView productQuantity = row.findViewById(R.id.txtProductQuantityProcessing);

            productName.setText(adapterProductName[position]);
            productPrice.setText("Price: " + adapterProductPrice[position] + "$");
            productQuantity.setText("Quantity: " + adapterProductQuantity[position]);
            return row;
        }
    }


}