package com.example.prm_assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Category extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    ArrayAdapter<String> adapter;
    String username;
    EditText txtSearchText;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        username = getIntent().getExtras().getString("username");
        // get shared preferences
        sharedPreferences = getSharedPreferences("rememberLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ListView categoryListView = findViewById(R.id.list_category);
        searchButton = findViewById(R.id.btnSearch);
        txtSearchText = findViewById(R.id.txtSearchText);

        // set adapter
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        categoryListView.setAdapter(adapter);

        getAllCategories();

        categoryListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(Category.this, Product.class);
            i.putExtra("category_name", ((TextView) view).getText().toString());
            i.putExtra("username", username);
            startActivity(i);
        });

        searchButton.setOnClickListener((v) -> {
            Intent i = new Intent(Category.this, Product.class);
            i.putExtra("search_text", txtSearchText.getText().toString());
            i.putExtra("username", username);
            startActivity(i);
        });

    }

    private void getAllCategories() {
        Cursor cursor = databaseHelper.showAllCategories();
        while (!cursor.isAfterLast()) {
            adapter.add(cursor.getString(0));
            cursor.moveToNext();
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
                Intent i = new Intent(Category.this, Login.class);
                startActivity(i);
                return true;
            case R.id.ShoppingCart:
                Intent i2 = new Intent(Category.this, ShoppingCart.class);
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
