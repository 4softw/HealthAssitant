package com.google.se;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.List;

public class AddedFoofFragment extends Fragment {
    RecyclerView list;
    ImageView center;
    SearchView search;
    DailyIDataDBHelper dailyIDataDBHelper;
    static List<FoodModel> addfood;
    static AddedFoodAdapter addedFoodAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_food_fragment, container, false);
        dailyIDataDBHelper=new DailyIDataDBHelper(getContext());
        addfood=dailyIDataDBHelper.GetTodayMealFood(FoodPage.mealtxt);
        list=view.findViewById(R.id.list);
        center=view.findViewById(R.id.center);
        LinearLayoutManager linear = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        addedFoodAdapter=new AddedFoodAdapter(getContext(),addfood);
        list.setLayoutManager(linear);
        search=view.findViewById(R.id.serch);
        list.setAdapter(addedFoodAdapter);
  //      Toast.makeText(getContext(), ""+addfood.size(), Toast.LENGTH_SHORT).show();
        if (FoodPage.AddedFood.size()!=0){
            center.setVisibility(View.GONE);
        }

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                addfood=dailyIDataDBHelper.SearchDailyFood(newText);
                AddedFoodAdapter addedFoodAdapter=new AddedFoodAdapter(getContext(),addfood);
                list.setAdapter(addedFoodAdapter);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        addfood=dailyIDataDBHelper.GetTodayMealFood(FoodPage.mealtxt);
        addedFoodAdapter=new AddedFoodAdapter(getContext(),addfood);
        list.setAdapter(addedFoodAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        addfood=dailyIDataDBHelper.GetTodayMealFood(FoodPage.mealtxt);
        addedFoodAdapter=new AddedFoodAdapter(getContext(),addfood);
        list.setAdapter(addedFoodAdapter);
    }
}
