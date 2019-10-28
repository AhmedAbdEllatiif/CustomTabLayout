package enhancedviewpager.base.com.enhancedviewpager;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class CustomViewPager{

    private final static String TAG = "CustomViewPager";

    private Context context;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Integer tabsCount;
    private ViewPagerAdapter viewPagerAdapter;

    private boolean WITH_TABS;

    public CustomViewPager(Context context, ViewPager viewPager, TabLayout tabLayout) {
        this.context = context;
        this.tabLayout = tabLayout;
        this.viewPager = viewPager;
    }

    public CustomViewPager(Context context, ViewPager viewPager, TabLayout tabLayout,
                           Integer tabsCount, ViewPagerAdapter fragmentPagerAdapter) {
        this.context = context;
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
        this.tabsCount = tabsCount;
        this.viewPagerAdapter = fragmentPagerAdapter;
        if (tabLayout != null){
            //setDefaultTabColor();
        }

    }

    public void setViewPager(){
        viewPager.setAdapter(viewPagerAdapter);
        Collections.reverse(viewPagerAdapter.fragments);
        viewPager.setCurrentItem(viewPagerAdapter.getCount() - 1);

        onPageChanged();
        onTabSelected();
        setDefaultTabColor();
    }

    public void addTabs(){
        for(int i=0; i < tabsCount; i++){
            tabLayout.addTab(tabLayout.newTab());
        }
    }

    public void addTabs(ArrayList<String> tabsTitles){
        for(int i=0; i < tabsCount; i++){
            tabLayout.addTab(addNewTab(tabsTitles.get(i)));
        }

    }

    public void addTabs(ArrayList<String> tabsTitles, ArrayList<Integer> iconsResources){
        for(int i=0; i < tabsCount; i++){
            tabLayout.addTab(addNewTab(tabsTitles.get(i),iconsResources.get(i)));
        }

    }

    //To make a new Tab
    private TabLayout.Tab addNewTab(String title,int iconResource) {
        TabLayout.Tab newTab = tabLayout.newTab();
        newTab.setText(title);
        newTab.setIcon(iconResource);
        return newTab;
    }


    //To make a new Tab
    private TabLayout.Tab addNewTab(String title) {
        TabLayout.Tab newTab = tabLayout.newTab();
        newTab.setText(title);
        return newTab;
    }



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


    private void onTabSelected(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            changePageWithSelectedTab_Arabic(tab);
                if (tab.getIcon()!=null){
                    tab.getIcon().setColorFilter(context.getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getIcon()!=null){
                    tab.getIcon().setColorFilter(context.getColor(R.color.txt_gray), PorterDuff.Mode.SRC_IN);
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

    //To set filter color by default to tabs
    private void setDefaultTabColor(){
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



