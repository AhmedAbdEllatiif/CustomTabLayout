package com.ahmed.sampleApp;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.ahmed.sampleApp.Translation.Utility;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(MyApp.localeManager.setLocale(base));
        Log.d(TAG, "attachBaseContext");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        Utility.resetActivityTitle(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showResourcesInfo();
    }

    private void showResourcesInfo() {
        Resources topLevelRes = Utility.getTopLevelResources(this);
    }



}
