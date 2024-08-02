package com.example.bookofrecipes.model;

public class Ingredient {

    String ingredient;
    String quantity;

    public Ingredient( String ingredient, String quantity) {

        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "{" +
                "\"ingredient\":\"" + ingredient + "\"" +
                ", \"quantity\":\"" + quantity + "\"" +
                '}';
    }
}

