package com.ahmed.sampleApp.Activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import com.ahmed.sampleApp.BaseActivity;
import com.ahmed.sampleApp.MyApp;
import com.ahmed.sampleApp.R;
import com.ahmed.sampleApp.Translation.Languages;
import com.ahmed.sampleApp.Translation.LocalManger;


public class MainActivity extends BaseActivity {

    private Switch aSwitch;
    private Button btn_showViewPager_default;
    private Button btn_showViewPager_circle;
    private Button btn_showViewPager_titles;
    private Button btn_showViewPager_titles_icons;


    public static final String VIEW_TYPE = "TYPE";
    public static final int DEFAULT = 0;
    public static final int CIRCLE = 100;
    public static final int WITH_TITLES = 200;
    public static final int WITH_TITLES_ICONS = 300;
    public static final int WITH_ICONS = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize views
        initViews();

        //onViewsClicked
        onViewsClicked();

        dealWithSwitchBtn();


    }

    //Initialize views
    private void initViews() {
        aSwitch = findViewById(R.id.aSwitch);
        btn_showViewPager_default = findViewById(R.id.btn_showViewPager_default);
        btn_showViewPager_circle = findViewById(R.id.btn_showViewPager_circle);
        btn_showViewPager_titles = findViewById(R.id.btn_showViewPager_titles);
        btn_showViewPager_titles_icons = findViewById(R.id.btn_showViewPager_icons);


    }

    //OnViewsClicked
    private void onViewsClicked() {
        aSwitch.setOnClickListener(v -> {
            if (!aSwitch.isActivated()) {
                new Handler().postDelayed(() -> setNewLocale(LocalManger.LANGUAGE_ARABIC), 1500);
            } else {
                new Handler().postDelayed(() -> setNewLocale(LocalManger.LANGUAGE_ENGLISH), 1500);
            }
        });

        btn_showViewPager_default.setOnClickListener(v -> startViewPagerActivity(DEFAULT));
        btn_showViewPager_circle.setOnClickListener(v -> startViewPagerActivity(CIRCLE));
        btn_showViewPager_titles.setOnClickListener(v -> startViewPagerActivity(WITH_TITLES));
        btn_showViewPager_titles_icons.setOnClickListener(v -> startViewPagerActivity(WITH_TITLES_ICONS));

    }

    private void startViewPagerActivity(int viewPagerType) {
        Intent intent = new Intent(this, ViewPagerActivity.class);
        intent.putExtra(VIEW_TYPE, viewPagerType);
        startActivity(intent);
    }

    //Change the language
    public void setNewLocale(String language) {
        MyApp.localeManager.setNewLocale(this, language);

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));


        System.exit(0);


    }


    //Change the ٍ switch btn status due to current Language
    private void dealWithSwitchBtn() {
        if (LocalManger.getLocale(getResources()).getDisplayLanguage().equals(Languages.ENGLISH)) {
            aSwitch.setChecked(false);
            aSwitch.setActivated(false);
        } else {
            aSwitch.setChecked(true);
            aSwitch.setActivated(true);
        }
    }

}
