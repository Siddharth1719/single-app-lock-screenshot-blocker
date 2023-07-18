package com.applock.singleapplock;

import android.content.Context;

public class GetAppPassword {
    public static boolean customPasswordExists(Context context) {
        return !((String) new SharedPrefsHelper().getSharedPrefs(context, SharedPrefsKeys.DEFAULT_ENCRYPTION_KEY, "")).equals("");
    }

    public static String getPassword(Context context) {
        String str = (String) new SharedPrefsHelper().getSharedPrefs(context, SharedPrefsKeys.DEFAULT_ENCRYPTION_KEY, "");
        if (str == null || str.length() <= 0) {
            return ApplicationSettings.PASSWORD[0];
        }
        return str;
    }
}
