package com.google.se;

import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class WaterFragment extends DialogFragment {
    ImageView add,minus;
    TextView liter,glass;
    DailyIDataDBHelper dailyIDataDBHelper;
    com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_water,container,false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dailyIDataDBHelper=new DailyIDataDBHelper(getContext());
            getDialog().getWindow().setLayout((int)0.5*ViewGroup.LayoutParams.MATCH_PARENT,
                    (int)0.5*ViewGroup.LayoutParams.MATCH_PARENT);
          //  dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        add=view.findViewById(R.id.plus);
        minus=view.findViewById(R.id.minus);
        progressBar=view.findViewById(R.id.progressWater);
        liter=view.findViewById(R.id.liter);
        glass=view.findViewById(R.id.glass);
        liter.setText(String.valueOf(Integer.parseInt(HomePage.water.getText().toString())*200)+"ml");
        glass.setText(HomePage.water.getText().toString());
        progressBar.setMax(8);
        progressBar.setProgress(Integer.parseInt(HomePage.water.getText().toString()));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int addd = Integer.parseInt(HomePage.water.getText().toString());
                addd++;
                HomePage.water.setText(String.valueOf(addd));
                WaterModel waterModel=new WaterModel();
                waterModel.setGlass(addd);
                waterModel.setTime(HomePage.getdate());
                dailyIDataDBHelper.Insertwater(waterModel);
               HomePage.calPoint(10,getContext());
                progressBar.setProgress(Integer.valueOf(HomePage.water.getText().toString()));
                liter.setText(String.valueOf(Integer.parseInt(HomePage.water.getText().toString())*200)+"ml");
                glass.setText(HomePage.water.getText().toString());

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int addd = Integer.parseInt(HomePage.water.getText().toString());
                if (addd != 0) {
                    addd = addd - 1;
                    HomePage.water.setText(String.valueOf(addd));
                    WaterModel waterModel=new WaterModel();
                    waterModel.setGlass(addd);
                    waterModel.setTime(HomePage.getdate());
                    dailyIDataDBHelper.Insertwater(waterModel);
                    HomePage.calPoint(-10,getContext());
                    progressBar.setProgress(Integer.valueOf(HomePage.water.getText().toString()));
                    liter.setText(String.valueOf(Integer.parseInt(HomePage.water.getText().toString())*200)+"ml");
                    glass.setText(HomePage.water.getText().toString());
                }
            }
        });
        return view;
    }
}
