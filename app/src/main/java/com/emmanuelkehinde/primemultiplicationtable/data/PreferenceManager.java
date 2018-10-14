package com.emmanuelkehinde.primemultiplicationtable.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.emmanuelkehinde.primemultiplicationtable.BuildConfig;

public class PreferenceManager {

    private SharedPreferences mSharedPreferences;
    private final String PREF_HOME_SHOW_CASE_1 = "home_show_case_1";
    private final String PREF_HOME_SHOW_CASE_2 = "home_show_case_2";

    public PreferenceManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
    }

    public boolean isHomeShowCase1Shown() {
        return mSharedPreferences.getBoolean(PREF_HOME_SHOW_CASE_1, false);
    }

    public void setHomeShowCase1Shown() {
        mSharedPreferences.edit().putBoolean(PREF_HOME_SHOW_CASE_1, true).apply();
    }

    public boolean isHomeShowCase2Shown() {
        return mSharedPreferences.getBoolean(PREF_HOME_SHOW_CASE_2, false);
    }

    public void setHomeShowCase2Shown() {
        mSharedPreferences.edit().putBoolean(PREF_HOME_SHOW_CASE_2, true).apply();
    }

}
