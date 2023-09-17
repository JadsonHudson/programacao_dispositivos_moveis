package com.example.pratica001_ppdm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView apresentation = findViewById(R.id.apresentation);
        apresentation.setText(R.string.apresentation);

        TextView description = findViewById(R.id.description);
        description.setText(R.string.description);

        EditText userLogin = findViewById(R.id.userLogin);
        userLogin.setHint(R.string.userLogin);

        EditText passwordLogin = findViewById(R.id.passwordLogin);
        passwordLogin.setHint(R.string.passwordLogin);

        Button logIn = findViewById(R.id.logIn);
        logIn.setText(R.string.logIn);

        Button createAccount = findViewById(R.id.createAccount);
        createAccount.setText(R.string.createAccount);

        Button forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setText(R.string.forgotPassword);
    }
}