package com.ahmed.sampleApp.Activities;


import com.ahmed.library.CustomTabLayout;
import com.ahmed.library.SlidePagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import com.ahmed.sampleApp.BaseActivity;
import com.ahmed.sampleApp.R;
import com.ahmed.sampleApp.Fragments.FirstFragment;
import com.ahmed.sampleApp.Fragments.SecondFragment;
import com.ahmed.sampleApp.Fragments.ThirdFragment;

public class ViewPagerActivity extends BaseActivity {

    //views
    private ViewPager2 viewPager;
    private CustomTabLayout tabLayout;
    private CustomTabLayout circle_tabLayout;
    //constant values
    public static final String VIEW_TYPE = "VIEW_TYPE";
    public static final int CIRCLE = 0;
    public static final int WITH_TITLES = 1;
    public static final int WITH_TITLES_ICONS = 2;
    public static final int WITH_ICONS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        //To Initialize views
        initViews();

        int viewType = getIntent().getIntExtra(VIEW_TYPE, -1);
        SlidePagerAdapter slidePagerAdapter = new SlidePagerAdapter(this);
        slidePagerAdapter.addAllFragments(getFragments());
        viewPager.setAdapter(slidePagerAdapter);


        switch (viewType) {
            case WITH_TITLES:
                tabLayout.titles(getTitlesList());
                tabLayout.setupWithViewPager(viewPager);
                break;

            case WITH_ICONS:
                tabLayout.iconsResources(getIcons());
                tabLayout.setupWithViewPager(viewPager);
                break;

            case WITH_TITLES_ICONS:
                tabLayout.titlesAndIconsResources(getTitlesList(), getIcons());
                tabLayout.setupWithViewPager(viewPager);
                break;

            case CIRCLE:
                int tabsCount = getFragments().size();
                circle_tabLayout.tabsCount(tabsCount);
                circle_tabLayout.setVisibility(View.VISIBLE);
                circle_tabLayout.setupWithViewPager(viewPager);
                break;
        }

    }


    /**
     * Initialize views
     */
    private void initViews() {
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);
        circle_tabLayout = findViewById(R.id.circle_tabLayout);
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


}