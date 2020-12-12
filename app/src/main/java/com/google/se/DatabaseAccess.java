package com.google.se;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new FoodDBHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<FoodModel> getQuotes() {
        List<FoodModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM  FOODDB", null);
        if (cursor.moveToFirst()){
            do{
                FoodModel foods=new FoodModel();
                foods.setID(cursor.getString(cursor.getColumnIndex("ID")));
                foods.setName(cursor.getString(cursor.getColumnIndex("نام")));
                list.add(foods);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }


    public List<FoodModel> SearchFoods(String name){
        List<FoodModel> foods=new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from " + "FOODDB" + " where " + "نام" + " like ?",
                new String[] { "%" + name + "%" });
        if (cursor.moveToFirst()){
            do{
                FoodModel food=new FoodModel();
                food.setID(cursor.getString(cursor.getColumnIndex("ID")));
                food.setName(cursor.getString(cursor.getColumnIndex("نام")));
                food.setCarbohidrat(cursor.getDouble(cursor.getColumnIndex("کربوهیدرات (g)")));
                food.setColorie(cursor.getDouble(cursor.getColumnIndex("کالری")));
                food.setFat(cursor.getDouble(cursor.getColumnIndex("چربی (g)")));
                food.setGruop(cursor.getString(cursor.getColumnIndex("گروه غذایی")));
                food.setProtoein(cursor.getDouble(cursor.getColumnIndex("پروتئین (g)")));
                foods.add(food);
            }while (cursor.moveToNext());
        }
        return foods;
    }
}
