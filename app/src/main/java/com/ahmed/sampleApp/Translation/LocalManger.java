package com.ahmed.sampleApp.Translation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static android.os.Build.VERSION_CODES.N;

public class LocalManger {

    public static final  String LANGUAGE_ENGLISH   = "en";
    public static final  String LANGUAGE_ARABIC = "ar_";
    private static final String LANGUAGE_KEY   = "language_key";

    private final SharedPreferences prefs;

    public LocalManger(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Context setLocale(Context c) {
        return updateResources(c, getLanguage());
    }

    public Context setNewLocale(Context c, String language) {
        persistLanguage(language);
        return updateResources(c, language);
    }

    private String getLanguage() {
        return prefs.getString(LANGUAGE_KEY, LANGUAGE_ARABIC);
    }

    @SuppressLint("ApplySharedPref")
    private void persistLanguage(String language) {
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        prefs.edit().putString(LANGUAGE_KEY, language).commit();
    }

    private Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Utility.isAtLeastVersion(JELLY_BEAN_MR1)) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
            //res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Utility.isAtLeastVersion(N) ? config.getLocales().get(0) : config.getLocales().get(0);
    }
}
