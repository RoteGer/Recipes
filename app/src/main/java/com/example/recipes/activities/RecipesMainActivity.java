package com.example.recipes.activities;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.R;
import com.example.recipes.adapters.Recipes_RecyclerViewAdapter;
import com.example.recipes.models.Recipes;
import com.example.recipes.services.DataService;

import java.util.ArrayList;
import java.util.List;

public class RecipesMainActivity extends AppCompatActivity {

    private ArrayList<Recipes> dataSet;

    private RecyclerView recycleView;
    private LinearLayoutManager layoutManager;
    private Recipes_RecyclerViewAdapter adapter;
    private List<ClipData.Item> itemList;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_main);

        DataService ds = new DataService();

        // Get to adapter



        recycleView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);

        recycleView.setItemAnimator(new DefaultItemAnimator());

        adapter = new Recipes_RecyclerViewAdapter(ds.getArrRecipes(),this);
        recycleView.setAdapter(adapter);

    }



}