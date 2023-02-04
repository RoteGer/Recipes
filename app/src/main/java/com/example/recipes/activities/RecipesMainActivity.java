package com.example.recipes.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.recipes.R;
import com.example.recipes.adapters.Recipes_RecyclerViewAdapter;
import com.example.recipes.models.Recipes;
import com.example.recipes.services.DataService;

import java.util.ArrayList;

public class RecipesMainActivity extends AppCompatActivity {

    private ArrayList<Recipes> dataSet;

    private RecyclerView recycleView;
    private LinearLayoutManager layoutManager;
    private Recipes_RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_main);

        DataService ds = new DataService();
        ArrayList<Recipes> arr = ds.getArrRecipes();

        // Get to adapter

        recycleView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);

        recycleView.setItemAnimator(new DefaultItemAnimator());

        Recipes_RecyclerViewAdapter c = new Recipes_RecyclerViewAdapter(arr);
        recycleView.setAdapter(c);

    }
}