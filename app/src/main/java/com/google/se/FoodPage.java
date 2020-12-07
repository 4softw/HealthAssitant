package com.google.se;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FoodPage extends AppCompatActivity {
    TextView back;
    ImageView breakfast,lunch,dinner,drink,snack;
    ProgressBar calorieProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_page);
        back = findViewById(R.id.back);
        breakfast = findViewById(R.id.breakfast);
        lunch = findViewById(R.id.lunch);
        dinner = findViewById(R.id.dinner);
        drink = findViewById(R.id.drinks);
        snack = findViewById(R.id.snack);
//        lunch.setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FoodPage.this,HomePage.class));
            }
        });
        calorieProgress = findViewById(R.id.progressCalorie);
        calorieProgress.setProgress(50);



    }
}