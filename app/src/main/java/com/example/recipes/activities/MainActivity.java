package com.example.recipes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recipes.R;
import com.example.recipes.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; // global
    private EditText userEmail;
    private EditText passwordText;
    FirebaseDatabase database;
    DatabaseReference myRef;
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance(); // local
        userEmail = findViewById(R.id.editTextTextEmailAddress);
        passwordText = findViewById(R.id.editTextTextPassword);
    }


    public void loginFunc(View view) {
        String email = userEmail.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "You entered successfully",
                                    Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(MainActivity.this, "You entered successfully",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public void regFunc(View view) {
        String email = userEmail.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "You registered successfully",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to register",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
        writeDB(email, password);
    }

    public void readDB () {
// Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Users value = dataSnapshot.getValue(Users.class);
                Toast.makeText(MainActivity.this, value.email,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void writeDB (String email, String password) {

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Users");

        myRef.addValueEventListener (new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    id = (int) snapshot.getChildrenCount();
                }else {}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Users p = new Users(email, password, "rotem");
        myRef = database.getReference().child("Users");
        myRef.child(String.valueOf(id+1)).setValue(p);
    }
}