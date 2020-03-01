package com.example.createaccount23.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesUtils {

    public enum SharedPreferenceKeys {
        USER_ID,
        REFERENCE_NAME
    }

    public static void setSharedPreferencesID(String userID, SharedPreferences sharedPreferences,
                                              SharedPreferences.Editor editor, Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putString(SharedPreferencesUtils.SharedPreferenceKeys.USER_ID.toString(), userID);

        editor.commit(); //todo: should this be apply()?
    }

    public static void setSharedPreferencesRefName(String refName, SharedPreferences sharedPreferences,
                                                   SharedPreferences.Editor editor, Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
        editor.putString(SharedPreferencesUtils.SharedPreferenceKeys.REFERENCE_NAME.toString(), refName);
        editor.commit();
    }

    public static String getSharedPreferences(String key, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        return sharedPreferences.getString(key, "");
    }
}
