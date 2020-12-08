package com.google.se;

import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

public class searchFood extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem tabItem;
    ViewPager viewPager;
    FragmentPagerAdapter adapterViewPager;
    static SearchFoodFragmentPage1 searchFoodFragmentPage1=new SearchFoodFragmentPage1();
    static SearchFoodFragmentPage2 searchFoodFragmentPage2=new SearchFoodFragmentPage2();
    static SearchFoodFragmentPage3 searchFoodFragmentPage3=new SearchFoodFragmentPage3();
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
                    return "جست و جو";
                case 1:
                    return "غذاهای من";
                case 2:
                    return "اضافه کردن";
                default:
                    return null;
            }
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return searchFoodFragmentPage1;
                case 1:
                    return searchFoodFragmentPage2;
                case 2:
                    return searchFoodFragmentPage3;
                default:
                    return null;
            }
        }

    }
}