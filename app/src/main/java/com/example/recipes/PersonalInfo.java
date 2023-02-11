package com.example.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class PersonalInfo extends Fragment {

    TextView backHome;
    private FirebaseAuth mAuth; // global
    private EditText fullNameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText conPasswordText;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    long id = 0;
    public PersonalInfo() {
        // Required empty public constructor
    }

    public static PersonalInfo newInstance() {
        PersonalInfo fragment = new PersonalInfo();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
        Bundle bundle = this.getArguments();

        mAuth = FirebaseAuth.getInstance(); // local

        backHome = view.findViewById(R.id.backHomeBtn);

        fullNameText = view.findViewById(R.id.updateFullName);
        emailText = view.findViewById(R.id.updateEmailAddress);
        passwordText = view.findViewById(R.id.updatePassword);
        conPasswordText = view.findViewById(R.id.updateConPassword);


        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_personalInfo_to_home2);
            }

        });
        return view;
    }
    public void UpdateFunc(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String conPassword = conPasswordText.getText().toString();
        String fullName = fullNameText.getText().toString();


        // validation user fill all the fields before sending to DB
        if (fullName.isEmpty() || email.isEmpty() ||
                password.isEmpty() || conPassword.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill all the fields",
                    Toast.LENGTH_SHORT).show();

        }else if (!password.equals(conPassword)) {
            Toast.makeText(getActivity(), "Passwords are not matching",
                    Toast.LENGTH_SHORT).show();
        }
    }

}