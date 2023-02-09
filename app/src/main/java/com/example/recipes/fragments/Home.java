package com.example.recipes.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.recipes.R;
import com.example.recipes.activities.RecipesMainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {


    Button recipes;
    Button personalInfo;

    private static final String isAdmin = "isAdminBundle";

    private String isAdminBundle;

    public Home() { // Required empty public constructor
         }

    public static Home newInstance(String param1) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString("isAdmin", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isAdminBundle = getArguments().getString("isAdmin");
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Bundle bundle = this.getArguments();
        recipes = view.findViewById(R.id.recipesBtn);
        personalInfo = view.findViewById(R.id.personalInfoBtn);

        if (isAdminBundle == "false") {
            personalInfo.setVisibility(view.GONE);
        }

        personalInfo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });

        recipes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RecipesMainActivity.class);
                startActivity(intent);
            }

        });

        return view;
    }


}