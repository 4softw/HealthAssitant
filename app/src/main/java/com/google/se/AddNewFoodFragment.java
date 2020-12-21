package com.google.se;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.List;

public class AddNewFoodFragment extends Fragment {

    RecyclerView list;
    ImageView add;
    AddNewFoodDBHelper addNewFoodDBHelper;
    List<FoodModel> addfood;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_food_fragment, container, false);
        addNewFoodDBHelper =new AddNewFoodDBHelper(getContext());
        addfood= addNewFoodDBHelper.GetAllNewFoods();
        list=view.findViewById(R.id.listNewFood);
        LinearLayoutManager linear = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        AddnewFoodAdapter addedFoodAdapter=new AddnewFoodAdapter(getContext(),addfood);
        list.setLayoutManager(linear);
        list.setAdapter(addedFoodAdapter);
        add=view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AddNewFood.class));
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        addfood= addNewFoodDBHelper.GetAllNewFoods();
        AddnewFoodAdapter addedFoodAdapter=new AddnewFoodAdapter(getContext(),addfood);
        list.setAdapter(addedFoodAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        addfood= addNewFoodDBHelper.GetAllNewFoods();
        AddnewFoodAdapter addedFoodAdapter=new AddnewFoodAdapter(getContext(),addfood);
        list.setAdapter(addedFoodAdapter);
    }
}
