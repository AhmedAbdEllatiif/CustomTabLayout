package enhancedviewpager.base.com.enhancedviewpager.Activities;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.Objects;

import enhancedviewpager.base.com.enhancedviewpager.BaseActivity;
import enhancedviewpager.base.com.enhancedviewpager.CustomViewPager;
import enhancedviewpager.base.com.enhancedviewpager.Fragments.FirstFragment;
import enhancedviewpager.base.com.enhancedviewpager.Fragments.SecondFragment;
import enhancedviewpager.base.com.enhancedviewpager.Fragments.ThirdFragment;
import enhancedviewpager.base.com.enhancedviewpager.R;
import enhancedviewpager.base.com.enhancedviewpager.Translation.Languages;
import enhancedviewpager.base.com.enhancedviewpager.Translation.LocalManger;
import enhancedviewpager.base.com.enhancedviewpager.ViewPagerAdapter;

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
           // setUpViewPager_RTL();
            CustomViewPager customViewPager = new CustomViewPager(this,viewPager,tabLayout,viewPagerAdapter.getCount(),viewPagerAdapter);
            ArrayList<Integer> icons = new ArrayList<>();
            icons.add(R.drawable.ic_home);
            icons.add(R.drawable.ic_photo);
            icons.add(R.drawable.ic_library_music);
            //customViewPager.addTabs(viewPagerAdapter.getTitles(),icons);
            customViewPager.addTabs(viewPagerAdapter.getTitles());

            customViewPager.setViewPager();


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
        viewPagerAdapter.addFragment(new FirstFragment(),getString(R.string.fisrt));
        viewPagerAdapter.addFragment(new SecondFragment(),getString(R.string.second));
        viewPagerAdapter.addFragment(new ThirdFragment(),getString(R.string.third));
    }


    //To set viewPager with arabic language
    private void setUpViewPager_RTL(){
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }



    //To set viewPager with arabic language
    private void setUpViewPager_LTR(){
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        circle_tabLayout.setupWithViewPager(viewPager);
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


}
