package com.google.se;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;

public class AgeFragment extends android.support.v4.app.Fragment {

    com.crystal.crystalrangeseekbar.widgets.BubbleThumbSeekbar age;
    static String agestr;
    TextView amin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_information_page4, container, false);
        age=view.findViewById(R.id.age);
        amin=view.findViewById(R.id.amin);


        age.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number value) {
                amin.setText(String.valueOf(value));
                agestr=String.valueOf(value);
            }
        });

        return view;
    }
}
