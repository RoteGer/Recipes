package com.example.recipes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.recipes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    private FirebaseAuth mAuth; // global
    private EditText emailText;
    private EditText passwordText;

    Button login;

    public FragmentLogin() {
        // Required empty public constructor
    }

    public static FragmentLogin newInstance() {
        FragmentLogin fragment = new FragmentLogin();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void setContentView(int activity_login) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance(); // local

        emailText = view.findViewById(R.id.emailAddress);
        passwordText = view.findViewById(R.id.password);
        login = view.findViewById(R.id.loginBtn);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter your email and password", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("Users");
                                        myRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                                                    if (childSnapshot.child("email").getValue().toString().equals(email)){
                                                        Bundle bundle = new Bundle();
                                                        if (childSnapshot.child("isAdmin").getValue().equals(true)){
                                                            // User is Admin
                                                            Toast.makeText(getActivity(), "Welcome Admin " + childSnapshot.child("fullName").getValue().toString(),
                                                                    Toast.LENGTH_LONG).show();
                                                        } else {
                                                            // User is not Admin
                                                                Toast.makeText(getActivity(), "Welcome user " + childSnapshot.child("fullName").getValue().toString(),
                                                                        Toast.LENGTH_LONG).show();
                                                        }
                                                        String isAdmin = childSnapshot.child("isAdmin").getValue().toString();
                                                        String id = childSnapshot.getKey();

                                                        bundle.putString("isAdmin", isAdmin);
                                                        bundle.putString("email", email);
                                                        bundle.putString("password", password);
                                                        bundle.putString("id", id);
                                                        Navigation.findNavController(view).navigate(R.id.action_fragmentLogin_to_home, bundle);
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    } else {
                                        Toast.makeText(getActivity(), "Failed to enter",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }

        });
        return view;
    }
}

