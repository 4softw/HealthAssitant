package com.google.se;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class GenderFragment extends Fragment {


    RadioButton man;
    RadioButton woman;
    static String gender;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_information_page2, container, false);
        man=view.findViewById(R.id.man);
        woman=view.findViewById(R.id.woman);
        man.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gender="man";
              //  Toast.makeText(getContext(), gender, Toast.LENGTH_SHORT).show();
            }
        });

        woman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                gender="woman";
             //   Toast.makeText(getContext(), gender, Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }
}
