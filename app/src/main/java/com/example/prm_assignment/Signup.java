package com.example.prm_assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {

    EditText username, password, confirmPassword, fullName;
    TextView signIn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button btnSignUp;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        username = findViewById(R.id.txtUsernameSignUp);
        password = findViewById(R.id.txtPasswordSignUp);
        confirmPassword = findViewById(R.id.txtConfirmPasswordSignUp);
        fullName = findViewById(R.id.txtFullNameSignUp);
        signIn = findViewById(R.id.txtSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Sign up
        btnSignUp.setOnClickListener(v -> {
            boolean duplicated = databaseHelper.duplicateUserCheck(username.getText().toString());
            if (duplicated) {
                Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_LONG).show();
            } else {
                if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                    boolean success = databaseHelper.signUpUser(fullName.getText().toString(), username.getText().toString(), password.getText().toString());
                    if (success) {
                        Toast.makeText(getApplicationContext(), "Sign up Successfully, returning to login", Toast.LENGTH_LONG).show();
                        final Handler handler = new Handler();
                        handler.postDelayed(() -> {
                            // Do something after 5s = 5000ms
                            Intent i = new Intent(Signup.this, Login.class);
                            startActivity(i);
                        }, 5000);
                    } else {
                        Toast.makeText(getApplicationContext(), "You have to fill all the fields", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Password and Confirm password doesn't match", Toast.LENGTH_LONG).show();
                }
            }
        });

        signIn.setOnClickListener(v -> {
            Intent i = new Intent(Signup.this, Login.class);
            startActivity(i);
        });

    }
}
