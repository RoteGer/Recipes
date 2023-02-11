package com.example.recipes.fragments;

import static android.content.ContentValues.TAG;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.recipes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class PersonalInfo extends Fragment {

    TextView returnHome;
    Button update;
    private EditText fullNameText;
    private EditText emailText;
    private EditText passwordText;
    private EditText conPasswordText;
    private String emailBundle;
    private String passwordBundle;
    private String idBundle;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");
    Map<String, Object> userUpdates = new HashMap<>();

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
        if (getArguments() != null) {
            emailBundle = getArguments().getString("email");
            passwordBundle = getArguments().getString("password");
            idBundle = getArguments().getString("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);

        returnHome = view.findViewById(R.id.returnHomeBtn);
        update = view.findViewById(R.id.updateBtn);
        emailText = view.findViewById(R.id.updateEmailAddress);
        passwordText = view.findViewById(R.id.updatePassword);
        conPasswordText = view.findViewById(R.id.updateConPassword);
        fullNameText = view.findViewById(R.id.updateFullName);


        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_personalInfo_to_home);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();
                String conPassword = conPasswordText.getText().toString();
                String fullName = fullNameText.getText().toString();

                if (email.isEmpty() && password.isEmpty() && fullName.isEmpty() && conPassword.isEmpty()) {
                    Toast.makeText(getActivity(), "Nothing to update",
                            Toast.LENGTH_SHORT).show();
                }

                if ((password != null || conPassword != null) && !password.equals(conPassword)) {
                    Toast.makeText(getActivity(), "Passwords are not matching",
                            Toast.LENGTH_SHORT).show();
                } else if (password != null && conPassword != null && !password.isEmpty() && !conPassword.isEmpty()) {
                    updatePassword(password);
                }

                if (fullName != NULL && !fullName.isEmpty()) {
                    userUpdates.put("fullName", fullName);
                }

                if (email != NULL && !email.isEmpty()) {
                    updateEmail(email);
                }

                if (!userUpdates.isEmpty() && !userUpdates.equals(null)) {
                    updateUserData(userUpdates);
                }
            }

        });
        return view;
    }

    private void updateUserData(Map userUpdates) {
        Toast.makeText(getContext(), userUpdates.toString(),  Toast.LENGTH_SHORT).show();


        myRef.child(idBundle).updateChildren(userUpdates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getActivity(), "Data updated successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Data update failed", Toast.LENGTH_SHORT).show();
                });
    }

    public void updatePassword(String newPass) {
        AuthCredential credential = EmailAuthProvider.getCredential(emailBundle, passwordBundle);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users");
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        userUpdates.put("password", newPass);
                                        updateUserData(userUpdates);
                                    } else {
                                        Toast.makeText(getActivity(), "Error password not updated",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "Error auth failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void updateEmail(String newEmail) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(emailBundle, passwordBundle);

        // Making sure email does not exist
        myRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email = emailText.getText().toString();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if (childSnapshot.child("email").getValue().toString().equals(email)) {
                        Toast.makeText(getActivity(), "Email already exist", Toast.LENGTH_SHORT).show();
                    } else {
                        // Prompt the user to re-provide their sign-in credentials
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d(TAG, "User re-authenticated");

                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        user.updateEmail(newEmail)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            userUpdates.put("email", newEmail);
                                                            updateUserData(userUpdates);
                                                            Log.d(TAG, "User email address updated");
                                                        }
                                                    }
                                                });
                                    }
                                });

                    }
                }

        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

}
}

