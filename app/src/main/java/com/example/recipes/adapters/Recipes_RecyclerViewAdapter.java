package com.example.recipes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipes.R;
import com.example.recipes.models.Recipes;

import java.util.ArrayList;

public class Recipes_RecyclerViewAdapter extends RecyclerView.Adapter<Recipes_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Recipes> recipesModels;

    public Recipes_RecyclerViewAdapter(ArrayList<Recipes> recipesModels,Context context) {
        this.recipesModels = recipesModels;
        this.context = context;
    };
    @NonNull
    @Override
    public Recipes_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cards_layout, parent, false);
        return new Recipes_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Recipes_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // assigning values to the views we created in the recycler_view_row layout file
        // based on the position of the recycler view

        holder.title.setText(recipesModels.get(position).getTitle());
        holder.description.setText(recipesModels.get(position).getSummary());
//        holder.imageView.setImageResource(recipesModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {

        return recipesModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title, description;
        public MyViewHolder (@NonNull View itemView) {
            super (itemView);

            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.textView);
            description = itemView.findViewById(R.id.textView2);
        }
    }
}
