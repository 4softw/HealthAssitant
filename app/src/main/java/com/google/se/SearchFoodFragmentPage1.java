package com.google.se;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SearchFoodFragmentPage1 extends Fragment implements View.OnClickListener {
    ListView ListView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arraylist;
    ArrayList<Integer> ticks,lines;
    TextView nameFood;
    ImageView tick,line;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_food_fragment1, container, false);
        nameFood = getView().findViewById(R.id.food_name);
        tick = getView().findViewById(R.id.tick);
        line = getView().findViewById(R.id.line);
        ListView = getView().findViewById(R.id.search_list);
        String[] FoodNames = {"نودل مرغ"};
        Integer tick = R.id.tick;
        Integer line = R.id.line;
        arraylist = new ArrayList<>(Arrays.asList(FoodNames));
        ticks = new ArrayList<>(Arrays.asList(tick));
        lines = new ArrayList<>(Arrays.asList(line));
        adapter = new Adapter(getContext(),arraylist,ticks,lines);
        ListView.setAdapter(adapter);
//        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                startActivity(new Intent(SearchFoodFragmentPage1.this,FoodInfo.class));
//            }
//        });

        return view;
    }


}
