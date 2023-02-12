package com.example.recipes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipes.R;

public class Login extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }
    public void regHere(View view) {

        // open Register activity
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }

}