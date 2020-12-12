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
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFoodFragment extends Fragment implements View.OnClickListener {
    ListView ListView;
    TextView nameFood;
    ImageView tick,line,searchview;
    SearchView search;

    List<FoodModel> quotes;
    DatabaseAccess databaseAccess;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_food_fragment, container, false);
        nameFood = view.findViewById(R.id.food_name);
        tick = view.findViewById(R.id.tick);
        line =view.findViewById(R.id.line);
        ListView =view.findViewById(R.id.search_list);
        searchview=view.findViewById(R.id.searchview);
        search=view.findViewById(R.id.search);
        databaseAccess = DatabaseAccess.getInstance(getContext());


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                databaseAccess.open();
                quotes = databaseAccess.SearchFoods(newText);
                if (quotes.size()!=0){
                    searchview.setVisibility(View.INVISIBLE);
                }
                ArrayAdapter<FoodModel> adapter = new ArrayAdapter<FoodModel>(getContext(), android.R.layout.simple_list_item_activated_1, quotes);
                ListView.setAdapter(adapter);
                return false;
            }
        });
        databaseAccess.close();
        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReadyForInfoPage(position);
                Intent intent = new Intent(getActivity(), FoodInfo.class);
                startActivity(intent);
            }
        });
        return view;
    }

    void ReadyForInfoPage(int position){
        FoodPage.nameOffood=quotes.get(position).getName().toString();
        FoodPage.coloriOfffod=quotes.get(position).getColorie();
        FoodPage.gr=100;
        FoodPage.cPersent=quotes.get(position).getCarbohidrat();
        FoodPage.pPersent=quotes.get(position).getProtoein();
        FoodPage.fpercent=quotes.get(position).getFat();
    }

    @Override
    public void onClick(View v) {

    }
}
