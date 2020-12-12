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
import java.util.List;

public class FoodPage extends AppCompatActivity {
    TextView  back,time,allColori,currentColori;
    ImageView breakfast,lunch,dinner,drink,snack;
    ProgressBar calorieProgress;
    static String nameOffood;
    static double gr,coloriOfffod,cPersent,pPersent,fpercent;
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
            }
        });

        calorieProgress = findViewById(R.id.progressCalorie);
        calorieProgress.setMax((int)HomePage.calculatingColori(HomePage.person.get(0).getSex()));
        allColori.setText(String.valueOf(HomePage.calculatingColori(HomePage.person.get(0).getSex())));

    }
}