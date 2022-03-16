package com.example.prm_assignment;

import android.content.Context;
import android.content.Intent;
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
        super(_context, R.layout.row, _productNameAdapter);
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
        final TextView proname = row.findViewById(R.id.productname);
        TextView proprice = row.findViewById(R.id.productprice);
        final EditText quantitiy = row.findViewById(R.id.quantitytocart);
        Button addtocart = row.findViewById(R.id.addtocart_btn);
        addtocart.setOnClickListener(v -> {
            if (!quantitiy.getText().toString().equals("")) {
                try {

                    int q = Integer.parseInt(quantitiy.getText().toString());
                    if (q < 1) {
                        Toast.makeText(context.getApplicationContext(), "Enter valid number", Toast.LENGTH_LONG).show();
                    } else {
                        int customerId = databaseHelper.getcustomerID(username);
                        int productId = databaseHelper.getProductID(proname.getText().toString());
                        databaseHelper.addtoShoppingcart(String.valueOf(customerId), String.valueOf(productId), String.valueOf(q));
                        Toast.makeText(context.getApplicationContext(), "Added to Shopping cart", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(context, Product.class);
                        if (categoryName != null) {
                            i.putExtra("category_name", categoryName);
                        }
                        i.putExtra("username", username);
                        context.startActivity(i);
                    }
                } catch (Exception e) {
                    quantitiy.getText().clear();
                    Toast.makeText(context.getApplicationContext(), "Enter numeric only", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context.getApplicationContext(), "Enter quantity of product you want to add", Toast.LENGTH_LONG).show();
            }
        });

        proname.setText(productNameAdapter[position]);
        proprice.setText("Price: " + productPriceAdapter[position] + " $");
        return row;
    }
}