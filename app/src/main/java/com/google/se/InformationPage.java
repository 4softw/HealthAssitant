package com.google.se;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.pixelcan.inkpageindicator.InkPageIndicator;

public class InformationPage extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;
    InkPageIndicator springIndicator;
    static NameAndLastnamwFragment nameAndLastnamwFragment=new NameAndLastnamwFragment();
    static WeightAndHeghtFragment weightAndHeghtFragment=new WeightAndHeghtFragment();
    static GenderFragment genderFragment=new GenderFragment();
    static AgeFragment ageFragment=new AgeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);
        springIndicator=findViewById(R.id.indicator);
        ViewPager vpPager =findViewById(R.id.viewpager);

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        springIndicator.setViewPager(vpPager);
    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }


        @Override
        public int getCount() {
            return NUM_ITEMS;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return nameAndLastnamwFragment;
                case 1:
                    return genderFragment;
                case 2:
                    return ageFragment;
                case 3:
                    return weightAndHeghtFragment;
                default:
                    return null;
            }
        }

    }

}
