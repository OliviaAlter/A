package com.example.prm_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "not_db";
    SQLiteDatabase base_database;

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // create database and tables
        database.execSQL("CREATE TABLE IF NOT EXISTS Customers(CustomerId integer primary key autoincrement,CustomerName text not null,Username text not null,Password text not null)");

        database.execSQL("CREATE TABLE IF NOT EXISTS Categories(CategoryId integer primary key autoincrement,CategoryName text not null)");

        database.execSQL("CREATE TABLE IF NOT EXISTS Products(ProductId integer primary key autoincrement,ProductName text not null,ProductPrice integer not null, CategoryId integer,foreign key(CategoryId) REFERENCES Categories(CategoryId))");

        database.execSQL("CREATE TABLE IF NOT EXISTS Orders(OrderId integer primary key autoincrement,OrderDate date,Address text,CustomerId integer,foreign key(CustomerId) REFERENCES Customers(CustomerId))");

        database.execSQL("CREATE TABLE IF NOT EXISTS OrderDetails(OrderId integer not null,ProductId integer not null,Quantity integer not null,primary key(OrderId, ProductId),foreign key(OrderId) REFERENCES Orders(OrderId),foreign key(ProductId) REFERENCES Products(ProductId))");

        // populate test data
        ContentValues customerValue = new ContentValues();
        customerValue.put("CustomerName", "Test");
        customerValue.put("Username", "test");
        customerValue.put("Password", "123");
        database.insert("Customers", null, customerValue);

        ContentValues categoryValue = new ContentValues();
        categoryValue.put("CategoryName", "Seed & bulbs");
        database.insert("Categories", null, categoryValue);

        categoryValue.put("CategoryName", "Garden decorations");
        database.insert("Categories", null, categoryValue);

        categoryValue.put("CategoryName", "Garden supplies");
        database.insert("Categories", null, categoryValue);

        categoryValue.put("CategoryName", "Compost");
        database.insert("Categories", null, categoryValue);

        categoryValue.put("CategoryName", "Planters");
        database.insert("Categories", null, categoryValue);

        ContentValues row3 = new ContentValues();
        row3.put("ProductName", "Black pants");
        row3.put("ProductPrice", 150);
        row3.put("CategoryId", 1);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Long grey coat");
        row3.put("ProductPrice", 500);
        row3.put("CategoryId", 1);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Dark red skirt");
        row3.put("ProductPrice", 115);
        row3.put("CategoryId", 1);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Blue dress");
        row3.put("ProductPrice", 300);
        row3.put("CategoryId", 1);
        database.insert("Products", null, row3);

        row3.put("ProductName", "High collar pullover");
        row3.put("ProductPrice", 150);
        row3.put("CategoryId", 1);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Italiano pasta");
        row3.put("ProductPrice", 8);
        row3.put("CategoryId", 2);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Crystal sunflower oil- 1 Liter");
        row3.put("ProductPrice", 25);
        row3.put("CategoryId", 2);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Rice - 1 kg");
        row3.put("ProductPrice", 20);
        row3.put("CategoryId", 2);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Brown flour");
        row3.put("ProductPrice", 50);
        row3.put("CategoryId", 2);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 2);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 3);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 3);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 3);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 3);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 3);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 4);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 4);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 4);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 4);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 4);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 5);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 5);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 5);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 5);
        database.insert("Products", null, row3);

        row3.put("ProductName", "Sugar");
        row3.put("ProductPrice", 21);
        row3.put("CategoryId", 5);
        database.insert("Products", null, row3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS Customers");
        database.execSQL("DROP TABLE IF EXISTS Categories");
        database.execSQL("DROP TABLE IF EXISTS Products");
        database.execSQL("DROP TABLE IF EXISTS Orders");
        database.execSQL("DROP TABLE IF EXISTS OrderDetails");
    }

    // function login
    public boolean loginUser(String username, String password) {
        base_database = getWritableDatabase();
        String[] arg = {username, password};
        Cursor cursor = base_database.rawQuery("SELECT * FROM Customers WHERE Username LIKE ? AND Password LIKE ?", arg);
        if (cursor.getCount() > 0) {
            base_database.close();
            return true;
        }
        base_database.close();
        return false;
    }

    // function sign up
    public boolean signUpUser(String customerName, String username, String password) {
        if (customerName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return false;
        }
        ContentValues row = new ContentValues();
        row.put("CustomerName", customerName);
        row.put("Username", username);
        row.put("Password", password);
        base_database = getWritableDatabase();
        base_database.insert("Customers", null, row);
        base_database.close();
        return true;
    }

    // function user validation
    public boolean duplicateUserCheck(String username) {
        base_database = getReadableDatabase();
        String[] arg = {username};
        Cursor cursor = base_database.rawQuery("SELECT * FROM Customers WHERE Username LIKE ?", arg);
        if (cursor.getCount() == 1) {
            base_database.close();
            return true;
        }
        base_database.close();
        return false;
    }

    // function show all categories
    public Cursor showAllCategories() {
        base_database = getReadableDatabase();
        Cursor cursor = base_database.rawQuery("SELECT CategoryName FROM Categories", null);
        cursor.moveToFirst();
        base_database.close();
        return cursor;
    }

    // function search for product
    public Cursor searchProduct(String text) {
        base_database = getReadableDatabase();
        String[] arg = {text};
        Cursor cursor = base_database.rawQuery("SELECT ProductName ,ProductPrice ,Quantity FROM Products WHERE ProName LIKE '%'||?||'%'", arg);
        cursor.moveToFirst();
        base_database.close();
        return cursor;
    }

    // function delete item in cart
    public void deleteFromCart(String shoppingCartId, String productId) {
        base_database = getWritableDatabase();
        base_database.delete("OrderDetails", "OrderId='" + shoppingCartId + "' and ProductId='" + productId + "' ", null);
        base_database.close();
    }


    public int getcateoryId(String categoryname) {
        base_database = getReadableDatabase();
        String[] arg = {categoryname};
        Cursor cursor = base_database.rawQuery("SELECT CategoryId from Categories where CategoryName LIKE ?", arg);
        cursor.moveToFirst();
        base_database.close();
        return cursor.getInt(0);
    }

    public Cursor showCategoryproducts(String cateogry_id) {
        base_database = getReadableDatabase();
        String[] arg = {cateogry_id};
        Cursor cursor = base_database.rawQuery("SELECT ProductName,ProductPrice FROM Products WHERE CategoryId LIKE ?", arg);
        cursor.moveToFirst();
        base_database.close();
        return cursor;
    }

    public int getcustomerID(String email) {
        base_database = getReadableDatabase();
        String[] arg = {email};
        Cursor cursor = base_database.rawQuery("SELECT CustomerId FROM Customers WHERE Username LIKE ?", arg);
        cursor.moveToFirst();
        base_database.close();
        return cursor.getInt(0);
    }

    public int getProductID(String name) {
        base_database = getReadableDatabase();
        String[] arg = {name};
        Cursor cursor = base_database.rawQuery("SELECT ProductId FROM Products WHERE ProductName LIKE ?", arg);
        cursor.moveToFirst();
        base_database.close();
        return cursor.getInt(0);
    }

    public void addToCart(String customerId, String productId, String quantity) {
        base_database = getReadableDatabase();
        String[] arg = {customerId};
        Cursor cursor = base_database.rawQuery("SELECT OrderId FROM Orders WHERE CustomerId LIKE ?", arg);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            insertIntoExistingCart(String.valueOf(cursor.getInt(0)), productId, quantity);
        } else {
            insertIntoCart(customerId, productId, quantity);
        }
        base_database.close();
    }

    public void insertIntoExistingCart(String OrderId, String ProductId, String quantity) {
        base_database = getReadableDatabase();
        ContentValues row = new ContentValues();
        row.put("OrderId", OrderId);
        row.put("ProductId", ProductId);
        row.put("Quantity", quantity);
        base_database = getWritableDatabase();
        base_database.insert("OrderDetails", null, row);
        base_database.close();
    }

    public void insertIntoCart(String CustomerId, String ProductId, String quantity) {
        ContentValues row = new ContentValues();
        row.put("CustomerId", CustomerId);
        base_database = getWritableDatabase();
        base_database.insert("Orders", null, row);
        base_database.close();
        /////insert into OrderDetails/////////
        base_database = getReadableDatabase();
        String[] arg = {CustomerId};
        Cursor cursor = base_database.rawQuery("SELECT OrderId FROM Orders WHERE CustomerId LIKE ?", arg);
        cursor.moveToFirst();
        insertIntoExistingCart(String.valueOf(cursor.getInt(0)), ProductId, quantity);
        base_database.close();
    }

    /////////////////////////Shopping cart/////////////////////////////////
    public int getCustomerCartId(String CustomerId) {
        base_database = getReadableDatabase();
        String[] arg = {CustomerId};
        Cursor cursor = base_database.rawQuery("SELECT OrderId FROM Orders WHERE CustomerId LIKE ?", arg);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        } else {
            return 0;
        }
        base_database.close();
        return cursor.getInt(0);
    }

    public Cursor getCartProduct(String cartId) {
        base_database = getReadableDatabase();
        String[] arg = {cartId};
        Cursor cursor = base_database.rawQuery("SELECT ProductId, Quantity FROM OrderDetails WHERE OrderId LIKE ?", arg);
        cursor.moveToFirst();
        base_database.close();
        return cursor;
    }

    public void updateProductQuantityInCart(String cartId, String productId, String quantity) {
        base_database = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("Quantity", quantity);
        base_database.update("OrderDetails", row, "OrderId LIKE ? AND ProductId LIKE ?", new String[]{cartId, productId});
        base_database.close();
    }

    public Cursor getProductDataInCart(String productId) {
        base_database = getReadableDatabase();
        String[] arg = {productId};
        Cursor cursor = base_database.rawQuery("SELECT ProductName,ProductPrice FROM Products WHERE ProductId LIKE ?", arg);
        cursor.moveToFirst();
        base_database.close();
        return cursor;
    }
    //Ordid integer not null,Proid

    public void deleteProduct(String cartId, String productId) {
        base_database = getReadableDatabase();
        String[] arg = {cartId, productId};
        Cursor cursor = base_database.rawQuery("SELECT Quantity FROM OrderDetails WHERE OrderId LIKE ? AND ProductId LIKE ?", arg);
        cursor.moveToFirst();
        base_database = getWritableDatabase();
        base_database.delete("OrderDetails", "OrderId='" + cartId + "' and ProductId='" + productId + "' ", null);
        base_database.close();
    }

    public float calculateTotal(String[] p, String[] q) {
        float sum = 0;
        for (int i = 0; i < p.length; i++) {
            sum = sum + (Float.parseFloat(p[i]) * Float.parseFloat(q[i]));
        }
        return sum;
    }

    public void placeOrder(String OrderId, String CustomerId) {
        base_database = getWritableDatabase();
        ContentValues row = new ContentValues();
        base_database.update("Orders", row, "OrderId LIKE ? AND CustomerId LIKE ?", new String[]{OrderId, CustomerId});
        base_database.close();
    }
}
