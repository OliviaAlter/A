package com.example.prm_assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Product extends AppCompatActivity {
    ListView productList;
    String[] product_name;
    String[] product_price;

    DatabaseHelper databaseHelper;
    String username;

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
            int categoryId = databaseHelper.getcateoryId(categoryName);
            Cursor cursor = databaseHelper.showCategoryproducts(String.valueOf(categoryId));
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
            ProductAdapter adapter = new ProductAdapter(this, product_name, product_price, databaseHelper, username, categoryName);
            productList.setAdapter(adapter);
        } else {
            Log.e("Error in Product", "Intent not found");
        }
        backToCategoryList.setOnClickListener(v -> {
            Intent i = new Intent(Product.this, Category.class);
            i.putExtra("username", username);
            startActivity(i);
        });
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
        }
        return false;
    }

}
