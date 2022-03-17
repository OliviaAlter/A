package com.example.prm_assignment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class ShoppingCartAdapter extends ArrayAdapter<String> {
    private final ShoppingCart context;
    private final String[] productNameAdapter;
    private final String[] productPriceAdapter;
    private final String[] productQuantityAdapter;
    private final String username;
    DatabaseHelper databaseHelper;

    public ShoppingCartAdapter(ShoppingCart _context, String[] _productNameAdapter, String[] _productPriceAdapter, String[] _productQuantityAdapter, DatabaseHelper _databaseHelper, String username) {
        super(_context, R.layout.dataholder_cart, _productNameAdapter);
        this.context = _context;
        this.productNameAdapter = _productNameAdapter;
        this.productPriceAdapter = _productPriceAdapter;
        this.productQuantityAdapter = _productQuantityAdapter;
        this.databaseHelper = _databaseHelper;
        this.username = username;
    }


    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = layoutInflater.inflate(R.layout.rowsc, parent, false);

        final TextView productName = row.findViewById(R.id.txtProductInCartName);
        TextView productPrice = row.findViewById(R.id.txtProductInCartPrice);
        final TextView productQuantity = row.findViewById(R.id.txtProductInCartQuantity);
        final EditText setQuantityOfProduct = row.findViewById(R.id.txtQuantityField);
        Button updateQuantityButton = row.findViewById(R.id.btnUpdateQuantity);
        Button deleteProductButton = row.findViewById(R.id.btnRemoveProductInCart);

        updateQuantityButton.setOnClickListener(v -> {
            int customerId = databaseHelper.getcustomerID(username);
            int cartId = databaseHelper.getCustomerCartId(String.valueOf(customerId));
            int productID = databaseHelper.getProductID(productNameAdapter[position]);
            Cursor cursor = databaseHelper.getProductDataInCart(String.valueOf(productID));

            if (!setQuantityOfProduct.getText().toString().isEmpty()) {
                try {
                    int q = Integer.parseInt(setQuantityOfProduct.getText().toString());
                    if (q < 1 || q > (Integer.parseInt(cursor.getString(1)))) {
                        Toast.makeText(context.getApplicationContext(), "Enter valid number", Toast.LENGTH_LONG).show();
                    } else {
                        databaseHelper.updateProductQuantityInCart(String.valueOf(cartId), String.valueOf(productID), String.valueOf(q));
                        productQuantity.setText("Quantity: " + q);
                        Toast.makeText(context.getApplicationContext(), "Quantity updated", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    setQuantityOfProduct.getText().clear();
                    Toast.makeText(context.getApplicationContext(), "You should enter a number", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context.getApplicationContext(), "Enter quantity of product you want to change to", Toast.LENGTH_LONG).show();
            }
        });

        deleteProductButton.setOnClickListener(v -> {
            int customerId = databaseHelper.getcustomerID(username);
            int cartId = databaseHelper.getCustomerCartId(String.valueOf(customerId));
            int productId = databaseHelper.getProductID(productPriceAdapter[position]);

            Intent i = new Intent(context, ShoppingCart.class);
            i.putExtra("username", username);
            DeleteProduct deletion = new DeleteProduct(cartId, productId, databaseHelper, i);
            deletion.show(context.getSupportFragmentManager(), "delete");

        });

        productName.setText(productNameAdapter[position]);
        productPrice.setText("Price: " + productPriceAdapter[position] + " $");
        productQuantity.setText("Quantity: " + productQuantityAdapter[position]);

        return row;
    }
}
