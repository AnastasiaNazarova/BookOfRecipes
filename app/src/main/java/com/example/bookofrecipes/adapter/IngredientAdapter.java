package com.example.bookofrecipes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookofrecipes.R;
import com.example.bookofrecipes.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    Context context;
    List<Ingredient> ingredients;


    public IngredientAdapter( Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ingredientItems = LayoutInflater.from(context).inflate(R.layout.ingredient_item,parent,false);
        return new IngredientAdapter.IngredientViewHolder(ingredientItems);
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.IngredientViewHolder holder, int position) {
        holder.ingredient.setText( String.valueOf(ingredients.get(position).getIngredient()));
        holder.quantity.setText( ingredients.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static final class IngredientViewHolder extends RecyclerView.ViewHolder
    {
        TextView ingredient;
        TextView quantity;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.ingredient);
            quantity = itemView.findViewById(R.id.quantity);
        }
    }
}
