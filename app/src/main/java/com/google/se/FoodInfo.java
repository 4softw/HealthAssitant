package com.google.se;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

public class FoodInfo extends AppCompatActivity {
    TextView name,colori,fat,pro,carbo,grtext;
    com.warkiz.widget.IndicatorSeekBar gr;

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
        carbo.setText(String.valueOf(FoodPage.cPersent));
        grtext.setText(String.valueOf(FoodPage.gr));
//        gr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gr.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
//                    @Override
//                    public void valueChanged(Number value) {
//                        grtext.setText(String.valueOf(value));
//                        pro.setText(String.valueOf((Double.parseDouble(grtext.getText().toString())/100*(Double.parseDouble(pro.getText().toString())))));
//                        colori.setText(String.valueOf((Double.parseDouble(grtext.getText().toString())/100*(Double.parseDouble(pro.getText().toString())))));
//                        carbo.setText(String.valueOf((Double.parseDouble(grtext.getText().toString())/100*(Double.parseDouble(pro.getText().toString())))));
//                        fat.setText(String.valueOf((Double.parseDouble(grtext.getText().toString())/100*(Double.parseDouble(pro.getText().toString())))));
//                    }
//                });
//            }
//        });
     //   gr.setProgress(Integer.parseInt(grtext.getText().toString()));

    }
}