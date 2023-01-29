package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth; // global
    private EditText fullNameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText conPasswordText;

    int id = 0;

    Button registerBtn;
    TextView loginHereBtn;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    // DatabaseReference  = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance(); // local

        fullNameText = findViewById(R.id.fullName);
        emailText = findViewById(R.id.emailAddress);
        passwordText = findViewById(R.id.password);
        conPasswordText = findViewById(R.id.conPassword);

        registerBtn = findViewById(R.id.registerBtn);
        loginHereBtn = findViewById(R.id.loginHere);

        loginHereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void registerFunc(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String conPassword = conPasswordText.getText().toString();
        String fullName = fullNameText.getText().toString();

        // validation user fill all the fields before sending to DB
        if (fullName.isEmpty() || email.isEmpty() ||
                password.isEmpty() || conPassword.isEmpty()) {
            Toast.makeText(Register.this, "Please fill all the fields",
                    Toast.LENGTH_SHORT).show();
            // confirm password validation
        } else if (!password.equals(conPassword)) {
            Toast.makeText(Register.this, "Passwords are not matching",
                    Toast.LENGTH_SHORT).show();
        } else {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "You registered successfully",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Register.this, "Failed to register",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            writeDB(email, password, fullName);
        }
    }


    public void writeDB(String email, String password, String fullName) {

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    id = (int) snapshot.getChildrenCount();
                } else {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Users p = new Users(email, password, fullName);
        databaseReference = database.getReference().child("Users");
        databaseReference.child(String.valueOf(id + 1)).setValue(p);
    }
}