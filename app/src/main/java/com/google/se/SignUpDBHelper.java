package com.google.se;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SignUpDBHelper extends SQLiteOpenHelper {
    public static final String Signup="signup_db";
    public static final int SignupVersion=1;

    public static final String STABLE="SignupTable";
    public static final String ITABLE="InfTable";
    public static final String SCMD="CREATE TABLE IF NOT EXISTS\"SignupTable\" (\n" +
            "\t\"Id\"\tTEXT UNIQUE,\n" +
            "\t\"Password\"\tTEXT\n" +
            ");";

    public static final String ICMD="CREATE TABLE IF NOT EXISTS\"InfTable\" (\n" +
            "\t\"Id\"\tTEXT UNIQUE,\n" +
            "\t"+InformationModel.KEY_NAME+"\tTEXT ,\n" +
            "\t"+InformationModel.KEY_LNAME+"\tTEXT ,\n" +
            "\t"+InformationModel.KEY_W+"\tTEXT ,\n" +
            "\t"+InformationModel.KEY_H+"\tTEXT ,\n" +
            "\t"+InformationModel.KEY_AGE+"\tTEXT ,\n" +
            "\t"+InformationModel.KEY_SEX+"\tTEXT \n" +
            ");";
    public SignUpDBHelper(Context context) {
        super(context, Signup, null, SignupVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCMD);
        db.execSQL(ICMD);
    }


    public void Insert(Signup signup){
        ContentValues values=new ContentValues();
        values.put("Id",signup.getId());
        values.put("Password",signup.getPassword());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(STABLE,null,values);
    }

    public List<Signup> GetAll(){
        SQLiteDatabase db=getReadableDatabase();
        List<Signup> personList=new ArrayList<>();
        Cursor cursor= db.rawQuery("SELECT * FROM SignupTable",null);
        if (cursor.moveToFirst()){
            do{
                Signup person=new Signup();
                person.setId(cursor.getString(cursor.getColumnIndex("Id")));
                person.setPassword(cursor.getString(cursor.getColumnIndex("Password")));
                personList.add(person);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return personList;
    }

    public List<InformationModel> GetInf() {
        SQLiteDatabase db = getReadableDatabase();
        List<InformationModel> model = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM InfTable", null);
        if (cursor.moveToFirst()) {
            do {
                InformationModel person = new InformationModel();
                person.setId(cursor.getString(cursor.getColumnIndex("Id")));
                person.setName(cursor.getString(cursor.getColumnIndex("Name")));
                person.setLastname(cursor.getString(cursor.getColumnIndex("LastName")));
                person.setWeight(cursor.getString(cursor.getColumnIndex("Weight")));
                person.setHeight(cursor.getString(cursor.getColumnIndex("Height")));
                person.setAge(cursor.getString(cursor.getColumnIndex("Age")));
                person.setSex(cursor.getString(cursor.getColumnIndex("Sex")));
                model.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return model;
    }


    public void InsertInf(InformationModel person){
        ContentValues values=new ContentValues();
        values.put(InformationModel.KEY_ID,person.getId());
        values.put(InformationModel.KEY_NAME,person.getName());
        values.put(InformationModel.KEY_LNAME,person.getLastname());
        values.put(InformationModel.KEY_W,person.getWeight());
        values.put(InformationModel.KEY_H,person.getHeight());
        values.put(InformationModel.KEY_AGE,person.getAge());
        values.put(InformationModel.KEY_SEX,person.getSex());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(ITABLE,null,values);
    }

    public void update(String id,ContentValues values){
        SQLiteDatabase database=getWritableDatabase();
        database.update("InfTable",values,"Name"+" = "+id ,null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
