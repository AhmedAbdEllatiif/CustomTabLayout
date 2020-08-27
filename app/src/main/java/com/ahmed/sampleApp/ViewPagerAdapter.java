package com.ahmed.sampleApp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    public final ArrayList<Fragment> fragments = new ArrayList<>();
    private final ArrayList<String> titles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment,String title){
        fragments.add(fragment);
        titles.add(title);
    }
    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }
    public ArrayList<String> getTitles(){
        return titles;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (titles.size() > 0){
            return titles.get(position);
        }else {
            return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
