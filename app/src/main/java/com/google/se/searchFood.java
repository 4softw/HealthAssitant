package com.google.se;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class searchFood extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem tabItem;
    ViewPager viewPager;
    FragmentPagerAdapter adapterViewPager;
    static SearchFoodFragment searchFoodFragment =new SearchFoodFragment();
    static AddNewFoodFragment addNewFoodFragment =new AddNewFoodFragment();
    static AddedFoofFragment addedFoofFragment =new AddedFoofFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);
        tabLayout=findViewById(R.id.tab);
        viewPager=findViewById(R.id.viewpager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
       viewPager.setAdapter(adapterViewPager);
      tabLayout.setupWithViewPager(viewPager);

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }


        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "اضافه شده";
                case 1:
                    return "جست و جو";
                case 2:
                    return "غذا های من";
                default:
                    return null;
            }
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return addedFoofFragment;
                case 1:
                    return searchFoodFragment;
                case 2:
                    return addNewFoodFragment;
                default:
                    return null;
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewPager != null && viewPager.getAdapter() != null) {
            viewPager.getAdapter().notifyDataSetChanged();
        }
    }
}