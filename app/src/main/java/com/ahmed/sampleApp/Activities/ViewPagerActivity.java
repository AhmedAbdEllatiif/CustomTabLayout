package com.ahmed.sampleApp.Activities;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Objects;

import com.ahmed.sampleApp.BaseActivity;
import com.ahmed.sampleApp.R;
import com.ahmed.sampleApp.ViewPagerHelper;
import com.ahmed.sampleApp.Fragments.FirstFragment;
import com.ahmed.sampleApp.Fragments.SecondFragment;
import com.ahmed.sampleApp.Fragments.ThirdFragment;
import com.ahmed.sampleApp.Translation.Languages;
import com.ahmed.sampleApp.Translation.LocalManger;
import com.ahmed.sampleApp.ViewPagerAdapter;

public class ViewPagerActivity extends BaseActivity {

    private static final String TAG = "ViewPagerActivity";
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabLayout circle_tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        //Initialize views
        initViews();

        //init ViewPagerAdapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        //To add fragments in viewpagerAdapter
        addFragmentsToAdapter();


        if (getLanguage(this).equals(Languages.ARABIC)){
         setUpViewPager_RTL();


        } else {
            setUpViewPager_LTR();
        }

    }


    //Initialize views
    private void initViews(){
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabLayout);
        circle_tabLayout = findViewById(R.id.circle_tabLayout);
    }

    //To add fragments in viewpagerAdapter
    private void addFragmentsToAdapter(){
        int viewType = getIntent().getIntExtra(MainActivity.VIEW_TYPE,-1);
        if (viewType == MainActivity.CIRCLE ){
            viewPagerAdapter.addFragment(new FirstFragment());
            viewPagerAdapter.addFragment(new SecondFragment());
            viewPagerAdapter.addFragment(new ThirdFragment());
        }else {
            viewPagerAdapter.addFragment(new FirstFragment(),getString(R.string.fisrt));
            viewPagerAdapter.addFragment(new SecondFragment(),getString(R.string.second));
            viewPagerAdapter.addFragment(new ThirdFragment(),getString(R.string.third));
        }



    }



    //To set viewPager with arabic language
    private void setUpViewPager_RTL(){
        int viewType = getIntent().getIntExtra(MainActivity.VIEW_TYPE,-1);
        ViewPagerHelper customViewPager  =
                new ViewPagerHelper(this,viewPager,tabLayout,viewPagerAdapter.getCount(),viewPagerAdapter);

        switch (viewType){
            case MainActivity.DEFAULT :
                customViewPager.setViewPager(false);
                break;
            case MainActivity.CIRCLE :
                ViewPagerHelper customViewPager_circle
                        = new ViewPagerHelper(this,viewPager,circle_tabLayout,viewPagerAdapter.getCount(),viewPagerAdapter);
                tabLayout.setVisibility(View.GONE);
                circle_tabLayout.setVisibility(View.VISIBLE);
                customViewPager_circle.addTabs();
                customViewPager_circle.setViewPager(true);
                break;
            case MainActivity.WITH_TITLES :
                customViewPager.addTabs(viewPagerAdapter.getTitles());
                customViewPager.setViewPager(true);
                break;

            case  MainActivity.WITH_TITLES_ICONS:
                tabLayout.setVisibility(View.VISIBLE);
                customViewPager.addTabs(viewPagerAdapter.getTitles(),getIcons());
                customViewPager.setViewPager(true);
                break;

            case MainActivity.WITH_ICONS :
                break;


        }
        // setUpViewPager_RTL();


    }



    //To set viewPager with arabic language
    private void setUpViewPager_LTR(){
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        circle_tabLayout.setupWithViewPager(viewPager);

        int viewType = getIntent().getIntExtra(MainActivity.VIEW_TYPE,-1);

        switch (viewType){
            case MainActivity.DEFAULT :
                tabLayout.setVisibility(View.GONE);
                circle_tabLayout.setVisibility(View.GONE);
                break;
            case MainActivity.CIRCLE :
                tabLayout.setVisibility(View.GONE);
                circle_tabLayout.setVisibility(View.VISIBLE);
                circle_tabLayout.setupWithViewPager(viewPager);
                break;
            case MainActivity.WITH_TITLES :
                viewPager.setAdapter(viewPagerAdapter);
                tabLayout.setupWithViewPager(viewPager);
                break;

            case  MainActivity.WITH_TITLES_ICONS:
                for (int i=0; i < tabLayout.getTabCount(); i ++){
                    tabLayout.getTabAt(i).setIcon(getIcons().get(i));
                    onTabSelected();
                    setDefaultTabColor();
                }
                break;

            case MainActivity.WITH_ICONS :
                break;


        }
    }


    //To get currentLanguage
    public String getLanguage(Activity activity){
        Log.e(TAG,"Language: " + LocalManger.getLocale(activity.getResources()).getDisplayLanguage() );
        return LocalManger.getLocale(activity.getResources()).getDisplayLanguage();
    }

    //To set filter color by default to tabs
    private void setDefaultTabColor(){
        int tabsCount = tabLayout.getTabCount();
        for(int i=0; i<tabsCount; i++){
            if (Objects.requireNonNull(tabLayout.getTabAt(i)).isSelected()){
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null){
                    if (tab.getIcon() != null){
                        tab.getIcon().setColorFilter(getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                    }
                }

            }else {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab!= null && tab.getIcon()!=null){
                    tab.getIcon().setColorFilter(getColor(R.color.txt_gray), PorterDuff.Mode.SRC_IN);
                }
            }
        }
    }

    private void onTabSelected() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getIcon() != null) {
                    tab.getIcon().setColorFilter(getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                }

            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getIcon() != null) {
                    tab.getIcon().setColorFilter(getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private ArrayList<Integer> getIcons(){
        ArrayList<Integer> icons = new ArrayList<>();
        icons.add(R.drawable.ic_home);
        icons.add(R.drawable.ic_photo);
        icons.add(R.drawable.ic_library_music);
        return icons;
    }

}
