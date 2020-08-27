package com.ahmed.sampleApp;

import android.content.Context;
import android.graphics.PorterDuff;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class ViewPagerHelper {

    //private final static String TAG = "ViewPagerHelper";

    private Context context;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Integer tabsCount;
    private ViewPagerAdapter viewPagerAdapter;
    private Integer selectedColor;
    private Integer unSelectedColor;


    public ViewPagerHelper(Context context, ViewPager viewPager, TabLayout tabLayout) {
        this.context = context;
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
    }

    public ViewPagerHelper(Context context, ViewPager viewPager, TabLayout tabLayout,
                           Integer tabsCount, ViewPagerAdapter fragmentPagerAdapter) {

        this.context = context;
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        this.tabsCount = tabsCount;
        this.viewPagerAdapter = fragmentPagerAdapter;
    }


    //To setup viewPager
    public void setViewPager(boolean WITH_TABS){
        viewPager.setAdapter(viewPagerAdapter);
        Collections.reverse(viewPagerAdapter.fragments);
        viewPager.setCurrentItem(viewPagerAdapter.getCount() - 1);

        if (WITH_TABS){
        onPageChanged();
        onTabSelected();
        setTabsColors();
        }


    }


    //To add tabs
    /*////////////////////////////////////////////////////////////////////////////////////////*/
    // To add empty tab (circle, rectangle or any shape)
    public void addTabs(){
        for(int i=0; i < tabsCount; i++){
            tabLayout.addTab(makeNewTab());
        }
    }

    //To add tab with title
    public void addTabs(ArrayList<String> tabsTitles){
        for(int i=0; i < tabsCount; i++){
            tabLayout.addTab(makeNewTab(tabsTitles.get(i)));
        }

    }

    //To add tab with title and icon
    public void addTabs(ArrayList<String> tabsTitles, ArrayList<Integer> iconsResources){
        for(int i=0; i < tabsCount; i++){
            tabLayout.addTab(makeNewTab(tabsTitles.get(i),iconsResources.get(i)));
        }

    }
    /*////////////////////////////////////////////////////////////////////////////////////////*/





    //To make new tabs
    /*////////////////////////////////////////////////////////////////////////////////////////*/
    //To make a new empty tab
    private TabLayout.Tab makeNewTab() {
        TabLayout.Tab newTab = tabLayout.newTab();
        return newTab;
    }

    //To make a new tab with title
    private TabLayout.Tab makeNewTab(String title) {
        TabLayout.Tab newTab = tabLayout.newTab();
        newTab.setText(title);
        return newTab;
    }

    //To make a new tab with title and icon
    private TabLayout.Tab makeNewTab(String title,int iconResource) {
        TabLayout.Tab newTab = tabLayout.newTab();
        newTab.setText(title);
        newTab.setIcon(iconResource);
        return newTab;
    }
    /*////////////////////////////////////////////////////////////////////////////////////////*/




    //On change page  with scroll change the selected tab
    /*////////////////////////////////////////////////////////////////////////////////////////*/
    private void onPageChanged(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
                int selectedTab = (tabsCount - i) - 1;
                Objects.requireNonNull(tabLayout.getTabAt(selectedTab)).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    /*////////////////////////////////////////////////////////////////////////////////////////*/



    //On select change the selected page
    /*////////////////////////////////////////////////////////////////////////////////////////*/
    public void onTabSelected(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            changePageWithSelectedTab_Arabic(tab);
                if (tab.getIcon()!=null && getSelectedColor()!=null && getUnSelectedColor()!=null){
                    tab.getIcon().setColorFilter(context.getColor(getSelectedColor()), PorterDuff.Mode.SRC_IN);
                }else {
                    if (tab.getIcon() != null ){
                        tab.getIcon().setColorFilter(context.getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                    }
                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getIcon()!=null && getSelectedColor()!=null && getUnSelectedColor()!=null){
                    tab.getIcon().setColorFilter(context.getColor(getUnSelectedColor()), PorterDuff.Mode.SRC_IN);
                }else {
                    if (tab.getIcon() != null ){
                        tab.getIcon().setColorFilter(context.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
                    }

                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //To change the page in viewPager with selected Tab
    private void changePageWithSelectedTab_Arabic(TabLayout.Tab tab) {
        int tabsCount = tabLayout.getTabCount();
        int currentItemPosition_viewPager = (tabsCount - tab.getPosition()) - 1;
        viewPager.setCurrentItem(currentItemPosition_viewPager);
    }
    /*////////////////////////////////////////////////////////////////////////////////////////*/





    //To change tab colors
    /*////////////////////////////////////////////////////////////////////////////////////////*/
    //To setTabs color by default or with custom colors
    public void setTabsColors(){
        if (getSelectedColor() == null || getUnSelectedColor() == null ){
            setDefaultTabColor();

        }else {
            setCustomTabColor();
        }
    }

    private void setCustomTabColor(){
        int tabsCount = tabLayout.getTabCount();


        for(int i=0; i<tabsCount; i++){
            if (Objects.requireNonNull(tabLayout.getTabAt(i)).isSelected()){
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null){
                    if (tab.getIcon() != null){
                        tab.getIcon().setColorFilter(context.getColor(getSelectedColor()), PorterDuff.Mode.SRC_IN);
                    }
                }

            }else {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab!= null && tab.getIcon()!=null){
                    tab.getIcon().setColorFilter(context.getColor(getUnSelectedColor()), PorterDuff.Mode.SRC_IN);
                }
            }
        }
    }

    //To set filter color by default to tabs
    private void setDefaultTabColor(){
      if (tabLayout != null){
          int tabsCount = tabLayout.getTabCount();
          for(int i=0; i<tabsCount; i++){
              if (Objects.requireNonNull(tabLayout.getTabAt(i)).isSelected()){
                  TabLayout.Tab tab = tabLayout.getTabAt(i);
                  if (tab != null){
                      if (tab.getIcon() != null){
                          tab.getIcon().setColorFilter(context.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                      }
                  }

              }else {
                  TabLayout.Tab tab = tabLayout.getTabAt(i);
                  if (tab!= null && tab.getIcon()!=null){
                      tab.getIcon().setColorFilter(context.getColor(R.color.txt_gray), PorterDuff.Mode.SRC_IN);
                  }
              }
          }
      }
    }
    /*////////////////////////////////////////////////////////////////////////////////////////*/




    //Getters and setters
    /*///////////////////////////////////////////////////////////////////////////////////////////////*/
    private Integer getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Integer selectedColor) {
        this.selectedColor = selectedColor;
    }

    private Integer getUnSelectedColor() {
        return unSelectedColor;
    }

    public void setUnSelectedColor(Integer unSelectedColor) {
        this.unSelectedColor = unSelectedColor;
    }
    /*///////////////////////////////////////////////////////////////////////////////////////////////*/





}



