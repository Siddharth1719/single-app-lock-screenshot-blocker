package com.applock.singleapplock;

public class ApplicationSettings {
    public static final String[] DEFAULT_FOLDER_NAME = {"Single_App_Lock"};
    public static String DRIVE_PASSWORD = null;
    public static final String[] PASSWORD;

    static {
        String[] strArr = {"Single_App_Lock"};
        PASSWORD = strArr;
        DRIVE_PASSWORD = strArr[0];
    }
}
