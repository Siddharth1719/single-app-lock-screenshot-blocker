package com.applock.singleapplock;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Map;

public class SharedPrefsHelper {
    private final String FILE_NAME = "TOTP_Authenticator_Preferences";

    public void setSharePrefs(Context context, String str, Object obj) {
        SharedPreferences.Editor edit = context.getSharedPreferences("TOTP_Authenticator_Preferences", 0).edit();
        if (obj instanceof String) {
            edit.putString(str, (String) obj);
        } else if (obj instanceof Integer) {
            edit.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Boolean) {
            edit.putBoolean(str, ((Boolean) obj).booleanValue());
        } else if (obj instanceof Float) {
            edit.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof Long) {
            edit.putLong(str, ((Long) obj).longValue());
        } else {
            edit.putString(str, obj.toString());
        }
        edit.apply();
    }

    public Object getSharedPrefs(Context context, String str, Object obj) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("TOTP_Authenticator_Preferences", 0);
        if (obj instanceof String) {
            return sharedPreferences.getString(str, (String) obj);
        }
        if (obj instanceof Integer) {
            return Integer.valueOf(sharedPreferences.getInt(str, ((Integer) obj).intValue()));
        }
        if (obj instanceof Boolean) {
            return Boolean.valueOf(sharedPreferences.getBoolean(str, ((Boolean) obj).booleanValue()));
        }
        if (obj instanceof Float) {
            return Float.valueOf(sharedPreferences.getFloat(str, ((Float) obj).floatValue()));
        }
        if (obj instanceof Long) {
            return Long.valueOf(sharedPreferences.getLong(str, ((Long) obj).longValue()));
        }
        return null;
    }

    public void remove(Context context, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences("TOTP_Authenticator_Preferences", 0).edit();
        edit.remove(str);
        edit.apply();
    }

    public void clear(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences("TOTP_Authenticator_Preferences", 0).edit();
        edit.clear();
        edit.apply();
    }

    public boolean contains(Context context, String str) {
        return context.getSharedPreferences("TOTP_Authenticator_Preferences", 0).contains(str);
    }

    public Map<String, ?> getAll(Context context) {
        return context.getSharedPreferences("TOTP_Authenticator_Preferences", 0).getAll();
    }



}
