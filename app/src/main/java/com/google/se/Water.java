package com.google.se;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Water extends AppCompatActivity {
    TextView back,liter,glass;
    ImageView plus,minus;
    int glasses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        back = findViewById(R.id.back);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        Bundle bundle = getIntent().getExtras();
        final String glasss = bundle.getString("glass");
        liter = findViewById(R.id.liter);
        glass = findViewById(R.id.glass);
        glasses = Integer.parseInt(glasss);
        glasses = glasses+ 1;
        liter.setText(glasses*4 + "ml");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Water.this,HomePage.class));
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                glasses = glasses+ 1;
                liter.setText(glasses*200 + "ml");
                glass.setText(glasses);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                glasses = glasses - 1;
                liter.setText(glasses*4 + "ml");
                glass.setText(glasses);

            }
        });

    }
}