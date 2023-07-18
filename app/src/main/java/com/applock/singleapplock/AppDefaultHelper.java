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

    public int getColorCode(int i) {
        TypedValue typedValue = new TypedValue();
        TypedArray obtainStyledAttributes = this.context.obtainStyledAttributes(typedValue.data, new int[]{i});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        return color;
    }

    public static String getDefaultLanguage(Context context2) {
        return (String) new SharedPrefsHelper().getSharedPrefs(context2, SharedPrefsKeys.DEFAULT_LANGUAGE, "");
    }

    public static void changeLang(Context context2) {
        String defaultLanguage = getDefaultLanguage(context2);
        Configuration configuration = context2.getResources().getConfiguration();
        if (!"".equals(defaultLanguage) && !configuration.locale.getLanguage().equals(defaultLanguage)) {
            Locale locale = new Locale(defaultLanguage);
            Locale.setDefault(locale);
            Configuration configuration2 = new Configuration(configuration);
            if (Build.VERSION.SDK_INT <= 23) {
                configuration2.setLocale(locale);
            } else {
                configuration2.locale = locale;
            }
            context2.getResources().updateConfiguration(configuration2, context2.getResources().getDisplayMetrics());
        }
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

    public static SparseArray<String> getAllAppThemes() {
        SparseArray<String> sparseArray = new SparseArray<>();
        sparseArray.put(0, "Default");
        sparseArray.put(1, "Dark");
        sparseArray.put(2, "Light");
        return sparseArray;
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

    public static String getSortingType(Context context2) {
        String str = (String) new SharedPrefsHelper().getSharedPrefs(context2, SharedPrefsKeys.SORT_ACCOUNT_BY, "");
        if (str != null && str.length() != 0) {
            return str;
        }
        new SharedPrefsHelper().setSharePrefs(context2, SharedPrefsKeys.SORT_ACCOUNT_BY, "CUSTOM");
        return "CUSTOM";
    }

    public static int getSortingIndex(Context context2) {
        String sortingType = getSortingType(context2);
        sortingType.hashCode();
        if (!sortingType.equals("ISSUER")) {
            return !sortingType.equals("ACCOUNT") ? 2 : 0;
        }
        return 1;
    }

    public static boolean getSortingOrder(Context context2) {
        return ((Boolean) new SharedPrefsHelper().getSharedPrefs(context2, SharedPrefsKeys.SORTING_ORDER, true)).booleanValue();
    }

    public static boolean isGroupingEnable(Context context2) {
        return ((Boolean) new SharedPrefsHelper().getSharedPrefs(context2, SharedPrefsKeys.GROUP_BY_LABEL_FLAG, true)).booleanValue();
    }

    public static boolean isTapToRevealEnable(Context context2) {
        return ((Boolean) new SharedPrefsHelper().getSharedPrefs(context2, SharedPrefsKeys.TAB_TO_REVEAL, false)).booleanValue();
    }

    public static boolean getDefaultLabelFlag(Context context2) {
        return ((Boolean) new SharedPrefsHelper().getSharedPrefs(context2, SharedPrefsKeys.ADD_LABEL, false)).booleanValue();
    }

    private void putCategoryToHashMap(HashMap<String, String> hashMap, String str, String... strArr) {
        for (String put : strArr) {
            hashMap.put(put, str);
        }
    }

    public HashMap<String, String> getAppIssuerCategory() {
        HashMap<String, String> hashMap = new HashMap<>();
        putCategoryToHashMap(hashMap, "entertainment", "fortnite");
        putCategoryToHashMap(hashMap, "tools", "trello", "bitwarden", "dropbox", "dashlane", "evernote", "teamviewer", "lastpass", "mailchimp", "ifttt", "godaddy");
        putCategoryToHashMap(hashMap, "work", "heroku", "slack", "github", "salesforce", "atlassian", "bitbucket", "digital ocean", "zoho", "intuit");
        putCategoryToHashMap(hashMap, NotificationCompat.CATEGORY_SOCIAL, "snapchat", "discord", "tumblr", "twitter", "linkedin", "facebook", "instagram", "angel_list", "reddit", "kickstarter");
        putCategoryToHashMap(hashMap, "personal", "google", "yahoo", "envato", "microsoft", "uber", "wordpress", "paypal", "coinbase", "amazon", "apple");
        return hashMap;
    }
}
