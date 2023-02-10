package com.example.recipes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipes.R;
import com.example.recipes.fragments.Home;
import com.example.recipes.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonalInfoActivity extends AppCompatActivity {
    TextView backhomeBtn;
    private FirebaseAuth mAuth; // global
    private EditText fullNameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText conPasswordText;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        mAuth = FirebaseAuth.getInstance(); // local

        backhomeBtn = findViewById(R.id.Back_home);

        fullNameText = findViewById(R.id.updateFullName);
        emailText = findViewById(R.id.updateEmailAddress);
        passwordText = findViewById(R.id.updatePassword);
        conPasswordText = findViewById(R.id.updateConPassword);

        backhomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PersonalInfoActivity.this, Home.class));
            }

        });
    }

    public void UpdateFunc(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String conPassword = conPasswordText.getText().toString();
        String fullName = fullNameText.getText().toString();


        // validation user fill all the fields before sending to DB
        if (fullName.isEmpty() || email.isEmpty() ||
                password.isEmpty() || conPassword.isEmpty()) {
            Toast.makeText(PersonalInfoActivity.this, "Please fill all the fields",
                    Toast.LENGTH_SHORT).show();

        }else if (!password.equals(conPassword)) {
            Toast.makeText(PersonalInfoActivity.this, "Passwords are not matching",
                    Toast.LENGTH_SHORT).show();
        }else {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PersonalInfoActivity.this, "You registered successfully",
                                        Toast.LENGTH_LONG).show();
                                writeDB(email, password, fullName, true);
                            } else {
                                Toast.makeText(PersonalInfoActivity.this, "Failed to register",
                                        Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });
    }
}
    public void writeDB(String email, String password, String fullName, Boolean isAdmin) {

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

        Users p = new Users(email, password, fullName, isAdmin);
        databaseReference.child(String.valueOf(id+1)).setValue(p);
    }
    }