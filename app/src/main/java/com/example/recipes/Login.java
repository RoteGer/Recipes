package com.example.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       final EditText email = findViewById(R.id.emailAddress);
       final EditText password = findViewById(R.id.password);
       final Button loginBtn = findViewById(R.id.loginBtn);
       final TextView registerHereBtn = findViewById(R.id.registerHere);

        registerHereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open Register activity
                startActivity(new Intent(Login.this, Register.class));
            }
        });

       loginBtn.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               final String emailText = email.getText().toString();
               final String passwordText = password.getText().toString();

               if (emailText.isEmpty() || passwordText.isEmpty()) {
                   Toast.makeText(Login.this, "Please enter your email and password",
                           Toast.LENGTH_SHORT).show();
               }

           }
       });


    }

}