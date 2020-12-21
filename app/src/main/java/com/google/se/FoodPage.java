package com.google.se;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.ArrayList;
import java.util.List;

public class FoodPage extends AppCompatActivity {
    TextView  back,time,allColori,currentColori;
    ImageView breakfast,lunch,dinner,drink,snack;
    ProgressBar calorieProgress;
    static String nameOffood;
    public  static  String meal,mealtxt;
    DailyIDataDBHelper dailyIDataDBHelper;
    static double gr,coloriOfffod,cPersent,pPersent,fpercent,currentColoritxt=0;
    static List<FoodModel> AddedFood=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_page);
        time=findViewById(R.id.time);
        dailyIDataDBHelper=new DailyIDataDBHelper(this);
        PersianCalendar persianCalendar=new PersianCalendar();
        time.setText(persianCalendar.getPersianLongDate());
        back = findViewById(R.id.back);
        AddedFood=dailyIDataDBHelper.GetTodayFood("");
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
                mealtxt="صبحانه";
            }
        });

        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodPage.this,searchFood.class));
                meal="ناهار مورد نظر خودرا جست و جو کنید";
                mealtxt="ناهار";
            }
        });

        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodPage.this,searchFood.class));
                meal="شام  مورد نظر خودرا جست و جو کنید";
                mealtxt="شام";
            }
        });
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodPage.this,searchFood.class));
                meal="نوشیدنی  مورد نظر خودرا جست و جو کنید";
                mealtxt="نوشیدنی";
            }
        });

        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FoodPage.this,searchFood.class));
                meal="میان وعده  مورد نظر خودرا جست و جو کنید";
                mealtxt="میان وعده";
            }
        });


        calorieProgress = findViewById(R.id.progressCalorie);
        calorieProgress.setMax((int)HomePage.calculatingColori(HomePage.person.get(0).getSex()));
        allColori.setText(String.valueOf((int)HomePage.calculatingColori(HomePage.person.get(0).getSex())));
        currentColori.setText(String.valueOf((int)usedColori()));
        calorieProgress.setProgress((int)usedColori());


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentColori.setText(String.valueOf(usedColori()));
        calorieProgress.setProgress((int)usedColori());
    }

    double usedColori(){
        double colori = 0;
        List<FoodModel> list;
        PersianCalendar persianCalendar=new PersianCalendar();
        list=dailyIDataDBHelper.GetTodayFood("");
        for (int i=0;i<list.size();i++){
            colori=colori+list.get(i).getColorie();
        }
        return colori;
    }
}