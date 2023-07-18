package com.applock.singleapplock;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;

import androidx.core.app.NotificationCompat;



import java.util.HashMap;
import java.util.Locale;

public class AppDefaultHelper {
    Context context;

    public AppDefaultHelper(Context context2) {
        this.context = context2;
    }

    public int setAppTheme() {
        try {
            return getThemeStyleCode().get(getThemeType());
        } catch (NullPointerException unused) {
            return R.style.AppTheme;
        }
    }

    public int getThemeType() {
        String str = (String) new SharedPrefsHelper().getSharedPrefs(this.context, SharedPrefsKeys.THEME_TYPE, "");
        if (StringHelper.isValidString(str)) {
            try {
                return Integer.parseInt(str);
            } catch (Exception unused) {
            }
        }
        return 0;
    }

    public static int getThemeId(Context context2) {
        String str = (String) new SharedPrefsHelper().getSharedPrefs(context2, SharedPrefsKeys.THEME_TYPE, "");
        if (StringHelper.isValidString(str)) {
            try {
                return Integer.parseInt(str);
            } catch (Exception unused) {
            }
        }
        return 0;
    }

    public static boolean isScreenShotEnable(Context context2) {
        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper();
        if (((String) sharedPrefsHelper.getSharedPrefs(context2, SharedPrefsKeys.SET_SCREENSHOT_FLAG, "")).equals("")) {
            sharedPrefsHelper.setSharePrefs(context2, SharedPrefsKeys.SET_SCREENSHOT_FLAG, "COMPLETED");
            sharedPrefsHelper.setSharePrefs(context2, SharedPrefsKeys.SCREENSHOT_FLAG, true);
        }
        return ((Boolean) sharedPrefsHelper.getSharedPrefs(context2, SharedPrefsKeys.SCREENSHOT_FLAG, false)).booleanValue();
    }

    private SparseIntArray getThemeStyleCode() {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sparseIntArray.put(0, R.style.AppTheme);
        sparseIntArray.put(1, R.style.AppTheme_DARK);
        sparseIntArray.put(2, R.style.AppTheme_LIGHT);
        return sparseIntArray;
    }


}
