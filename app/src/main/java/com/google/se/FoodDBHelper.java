package com.google.se;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FoodDBHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "DataOfFood.db";
    private static final int DATABASE_VERSION = 1;



    public FoodDBHelper(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION );
    }
}

