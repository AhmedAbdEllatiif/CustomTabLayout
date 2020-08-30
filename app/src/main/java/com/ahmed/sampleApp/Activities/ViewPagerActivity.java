package com.ahmed.sampleApp.Activities;

import android.app.Activity;

import com.ahmed.library.ViewPagerManager;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import com.ahmed.sampleApp.BaseActivity;
import com.ahmed.sampleApp.R;
import com.ahmed.sampleApp.Fragments.FirstFragment;
import com.ahmed.sampleApp.Fragments.SecondFragment;
import com.ahmed.sampleApp.Fragments.ThirdFragment;
import com.ahmed.sampleApp.Translation.Languages;
import com.ahmed.sampleApp.Translation.LocalManger;

public class ViewPagerActivity extends BaseActivity {

    private static final String TAG = "ViewPagerActivity";

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        //To Initialize views
        initViews();

        //Initialize ViewPagerManager.Builder
        ViewPagerManager.Builder builder = new ViewPagerManager.Builder(this);


        int viewType = getIntent().getIntExtra(MainActivity.VIEW_TYPE, -1);

        switch (viewType) {

            case MainActivity.DEFAULT:
                builder.viewPager(viewPager)
                        .isRTL(isRTL())
                        .fragments(getFragments())
                        .build();
                break;
            case MainActivity.WITH_TITLES:
                builder.viewPager(viewPager)
                        .withTabLayout(true)
                        .tabLayout(tabLayout)
                        .isRTL(isRTL())
                        .fragments(getFragments())
                        .titles(getTitlesList())
                        .build();
                break;

            case MainActivity.WITH_ICONS:
                builder.viewPager(viewPager)
                        .withTabLayout(true)
                        .tabLayout(tabLayout)
                        .isRTL(isRTL())
                        .fragments(getFragments())
                        .iconsResources(getIcons())
                        .selectedColor(R.color.colorAccent)
                        .unSelectedColor(R.color.colorPrimaryDark)
                        .build();

                break;

            case MainActivity.WITH_TITLES_ICONS:
                builder.viewPager(viewPager)
                        .withTabLayout(true)
                        .tabLayout(tabLayout)
                        .isRTL(isRTL())
                        .fragments(getFragments())
                        .titles(getTitlesList())
                        .iconsResources(getIcons())
                        .selectedColor(R.color.colorAccent)
                        .unSelectedColor(R.color.colorPrimaryDark)
                        .build();
                break;

        }


    }


    /**
     * Initialize views
     */
    private void initViews() {
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);
    }


    /**
     * @return true if language is arabic
     */
    private boolean isRTL() {
        return getLanguage(this).equals(Languages.ARABIC);
    }


    /**
     * @return arrayList of fragments
     */
    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());
        return fragments;
    }

    /**
     * @return arrayList of titles
     */
    private ArrayList<String> getTitlesList() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add(getString(R.string.fisrt));
        strings.add(getString(R.string.second));
        strings.add(getString(R.string.third));
        return strings;
    }


    /**
     * @return list of Integer with icons
     */
    private ArrayList<Integer> getIcons() {
        ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_home);
        icons.add(R.drawable.ic_photo);
        icons.add(R.drawable.ic_library_music);
        return icons;
    }


    /**
     * @return string of  currentLanguage
     */
    public String getLanguage(Activity activity) {
        Log.d(TAG, "currentLanguage: " + LocalManger.getLocale(activity.getResources()).getDisplayLanguage());
        return LocalManger.getLocale(activity.getResources()).getDisplayLanguage();
    }




    /*private ArrayList<TabLayout.Tab> tabs() {
        ArrayList<TabLayout.Tab> tabArrayList = new ArrayList<>();
        for (String title : titles()) {
            tabArrayList.add(addNewTab(title));
        }
        return tabArrayList;
    }

     *//*
    private TabLayout.Tab addNewTab(String title) {
        TabLayout.Tab newTab = tabLayout.newTab();
        newTab.setText(title);
        newTab.setIcon(R.drawable.ic_home);
        return newTab;
    }*/


}