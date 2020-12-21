package com.google.se;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

public class AddNewFoodDBHelper extends SQLiteOpenHelper {

    public static final String NewFoodDB="AddNewFoodDB";
    public static final int NewFoodVersion=1;

    public static final String NEW_FOODS ="NewFoods";

    public static String FOODCMD="CREATE TABLE IF NOT EXISTS\"NewFoods\" (\n" +
            "\t\"Time\"\tTEXT,\n" +
            "\t\"Name\"\tTEXT,\n" +
            "\t\"Meal\"\tTEXT,\n" +
            "\t\"Colorie\"\tREAL,\n" +
            "\t\"Fat\"\tREAL,\n" +
            "\t\"Protoein\"\tREAL,\n" +
            "\t\"Carbohidrat\"\tREAL\n" +
            ");";

    public AddNewFoodDBHelper(Context context) {
        super(context, NewFoodDB, null, NewFoodVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FOODCMD);
    }


    public void InsertNewFood(FoodModel food){
        ContentValues values=new ContentValues();
        PersianCalendar persianCalendar=new PersianCalendar();
        values.put("Time",persianCalendar.getPersianLongDate());
        values.put("Name",food.getName());
        values.put("Meal" ,food.getMeal());
        values.put("Colorie",food.getColorie());
        values.put("Fat",food.getFat());
        values.put("Protoein",food.getProtoein());
        values.put("Carbohidrat",food.getCarbohidrat());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(NEW_FOODS,null,values);
    }

    public List<FoodModel> GetAllNewFoods() {
        SQLiteDatabase db = getReadableDatabase();
        List<FoodModel> foods = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM NewFoods", null);
        if (cursor.moveToFirst()) {
            do {
                FoodModel food = new FoodModel();
                food.setTime(cursor.getString(cursor.getColumnIndex("Time")));
                food.setName(cursor.getString(cursor.getColumnIndex("Name")));
                food.setMeal(cursor.getString(cursor.getColumnIndex("Meal")));
                food.setColorie(cursor.getDouble(cursor.getColumnIndex("Colorie")));
                food.setFat(cursor.getDouble(cursor.getColumnIndex("Fat")));
                food.setCarbohidrat(cursor.getDouble(cursor.getColumnIndex("Carbohidrat")));
                food.setProtoein(cursor.getDouble(cursor.getColumnIndex("Protoein")));
                foods.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return foods;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
