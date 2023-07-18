package com.applock.singleapplock;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Spannable;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;


import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
    public static boolean isValidString(String str) {
        return str != null && !str.equalsIgnoreCase("");
    }

    public static boolean isValidEmail(String str) {
        return Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(str).matches();
    }

    public static String getString(Locale locale, Context context, int i) {
        return getLocaleStringResource(locale, context, i);
    }

    private static String getLocaleStringResource(Locale locale, Context context, int i) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration).getText(i).toString();
    }

    public static void underLineTextView(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | 8);
    }

    public static void StrikeText(TextView textView, String str) {
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        textView.setText(str, TextView.BufferType.SPANNABLE);
        ((Spannable) textView.getText()).setSpan(strikethroughSpan, 0, str.length(), 33);
    }

    private static boolean containsUppercase(String str) {
        return str.matches(".*[A-Z].*");
    }

    private static boolean containsLowercase(String str) {
        return str.matches(".*[a-z].*");
    }

    private static boolean containsNumeric(String str) {
        return str.matches(".*[0-9].*");
    }

    public static String passwordValidator(String str) {
        if (str.length() == 0) {
            return "Password field can't be empty";
        }
        if (str.equals(ApplicationSettings.PASSWORD[0])) {
            return "This password is not allowed. Please choose a different one";
        }
        if (str.length() < 8) {
            return "Minimum length for the password is 8";
        }
        if (!containsUppercase(str)) {
            return "Must contain at least one uppercase character";
        }
        if (!containsLowercase(str)) {
            return "Must contain at least one lowercase character";
        }
        return !containsNumeric(str) ? "Must contain at least one digit" : "";
    }

    public static String toTitleCase(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        String lowerCase = str.toLowerCase();
        char charAt = lowerCase.charAt(0);
        String upperCase = ("" + charAt).toUpperCase();
        return upperCase + lowerCase.substring(1);
    }

    public static boolean containsString(String... strArr) {
        if (strArr != null && strArr.length > 1) {
            String lowerCase = strArr[0].toLowerCase();
            for (int i = 1; i < strArr.length; i++) {
                if (strArr[i].toLowerCase().contains(lowerCase)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String removeSpace(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        return str.replace(" ", "").trim();
    }

    public static String insert(String str, String str2, int i) {
        Matcher matcher = Pattern.compile("(.{" + i + "})", 32).matcher(str);
        return matcher.replaceAll("$1" + str2);
    }
}
