package com.example.bookofrecipes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookofrecipes.R;
import com.example.bookofrecipes.RecipeInfoActivity;
import com.example.bookofrecipes.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    Context context;
    List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
       View recipeItems = LayoutInflater.from(context).inflate(R.layout.recipe_item,parent,false);
        return new RecipeViewHolder(recipeItems);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.recipeTitle.setText(recipes.get(position).getRecipeTitle());
        holder.category.setText(recipes.get(position).getCategory());
        holder.cookingTime.setText(recipes.get(position).getCookingTime());
        holder.portions.setText(recipes.get(position).getPortions());
        holder.numIngredients.setText( recipes.get(position).getNumIngredients() + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RecipeInfoActivity.class);
                intent.putExtra("id",recipes.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static final class RecipeViewHolder extends RecyclerView.ViewHolder
    {
        TextView recipeTitle;
        TextView category;
        TextView numIngredients;
        TextView portions;
        TextView cookingTime;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeTitle = itemView.findViewById(R.id.recipeTitle);
            category = itemView.findViewById(R.id.category);
            numIngredients = itemView.findViewById(R.id.numIngredients);
            portions = itemView.findViewById(R.id.portions);
            cookingTime = itemView.findViewById(R.id.cookingTime);
        }
    }
}
