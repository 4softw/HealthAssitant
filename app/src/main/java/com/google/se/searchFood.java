package com.google.se;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class searchFood extends AppCompatActivity {
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");

        title = findViewById(R.id.title_search);
        title.setText(name+" >");
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(searchFood.this,FoodPage.class));
            }
        });


    }
}