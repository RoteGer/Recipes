package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth; // global
    private EditText emailText;
    private EditText passwordText;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Button loginBtn;
    TextView registerHereBtn;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance(); // local

        emailText = findViewById(R.id.emailAddress);
        passwordText = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);

        registerHereBtn = findViewById(R.id.registerHere);

        registerHereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open Register activity
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    public void loginFunc(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Login.this, "Please enter your email and password",
                           Toast.LENGTH_SHORT).show();
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "You entered successfully",
                                    Toast.LENGTH_LONG).show();
                            // here we need to navigate to homepage fragment
                        } else {
                            Toast.makeText(Login.this, "Login failed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}