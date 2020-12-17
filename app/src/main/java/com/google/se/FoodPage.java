package com.google.se;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FoodPage extends AppCompatActivity {
    TextView  back,time,allColori,currentColori;
    ImageView breakfast,lunch,dinner,drink,snack;
    ProgressBar calorieProgress;
    static String nameOffood;
    static  String meal;
    static double gr,coloriOfffod,cPersent,pPersent,fpercent,currentColoritxt=0;
    static List<FoodModel> AddedFood=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_page);
        time=findViewById(R.id.time);
        PersianCalendar persianCalendar=new PersianCalendar();
        time.setText(persianCalendar.getPersianLongDate());
        back = findViewById(R.id.back);
        allColori=findViewById(R.id.allColori);
        currentColori=findViewById(R.id.currentColori);
        breakfast = findViewById(R.id.breakfast);
        lunch = findViewById(R.id.lunch);
        dinner = findViewById(R.id.dinner);
        drink = findViewById(R.id.drinks);
        snack = findViewById(R.id.snack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodPage.this,HomePage.class));
            }
        });
        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodPage.this,searchFood.class));
                meal="صبحانه مورد نظر خود را جست و جو کنید";
            }
        });

        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodPage.this,searchFood.class));
                meal="ناهار مورد نظر خودرا جست و جو کنید";
            }
        });

        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodPage.this,searchFood.class));
                meal="شام  مورد نظر خودرا جست و جو کنید";
            }
        });
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodPage.this,searchFood.class));
                meal="نوشیدنی  مورد نظر خودرا جست و جو کنید";
            }
        });

        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodPage.this,searchFood.class));
                meal="میان وعده  مورد نظر خودرا جست و جو کنید";
            }
        });


        calorieProgress = findViewById(R.id.progressCalorie);
        calorieProgress.setMax((int)HomePage.calculatingColori(HomePage.person.get(0).getSex()));
        allColori.setText(String.valueOf((int)HomePage.calculatingColori(HomePage.person.get(0).getSex())));


    }

    @Override
    protected void onResume() {
        super.onResume();
        currentColori.setText(String.valueOf(currentColoritxt));
    }
}