package com.example.fintech.constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SuppressLint("ApplySharedPref")
public class AppPreferences {


    private static final String TAG = "AppPreferences";

    private static String uniqueID = null;
    private static String token = null;
    private final SharedPreferences sharedPrefs;


    public AppPreferences(Context context) {
        this.sharedPrefs = context.getSharedPreferences(PreferenceNames.APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPrefs.edit();
        prefsEditor.commit();
    }

    public void setUserName(String userName) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PreferenceNames.USER_NAME, userName);
        editor.commit();
    }

    public String getUserName() {
        String username = "";
        try {
            username = sharedPrefs.getString(PreferenceNames.USER_NAME, "");
        } catch (Exception ex) {
        }
        return username;
    }

    public void setCookies(String cookies) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(PreferenceNames.COOKIES, cookies);
        editor.commit();
    }

    public String getCookies() {
        String cookies = "";
        try {
            cookies = sharedPrefs.getString(PreferenceNames.COOKIES, "");
        } catch (Exception ex) {
        }
        return cookies;
    }

    static class PreferenceNames {

        private PreferenceNames() {
            throw new IllegalStateException("Utility Class");
        }

        private static final String EMAIL_ID = "EMAIL_ID";
        private static final String COOKIES = "COOKIES";
        private static final String USER_NAME = "USER_NAME";
        private static final String APP_SHARED_PREFS = "com.example.fintech";
    }



}
