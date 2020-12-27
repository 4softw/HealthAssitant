package com.google.se;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.provider.ContactsContract;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

public class DailyIDataDBHelper extends SQLiteOpenHelper{
    public static final String DataDB="DailyData_db";
    public static final int DatafVersion=1;

    public static final String DataTABLE="DataTable";

    public static String FOODCMD="CREATE TABLE IF NOT EXISTS\"DataTable\" (\n" +
            "\t\"Time\"\tTEXT,\n" +
            "\t\"Name\"\tTEXT,\n" +
            "\t\"Meal\"\tTEXT,\n" +
            "\t\"Colorie\"\tREAL,\n" +
            "\t\"Fat\"\tREAL,\n" +
            "\t\"Protoein\"\tREAL,\n" +
            "\t\"Carbohidrat\"\tREAL\n" +
            ");";

    public static String WATERCMD="CREATE TABLE IF NOT EXISTS\"WATERTABLE\" (\n" +
            "\t\"Time\"\tTEXT,\n" +
            "\t\"Glass\"\tREAL\n" +
            ");";

    public static String POINTCMD="CREATE TABLE IF NOT EXISTS\"POINTTABLE\" (\n" +
            "\t\"Time\"\tTEXT,\n" +
            "\t\"Point\"\tREAL\n" +
            ");";
    public static String SLEEPCMD="CREATE TABLE IF NOT EXISTS\"SLEEP\" (\n" +
            "\t\"Time\"\tTEXT,\n" +
            "\t\"Sleep\"\tTEXT,\n" +
            "\t\"SleepM\"\tTEXT\n" +
            ");";


    public DailyIDataDBHelper(Context context) {
        super(context, DataDB, null, DatafVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(POINTCMD);
        db.execSQL(FOODCMD);
        db.execSQL(WATERCMD);
        db.execSQL(SLEEPCMD);


    }

    public void InsertFood(FoodModel food){
        ContentValues values=new ContentValues();
        values.put("Time",getdate());
        values.put("Name",food.getName());
        values.put("Meal" ,food.getMeal());
        values.put("Colorie",food.getColorie());
        values.put("Fat",food.getFat());
        values.put("Protoein",food.getProtoein());
        values.put("Carbohidrat",food.getCarbohidrat());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(DataTABLE,null,values);
    }
    public List<FoodModel> GetAllFood() {
        SQLiteDatabase db = getReadableDatabase();
        List<FoodModel> foods = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM DataTable", null);
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
    public List<FoodModel> GetTodayFood(String meal){
        SQLiteDatabase db = getReadableDatabase();
        List<FoodModel> foods = new ArrayList<>();
        Calendar mCalendar = Calendar.getInstance();
        String todayDate =
                String.valueOf(mCalendar.get(Calendar.MONTH)) + "/" +
                        String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(mCalendar.get(Calendar.YEAR));
        Cursor cursor = db.rawQuery("SELECT * FROM DataTable  WHERE  Time=?",new String[]{todayDate} );
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
    public List<FoodModel> GetTodayMealFood(String meal){
        SQLiteDatabase db = getReadableDatabase();
        List<FoodModel> foods = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM DataTable  WHERE  Time=? AND Meal=?",new String[]{getdate(),meal} );
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
    public List<FoodModel> SearchDailyFood(String name){
        List<FoodModel> foods=new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + DataTABLE + " where " + "Name  like?  AND Time  =?" ,
                new String[] { "%" + name + "%",getdate() });
        if (cursor.moveToFirst()){
            do{
                FoodModel food=new FoodModel();
                food.setName(cursor.getString(cursor.getColumnIndex("Name")));
                food.setTime(cursor.getString(cursor.getColumnIndex("Time")));
                food.setMeal(cursor.getString(cursor.getColumnIndex("Meal")));
                food.setColorie(cursor.getDouble(cursor.getColumnIndex("Colorie")));
                food.setFat(cursor.getDouble(cursor.getColumnIndex("Fat")));
                food.setCarbohidrat(cursor.getDouble(cursor.getColumnIndex("Carbohidrat")));
                food.setProtoein(cursor.getDouble(cursor.getColumnIndex("Protoein")));
                foods.add(food);
            }while (cursor.moveToNext());
        }
        return foods;
    }
    public void DeletDailyFood(String Name,String Time){
        SQLiteDatabase db=getWritableDatabase();
        db.delete("DataTable","Name =? AND Time=?",new String[]{Name,Time});
    }


    public void Insertwater(WaterModel water){
        ContentValues values=new ContentValues();
        values.put("Glass",water.getGlass());
        values.put("Time",water.time);
        SQLiteDatabase db=getWritableDatabase();
        db.insert("WATERTABLE",null,values);
    }
    public void InsertPoint(PointModel point){
        ContentValues values=new ContentValues();
        values.put("Point",point.getPoint());
        values.put("Time",point.getDate());
        SQLiteDatabase db=getWritableDatabase();
        db.insert("POINTTABLE",null,values);
    }
    public void InsertSleep(SleepModel sleep){
        ContentValues values=new ContentValues();
        values.put("Sleep",sleep.getSleep());
        values.put("SleepM",sleep.getSleepM());
        values.put("Time",sleep.getDate());
        SQLiteDatabase db=getWritableDatabase();
        db.insert("SLEEP",null,values);
    }
    public List<WaterModel> GetWater(){
        SQLiteDatabase db = getReadableDatabase();
        List<WaterModel> w = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM WATERTABLE  WHERE  Time=? ",new String[]{getdate()} );
        if (cursor.moveToFirst()) {
            do {
                WaterModel waterModel = new WaterModel();
                waterModel.setGlass(cursor.getInt(cursor.getColumnIndex("Glass")));
                w.add(waterModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return w;
    }
    public List<SleepModel> GetSleep(){
        SQLiteDatabase db = getReadableDatabase();
        List<SleepModel> p = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM SLEEP  WHERE  Time=? ",new String[]{getdate()} );
        if (cursor.moveToFirst()) {
            do {
                SleepModel SleepTracker = new SleepModel();
                SleepTracker.setSleep(cursor.getString(cursor.getColumnIndex("Sleep")));
                SleepTracker.setSleepM(cursor.getString(cursor.getColumnIndex("SleepM")));
                p.add(SleepTracker);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return p;
    }
    public List<PointModel> GetPoints(){
        SQLiteDatabase db = getReadableDatabase();
        List<PointModel> p = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM POINTTABLE  WHERE  Time=? ",new String[]{getdate()} );
        if (cursor.moveToFirst()) {
            do {
                PointModel points = new PointModel();
                points.setPoint(cursor.getInt(cursor.getColumnIndex("Point")));
                p.add(points);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return p;
    }
    public void updateWater(ContentValues glass){
        SQLiteDatabase database=getWritableDatabase();
        database.update("WATERTABLE",glass,"Time"+" = "+getdate() ,null);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public String getdate(){
        Calendar mCalendar = Calendar.getInstance();
        String todayDate =
                String.valueOf(mCalendar.get(Calendar.MONTH)) + "/" +
                        String.valueOf(mCalendar.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(mCalendar.get(Calendar.YEAR));
        return todayDate;
    }
}
