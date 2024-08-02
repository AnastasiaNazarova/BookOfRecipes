package com.example.bookofrecipes;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookofrecipes.adapter.RecipeAdapter;
import com.example.bookofrecipes.model.Ingredient;
import com.example.bookofrecipes.model.Recipe;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    RecyclerView recipeRecycle;
    List<Recipe> recipeList;

    Button btnAddRecipe;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        readRecipeList();

        btnAddRecipe =(Button)findViewById(R.id.btnAddRecipe);

    }

    //считываем список рецептов из бд
    private  void readRecipeList()
    {
        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        recipeList = new ArrayList<>();

        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.query(dbHelper.RECIPE_TABLE_NAME, null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int recipeTitleColIndex = c.getColumnIndex(dbHelper.RECIPE_KEY_TITLE);
            int categoryColIndex = c.getColumnIndex(dbHelper.RECIPE_KEY_CATEGORY);
            int cookingTimeColIndex = c.getColumnIndex(dbHelper.RECIPE_KEY_COOKING_TIME);
            int portionColIndex = c.getColumnIndex(dbHelper.RECIPE_KEY_PORTION);
            int ingredientColIndex = c.getColumnIndex(dbHelper.RECIPE_KEY_INGREDIENT);
            int instructionColIndex = c.getColumnIndex(dbHelper.RECIPE_KEY_INSTRUCTION);

            do {

                String ingredients =  c.getString(ingredientColIndex);
                int numIngredients =0;
                try {
                    JSONObject jsonObject = new JSONObject(ingredients);
                    JSONArray jsonArray = jsonObject.getJSONArray("ingredients");
                    numIngredients = jsonArray.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // получаем значения по номерам столбцов и добавляем в список
                recipeList.add(new Recipe(
                        c.getInt(idColIndex),c.getString(recipeTitleColIndex),
                        c.getString(categoryColIndex), c.getInt(portionColIndex) + " порций",
                        c.getString(cookingTimeColIndex) ,numIngredients + "ингредиентов" ));
            }
            while (c.moveToNext());
        }
        c.close();

        setRecipeRecycle(recipeList);
    }


    private void setRecipeRecycle(List<Recipe> recipeList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);

        recipeRecycle = findViewById(R.id.recipeRecycle);
        recipeRecycle.setLayoutManager(layoutManager);

        RecipeAdapter recipeAdapter = new RecipeAdapter(this,recipeList);
        recipeRecycle.setAdapter(recipeAdapter);
    }

    public void onBtnAddRecipeClick(View v)
    {
        Intent intent = new Intent(v.getContext(), RecipeNewActivity.class);
        v.getContext().startActivity(intent);
    }

}