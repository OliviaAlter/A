package com.example.prm_assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText username, password;
    CheckBox rememberMe;
    TextView signUp;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btnLogin;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        signUp = findViewById(R.id.txtSignup);
        rememberMe = findViewById(R.id.ckRemember);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPreferences = getSharedPreferences("rememberLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Login
        btnLogin.setOnClickListener(v -> {
            boolean flag = databaseHelper.loginUser(username.getText().toString(), password.getText().toString());
            if (flag) {
                if (rememberMe.isChecked()) {
                    boolean is_remembered_checked = rememberMe.isChecked();
                    editor.putString("Username", username.getText().toString());
                    editor.putString("Password", password.getText().toString());
                    editor.putBoolean("Is_checked", is_remembered_checked);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Your data have been saved", Toast.LENGTH_LONG).show();
                } else {
                    editor.clear();
                    editor.apply();
                }
                Intent i = new Intent(Login.this, Category.class);
                i.putExtra("username", username.getText().toString());
                startActivity(i);
                username.getText().clear();
                password.getText().clear();
            } else {
                Toast.makeText(getApplicationContext(), "Your Username or Password is wrong", Toast.LENGTH_LONG).show();
            }
        });

        // Sign up
        signUp.setOnClickListener(v -> {
            Intent i = new Intent(Login.this, Signup.class);
            startActivity(i);
        });

    }

    // shared preferences
    private void GetShardPreferencesData() {
        SharedPreferences sp = getSharedPreferences("rememberLogin", MODE_PRIVATE);
        if (sp.contains("Username")) {
            String u = sp.getString("Username", null);
            username.setText(u);
        }
        if (sp.contains("Password")) {
            String p = sp.getString("Password", null);
            password.setText(p);
        }
        if (sp.contains("Is_checked")) {
            boolean check = sp.getBoolean("Is_checked", false);
            rememberMe.setChecked(check);
        }
    }

}