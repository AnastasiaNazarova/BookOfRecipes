package com.example.bookofrecipes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String RECIPE_TABLE_NAME = "RECIPE_TABLE";
    public static final String RECIPE_KEY_TITLE = "recipeTitle";
    public static final String RECIPE_KEY_CATEGORY = "category";
    public static final String RECIPE_KEY_INGREDIENT = "ingredient";
    public static final String RECIPE_KEY_PORTION = "portion";
    public static final String RECIPE_KEY_COOKING_TIME = "cookingTime";
    public static final String RECIPE_KEY_INSTRUCTION = "instruction";



    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table " + RECIPE_TABLE_NAME +" ("
                + "id integer primary key autoincrement,"
                + RECIPE_KEY_TITLE + " text,"
                + RECIPE_KEY_CATEGORY + " text,"
                + RECIPE_KEY_INGREDIENT + " text,"
                + RECIPE_KEY_PORTION + " INTEGER,"
                + RECIPE_KEY_COOKING_TIME + " text,"
                + RECIPE_KEY_INSTRUCTION + " text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("DROP TABLE IF EXISTS recipe");
        //onCreate(db);
    }
}
