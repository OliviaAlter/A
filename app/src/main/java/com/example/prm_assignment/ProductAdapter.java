package com.example.prm_assignment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class ProductAdapter extends ArrayAdapter<String> {
    private final Product context;
    private final String[] productNameAdapter;
    private final String[] productPriceAdapter;
    private final String username;
    private final String categoryName;
    DatabaseHelper databaseHelper;

    ProductAdapter(Product _context, String[] _productNameAdapter, String[] _productPriceAdapter, DatabaseHelper _databaseHelper, String _username, String _categoryName) {
        super(_context, R.layout.dataholder_product, _productNameAdapter);
        this.context = _context;
        this.productNameAdapter = _productNameAdapter;
        this.productPriceAdapter = _productPriceAdapter;
        this.databaseHelper = _databaseHelper;
        this.username = _username;
        this.categoryName = _categoryName;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = layoutInflater.inflate(R.layout.row, parent, false);

        final TextView productName = row.findViewById(R.id.productname);
        TextView productPrice = row.findViewById(R.id.productprice);
        final EditText productQuantity = row.findViewById(R.id.quantitytocart);
        Button addToCart = row.findViewById(R.id.addtocart_btn);

        addToCart.setOnClickListener(v -> {
            if (!productQuantity.getText().toString().equals("")) {
                try {
                    int q = Integer.parseInt(productQuantity.getText().toString());
                    if (q < 1) {
                        Toast.makeText(context.getApplicationContext(), "Enter valid number", Toast.LENGTH_SHORT).show();
                    } else {
                        int customerId = databaseHelper.getcustomerID(username);
                        int productId = databaseHelper.getProductID(productName.getText().toString());
                        databaseHelper.addToCart(String.valueOf(customerId), String.valueOf(productId), String.valueOf(q));
                        Toast.makeText(context.getApplicationContext(), "Added to Shopping cart", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, Product.class);
                        if (categoryName != null) {
                            i.putExtra("category_name", categoryName);
                        }
                        i.putExtra("username", username);
                        context.startActivity(i);
                    }
                } catch (Exception e) {
                    productQuantity.getText().clear();
                    Toast.makeText(context.getApplicationContext(), "Enter numeric only", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e(productQuantity.getText().toString(), "A");
                Toast.makeText(context.getApplicationContext(), "Enter quantity of product you want to add", Toast.LENGTH_SHORT).show();
            }
        });

        productName.setText(productNameAdapter[position]);
        productPrice.setText("Price: " + productPriceAdapter[position] + " $");
        return row;
    }
}