package com.google.se;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewFood extends AppCompatActivity {

    EditText name,colori,carbo,pro,fat;
    Button add;
    boolean n=true,co=true,ca=true,p=true,f=true;
    AddNewFoodDBHelper addNewFoodDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_food);
        addNewFoodDBHelper=new AddNewFoodDBHelper(this);
        name=findViewById(R.id.nName);
        carbo=findViewById(R.id.nCarbo);
        colori=findViewById(R.id.nColori);
        pro=findViewById(R.id.nPro);
        fat=findViewById(R.id.nFat);
        add=findViewById(R.id.add);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for (int i=0;i<addNewFoodDBHelper.GetAllNewFoods().size();i++) {
                    if (addNewFoodDBHelper.GetAllNewFoods().get(i).getName().equals(name.getText().toString())) {
                        Toast.makeText(AddNewFood.this, "قبلا این غذارو ثبت  کردی", Toast.LENGTH_SHORT).show();
                        name.setError("این غذاز رو قبلا ثبت کردی");
                        n = false;
                    }
                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().equals("")){
                    name.setError("لطفا نام را وارد کنید");
                    n=false;
                }
                if (carbo.getText().toString().trim().equals("")){
                    carbo.setError("لطفا کربوهیدرات را وارد کنید");
                    ca=false;
                }
                if (colori.getText().toString().trim().equals("")){
                    colori.setError("لطفاکالری را وارد کنید");
                    co=false;

                }
                if (fat.getText().toString().trim().equals("")){
                    fat.setError("لطفاچربی را وارد کنید");
                    f=false;
                }
                if (pro.getText().toString().trim().equals("")){
                    pro.setError("لطفا پروتئین را وارد کنید");
                    p=false;
                }

                if (n==true && ca==true&& ca==true&&f==true&&p==true){
                    FoodModel food=new FoodModel();
                    food.setName(name.getText().toString().trim());
                    food.setProtoein(Double.parseDouble(pro.getText().toString().trim()));
                    food.setCarbohidrat(Double.parseDouble(carbo.getText().toString().trim()));
                    food.setFat(Double.parseDouble(fat.getText().toString().trim()));
                    food.setColorie(Double.parseDouble(colori.getText().toString().trim()));
                    addNewFoodDBHelper.InsertNewFood(food);
                    finish();
                }
            }
        });
    }
}
