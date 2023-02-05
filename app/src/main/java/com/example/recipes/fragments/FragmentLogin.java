package com.example.recipes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    private FirebaseAuth mAuth; // global
    private EditText emailText;
    private EditText passwordText;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Button loginBtn;
    TextView registerHereBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogin newInstance(String param1, String param2) {
        FragmentLogin fragment = new FragmentLogin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }    }

   // public  <T extends android.view.View> T findViewById(  @IdRes int id) {
    //}

    private void setContentView(int activity_login) {
    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_login, container, false);


        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance(); // local

        emailText = view.findViewById(R.id.emailAddress);
        passwordText = view.findViewById(R.id.password);
        loginBtn = view.findViewById(R.id.loginBtn);

        registerHereBtn = view.findViewById(R.id.registerHere);

        registerHereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open Register activity
                Navigation.findNavController(view).navigate(R.id.action_fragmentLogin_to_register);
            }
        });
        Button button = view.findViewById(R.id.loginBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                         Toast.makeText(getActivity(),"Please enter your email and password",Toast.LENGTH_LONG).show();
                    }
                mAuth.signInWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                 {
                    @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful())
                   {
                    Toast.makeText(getActivity(), "You entered successfully",
                           Toast.LENGTH_LONG).show();
                      Navigation.findNavController(view).navigate(R.id.action_fragmentLogin_to_home2);
                    }
                     else
                        {
                            Toast.makeText(getActivity(), "You entered field",
                                    Toast.LENGTH_LONG).show();
                            Navigation.findNavController(view).navigate(R.id.action_fragmentLogin_to_register);
                        }

                  }
                  }
                 });

        };
    });

}