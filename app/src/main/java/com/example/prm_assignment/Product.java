package com.example.prm_assignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Product extends AppCompatActivity {
    ListView productList;
    String[] product_name;
    String[] product_price;

    DatabaseHelper databaseHelper;
    String username, searchText;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String categoryName;
    TextView productTextView;
    Button backToCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);

        sharedPreferences = getSharedPreferences("rememberLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        username = getIntent().getExtras().getString("username");

        productTextView = findViewById(R.id.productTextView);
        backToCategoryList = findViewById(R.id.btnBackToCategory);

        databaseHelper = new DatabaseHelper(this);

        if (getIntent().getExtras().getString("category_name") != null) {
            categoryName = getIntent().getExtras().getString("category_name");
            int categoryId = databaseHelper.getCategoryId(categoryName);
            Cursor cursor = databaseHelper.showCategoryProducts(String.valueOf(categoryId));
            product_name = new String[cursor.getCount()];
            product_price = new String[cursor.getCount()];
            int count = 0;
            while (!cursor.isAfterLast()) {
                product_name[count] = cursor.getString(0);
                product_price[count] = String.valueOf(cursor.getInt(1));
                count++;
                cursor.moveToNext();
            }
            productList = findViewById(R.id.productListView);
            ProductAdapter adapter = new ProductAdapter(this, product_name, product_price);
            productList.setAdapter(adapter);
        } else if (getIntent().getExtras().getString("search_text") != null) {
            searchText = getIntent().getExtras().getString("search_text");
            Cursor cursor = databaseHelper.searchForProducts(searchText);
            if (cursor.getCount() < 1) {
                productTextView.setText("No Item Found");
            } else {
                product_name = new String[cursor.getCount()];
                product_price = new String[cursor.getCount()];
                int count = 0;
                while (!cursor.isAfterLast()) {
                    product_name[count] = cursor.getString(0);
                    product_price[count] = String.valueOf(cursor.getInt(1));
                    count++;
                    cursor.moveToNext();
                }
                productList = findViewById(R.id.productListView);
                ProductAdapter adapter = new ProductAdapter(this, product_name, product_price);
                productList.setAdapter(adapter);
            }
        } else {
            Log.e("Error in Product", "Intent not found");
        }
        backToCategoryList.setOnClickListener(v -> {
            Intent i = new Intent(Product.this, Category.class);
            i.putExtra("username", username);
            startActivity(i);
        });
    }

    class ProductAdapter extends ArrayAdapter<String> {
        Context context;
        String[] productNameAdapter;
        String[] productPriceAdapter;

        ProductAdapter(Product _context, String[] _productNameAdapter, String[] _productPriceAdapter) {
            super(_context, R.layout.dataholder_product, _productNameAdapter);
            this.context = _context;
            this.productNameAdapter = _productNameAdapter;
            this.productPriceAdapter = _productPriceAdapter;

        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.product_row, parent, false);

            final TextView productName = row.findViewById(R.id.txtProductNames);
            TextView productPrice = row.findViewById(R.id.txtProductPrices);
            final EditText productQuantity = row.findViewById(R.id.txtQuantityProductToAddToCart);
            Button addToCart = row.findViewById(R.id.btnAddProductToCart);

            addToCart.setOnClickListener(v -> {
                if (!productQuantity.getText().toString().isEmpty()) {
                    try {
                        int q = Integer.parseInt(productQuantity.getText().toString());
                        if (q < 1) {
                            Toast.makeText(getApplicationContext(), "Enter valid number", Toast.LENGTH_SHORT).show();
                        } else {
                            int customerId = databaseHelper.getCustomerId(username);
                            int productId = databaseHelper.getProductID(productName.getText().toString());
                            databaseHelper.addToCart(String.valueOf(customerId), String.valueOf(productId), String.valueOf(q));
                            Toast.makeText(getApplicationContext(), "Added to Shopping cart", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Product.this, Product.class);
                            if (categoryName != null) {
                                i.putExtra("category_name", categoryName);
                                searchText = null;
                            }
                            if (searchText != null) {
                                i.putExtra("search_text", searchText);
                                categoryName = null;
                            }
                            i.putExtra("username", username);
                            startActivity(i);
                        }
                    } catch (Exception e) {
                        productQuantity.getText().clear();
                        Log.e(productQuantity.getText().toString(), "ERROR");
                        Log.e("ERROR", e.getMessage());
                        Toast.makeText(getApplicationContext(), "Enter numeric only", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enter quantity of product you want to add", Toast.LENGTH_SHORT).show();
                }
            });

            productName.setText(productNameAdapter[position]);
            productPrice.setText("Price: " + productPriceAdapter[position] + " $");
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
                Intent i = new Intent(Product.this, Login.class);
                startActivity(i);
                return true;
            case R.id.ShoppingCart:
                Intent i2 = new Intent(Product.this, ShoppingCart.class);
                i2.putExtra("username", username);
                startActivity(i2);
                return true;
            case R.id.store_location:
                //Intent i3 = new Intent(Category.this, MapFragment.class);
                //i3.putExtra("username", username);
                //startActivity(i3);
                return true;
        }
        return false;
    }

}
