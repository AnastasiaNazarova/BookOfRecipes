package com.example.bookofrecipes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
import java.util.HashMap;
import java.util.List;

public class RecipeNewActivity extends AppCompatActivity {

    EditText etRecipeTitle,etPortions,etCookingTime,
            etIngredient,etCountIngredient,etInstruction;
    Spinner spCategory,spUnitTime,spUnitMass;
    Button btnAddRecipe;

    DBHelper dbHelper;

    RecyclerView rvIngredients, rvInstructions;
    IngredientAdapter ingredientAdapter;
    InstructionAdapter instructionAdapter;
    ArrayList<Ingredient> ingredientArrayList;
    ArrayList<Instruction> instructionArrayList;

    String[] CategoryData = {"Завтраки", "Супы", "Закуски", "Напитки", "Основные блюда", "Салаты", "Соусы", "Десерты"};
    String[] UnitTimeData= {"мин", "ч"};
    String[] UnitMassData = {"г", "шт"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_new);

        dbHelper = new DBHelper(this);

        initViewElement();
        onCreateSpinner();

    }


    private  void initViewElement()
    {
        btnAddRecipe =(Button)findViewById(R.id.btnAddRecipe);
        etRecipeTitle = (EditText) findViewById(R.id.etRecipeTitle);
        etCookingTime = (EditText) findViewById(R.id.etCookingTime);
        etIngredient = (EditText) findViewById(R.id.etIngredient);
        etInstruction = (EditText) findViewById(R.id.etInstruction);
        etPortions = (EditText) findViewById(R.id.etPortions);
        etCountIngredient = (EditText) findViewById(R.id.etCountIngredient);

        ingredientArrayList = new ArrayList<>();
        instructionArrayList = new ArrayList<>();

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

    //Создание выпадающих списков
    private void onCreateSpinner()
    {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CategoryData);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory = (Spinner) findViewById(R.id.spCategory);
        spCategory.setAdapter(categoryAdapter);
        spCategory.setPrompt("Категории блюд");

        ArrayAdapter<String> unitTimeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, UnitTimeData);
        unitTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUnitTime = (Spinner) findViewById(R.id.spUnitTime);
        spUnitTime.setAdapter(unitTimeAdapter);

        ArrayAdapter<String> unitMassAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, UnitMassData);
        unitMassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUnitMass = (Spinner) findViewById(R.id.spUnitMass);
        spUnitMass.setAdapter(unitMassAdapter);
    }

    //добавление ингредиента в список
    public  void onBtnAddIngredientClick(View v)
    {
        ingredientArrayList.add(new Ingredient(etIngredient.getText().toString(),
                etCountIngredient.getText().toString() + " " + spUnitMass.getSelectedItem().toString()));

        ingredientAdapter.notifyItemInserted(ingredientArrayList.size() -1);
    }

    //добавление шагов рецепта
    public  void onBtnAddInstructionClick(View v)
    {
        instructionArrayList.add(new Instruction( instructionArrayList.size(), etInstruction.getText().toString()));
        instructionAdapter.notifyItemInserted(instructionArrayList.size() -1);
    }

    //добавление нового рецепта в бд
    public void onBtnAddRecipeClick(View v) throws JSONException {

        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String recipeTitle = etRecipeTitle.getText().toString();
        String category = spCategory.getSelectedItem().toString();
        String cookingTime = etCookingTime.getText().toString() + " " + spUnitTime.getSelectedItem().toString();
        int portions = Integer.parseInt(etPortions.getText().toString());
        String ingredients = ingredientArrayList.toString();
        ingredients = "{\"ingredients\":" + ingredients + "}";

        String instructions = instructionArrayList.toString();
        instructions = "{\"instructions\":" + instructions + "}";

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // подготовим данные для вставки в виде пар: наименование столбца - значение
        cv.put(dbHelper.RECIPE_KEY_TITLE , recipeTitle);
        cv.put(dbHelper.RECIPE_KEY_CATEGORY, category);
        cv.put(dbHelper.RECIPE_KEY_COOKING_TIME, cookingTime);
        cv.put(dbHelper.RECIPE_KEY_PORTION, portions);
        cv.put(dbHelper.RECIPE_KEY_INGREDIENT, ingredients);
        cv.put(dbHelper.RECIPE_KEY_INSTRUCTION, instructions);

        // вставляем запись и получаем ее ID
        long rowID = db.insert(dbHelper.RECIPE_TABLE_NAME, null, cv);

    }
}
