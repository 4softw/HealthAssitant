package com.google.se;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

public class FoodInfo extends AppCompatActivity {
    TextView name,colori,fat,pro,carbo,grtext;
    com.warkiz.widget.IndicatorSeekBar gr;
    int meghdar;
    Button ok,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);
        name=findViewById(R.id.nameOfFood);
        colori=findViewById(R.id.coloriOfFood);
        fat=findViewById(R.id.fPersent);
        pro=findViewById(R.id.pPersent);
        carbo=findViewById(R.id.cPersent);
        grtext=findViewById(R.id.grtext);
        gr=findViewById(R.id.grOfFood);
        name.setText(FoodPage.nameOffood);
        colori.setText(String.valueOf(FoodPage.coloriOfffod));
        fat.setText(String.valueOf(FoodPage.fpercent));
        pro.setText(String.valueOf(FoodPage.pPersent));
        ok=findViewById(R.id.ok);
        carbo.setText(String.valueOf(FoodPage.cPersent));
        cancel=findViewById(R.id.cancel);
        gr.setProgress((float)FoodPage.gr);
        gr.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                meghdar=seekParams.progress;
                double check=(double)meghdar;
                if (check==0){
                    meghdar=1;
                }
                String c=colori.getText().toString();
                colori.setText(String.valueOf(((double)meghdar)*(FoodPage.coloriOfffod)/100));
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodPage.currentColoritxt=Double.parseDouble(colori.getText().toString());
                FoodModel food=new FoodModel();
                food.setName(name.getText().toString());
                food.setProtoein(Double.parseDouble(pro.getText().toString()));
                food.setFat(Double.parseDouble(fat.getText().toString()));
                food.setCarbohidrat(Double.parseDouble(carbo.getText().toString()));
                food.setColorie(Double.parseDouble(colori.getText().toString()));
                food.setMeal(FoodPage.meal);
                FoodPage.AddedFood.add(food);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}