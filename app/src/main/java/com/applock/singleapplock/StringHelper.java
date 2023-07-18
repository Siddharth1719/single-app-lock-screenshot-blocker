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
}
