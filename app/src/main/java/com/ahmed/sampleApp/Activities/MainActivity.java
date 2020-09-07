package com.ahmed.sampleApp.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Button;


import com.ahmed.sampleApp.BaseActivity;
import com.ahmed.sampleApp.MyApp;
import com.ahmed.sampleApp.R;
import com.ahmed.sampleApp.Translation.Languages;
import com.ahmed.sampleApp.Translation.LocalManger;

import androidx.appcompat.widget.SwitchCompat;

import static com.ahmed.sampleApp.Activities.ViewPagerActivity.*;


public class MainActivity extends BaseActivity {

    //Views
    private SwitchCompat aSwitch;
    private Button btn_showViewPager_titles;
    private Button btn_showViewPager_icons;
    private Button btn_showViewPager_titles_icons;
    private Button btn_custom_shape;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //To initialize views
        initViews();

        //To handle on views clicked
        onViewsClicked();

        //Change the ٍ switch btn status due to current Language
        dealWithSwitchBtn();

    }

    /**
     *To initialize views
     * */
    private void initViews() {
        aSwitch = findViewById(R.id.aSwitch);
        btn_showViewPager_titles = findViewById(R.id.btn_showViewPager_titles);
        btn_showViewPager_icons = findViewById(R.id.btn_showViewPager_icons);
        btn_showViewPager_titles_icons = findViewById(R.id.btn_showViewPager_titlesAndIcons);
        btn_custom_shape = findViewById(R.id.btn_showViewPager_custom_shape);
    }

    /**
     * To handle on views clicked
     * */
    private void onViewsClicked() {
        btn_showViewPager_titles.setOnClickListener(v -> startViewPagerActivity(WITH_TITLES));
        btn_showViewPager_icons.setOnClickListener(v -> startViewPagerActivity(WITH_ICONS));
        btn_showViewPager_titles_icons.setOnClickListener(v -> startViewPagerActivity(WITH_TITLES_ICONS));
        btn_custom_shape.setOnClickListener(v -> startViewPagerActivity(CIRCLE));

        aSwitch.setOnClickListener(v -> {
            if (!aSwitch.isActivated()) {
                new Handler().postDelayed(() -> setNewLocale(LocalManger.LANGUAGE_ARABIC), 1500);
            } else {
                new Handler().postDelayed(() -> setNewLocale(LocalManger.LANGUAGE_ENGLISH), 1500);
            }
        });
    }

    /**
     * Change the ٍ switch btn status due to current Language
     * */
    private void dealWithSwitchBtn() {
        if (LocalManger.getLocale(getResources()).getDisplayLanguage().equals(Languages.ENGLISH)) {
            aSwitch.setChecked(false);
            aSwitch.setActivated(false);
        } else {
            aSwitch.setChecked(true);
            aSwitch.setActivated(true);
        }
    }

    /**
     * To open ViewPagerActivity
     * */
    private void startViewPagerActivity(int viewPagerType) {
        Intent intent = new Intent(this, ViewPagerActivity.class);
        intent.putExtra(VIEW_TYPE, viewPagerType);
        startActivity(intent);
    }

    /**
     * Change the language
     * */
    public void setNewLocale(String language) {
        MyApp.localeManager.setNewLocale(this, language);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        System.exit(0);
    }

}
