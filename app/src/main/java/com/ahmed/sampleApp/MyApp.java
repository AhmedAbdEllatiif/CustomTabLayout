package com.ahmed.sampleApp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.ahmed.sampleApp.Translation.LocalManger;

public class MyApp extends Application {
    public static LocalManger localeManager;

    private final String TAG = "App";

    @Override
    protected void attachBaseContext(Context base) {
        localeManager = new LocalManger(base);
        super.attachBaseContext(localeManager.setLocale(base));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        localeManager.setLocale(this);
        //Log.d(TAG, "onConfigurationChanged: " + newConfig.locale.getLanguage());
    }


}
