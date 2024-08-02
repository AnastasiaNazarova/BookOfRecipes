package com.example.bookofrecipes.model;

public class Recipe {

    int id;
    String recipeTitle;
    String category;
    String portions;
    String cookingTime;
    String numIngredients;


    public Recipe(int id, String recipeTitle, String category,  String portions, String cookingTime, String numIngredients) {
        this.id = id;
        this.recipeTitle = recipeTitle;
        this.category = category;
        this.portions = portions;
        this.cookingTime = cookingTime;
        this.numIngredients = numIngredients;

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRecipeTitle(String name) {
        this.recipeTitle = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setNumIngredients(String numIngredients) {
        this.numIngredients = numIngredients;
    }

    public void setPortions(String portions) {
        this.portions = portions;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }


    public int getId() {
        return id;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getCategory() {
        return category;
    }

    public String getNumIngredients() {
        return numIngredients;
    }

    public String getPortions() {
        return portions;
    }

    public String getCookingTime() {
        return cookingTime;
    }



}
