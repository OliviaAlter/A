package com.example.prm_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ForgetPassword extends AppCompatActivity {

    EditText username, newPassword, confirmNewPassword;
    DatabaseHelper databaseHelper;
    Button btnReset;
    TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_activity);

        username = findViewById(R.id.txtUsernameForgetField);
        newPassword = findViewById(R.id.txtNewPassword);
        confirmNewPassword = findViewById(R.id.txtConfirmNewPassword);

        databaseHelper = new DatabaseHelper(this);

        btnReset = findViewById(R.id.btnResetPassword);
        backToLogin = findViewById(R.id.txtBackToLogin);

        btnReset.setOnClickListener(v -> {
            if (!(newPassword.getText().toString().isEmpty()) && !(confirmNewPassword.getText().toString().isEmpty()) && !(username.getText().toString().isEmpty())) {
                boolean duplicated = databaseHelper.duplicateUserCheck(username.getText().toString());
                if (duplicated) {
                    if (newPassword.getText().toString().equals(confirmNewPassword.getText().toString())) {
                        databaseHelper.updatePassword(username.getText().toString(), newPassword.getText().toString());
                        Toast.makeText(getApplicationContext(), "Your password has updated", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ForgetPassword.this, Login.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Password and Confirm password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "This username doesn't exist in our database", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            }
        });

        backToLogin.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Returning to login", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ForgetPassword.this, Login.class);
            startActivity(i);
        });

    }
}
