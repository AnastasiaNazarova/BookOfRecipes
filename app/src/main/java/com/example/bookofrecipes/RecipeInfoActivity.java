package com.example.bookofrecipes;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookofrecipes.adapter.IngredientAdapter;
import com.example.bookofrecipes.adapter.InstructionAdapter;
import com.example.bookofrecipes.model.Ingredient;
import com.example.bookofrecipes.model.Instruction;
import com.example.bookofrecipes.model.Recipe;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RecipeInfoActivity extends AppCompatActivity {
    private static final int CM_DELETE_ID = 1;

    RecyclerView rvIngredients, rvInstructions;
    IngredientAdapter ingredientAdapter;
    InstructionAdapter instructionAdapter;
    ArrayList<Ingredient> ingredientArrayList;
    ArrayList<Instruction> instructionArrayList;

    DBHelper dbHelper;
    int idRecipe;

    TextView tvRecipeTitle,tvCookingTime;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_info);

        dbHelper = new DBHelper(this);

        Bundle arguments = getIntent().getExtras();
        idRecipe = Integer.parseInt( arguments.get("id").toString());

        tvRecipeTitle = (TextView)findViewById(R.id.recipeTitle);
        tvCookingTime = (TextView)findViewById(R.id.cookingTime);

        readRecipeInfo();
        setRecycle();
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {

            return true;
        }
        return super.onContextItemSelected(item);
    }



    //считываем информацию из БД
    private  void readRecipeInfo()
    {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ingredientArrayList = new ArrayList<>();
        instructionArrayList = new ArrayList<>();

        Cursor c = db.query(dbHelper.RECIPE_TABLE_NAME,
                new String[] {dbHelper.RECIPE_KEY_TITLE, dbHelper.RECIPE_KEY_COOKING_TIME,
                        dbHelper.RECIPE_KEY_INGREDIENT, dbHelper.RECIPE_KEY_INSTRUCTION},
                "id = ?",
                new String[] {String.valueOf(idRecipe)},
                null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int recipeTitleColIndex = c.getColumnIndex(dbHelper.RECIPE_KEY_TITLE);
            int cookingTimeColIndex = c.getColumnIndex(dbHelper.RECIPE_KEY_COOKING_TIME);
            int ingredientColIndex = c.getColumnIndex(dbHelper.RECIPE_KEY_INGREDIENT);
            int instructionColIndex = c.getColumnIndex(dbHelper.RECIPE_KEY_INSTRUCTION);

            tvRecipeTitle.setText(c.getString(recipeTitleColIndex));
            tvCookingTime.setText(c.getString(cookingTimeColIndex));

            ingredientArrayList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(c.getString(ingredientColIndex));
                JSONArray jsonArray = jsonObject.getJSONArray("ingredients");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject userData = jsonArray.getJSONObject(i);
                    ingredientArrayList.add(new Ingredient(userData.getString("ingredient"), userData.getString("quantity")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            instructionArrayList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(c.getString(instructionColIndex));
                JSONArray jsonArray = jsonObject.getJSONArray("instructions");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject userData = jsonArray.getJSONObject(i);
                    instructionArrayList.add(new Instruction(Integer.parseInt(userData.getString("number")), userData.getString("instruction")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            c.close();
        }
    }


    private void setRecycle()
    {
        RecyclerView.LayoutManager layoutManagerIngredient = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rvIngredients =  findViewById(R.id.rvIngredients);
        rvIngredients.setLayoutManager(layoutManagerIngredient);

        RecyclerView.LayoutManager layoutManagerInstruction = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rvInstructions =  findViewById(R.id.rvInstruction);
        rvInstructions.setLayoutManager(layoutManagerInstruction);

        ingredientAdapter = new IngredientAdapter(this,ingredientArrayList);
        rvIngredients.setAdapter(ingredientAdapter);

        instructionAdapter = new InstructionAdapter(this,instructionArrayList);
        rvInstructions.setAdapter(instructionAdapter);
    }
}
