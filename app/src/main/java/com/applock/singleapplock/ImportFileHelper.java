package com.applock.singleapplock;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

public class ImportFileHelper {
    private final Context context;
    private final String folderName = ApplicationSettings.DEFAULT_FOLDER_NAME[0];
    private final SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper();

    public ImportFileHelper(Context context2) {
        this.context = context2;
    }

    public TreeMap<Integer, File> getDefaultBackupFiles() {
        String str;
        if (((Boolean) this.sharedPrefsHelper.getSharedPrefs(this.context, SharedPrefsKeys.DEFAULT_LOCATION_FLAG, false)).booleanValue()) {
            str = (String) this.sharedPrefsHelper.getSharedPrefs(this.context, SharedPrefsKeys.CUSTOM_BACKUP_LOCATION_PATH, "");
        } else {
            str = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + this.folderName + "/";
        }
        File[] listFiles = new File(str).listFiles();
        HashMap hashMap = new HashMap();
        Arrays.sort(listFiles);
        Collections.reverse(Arrays.asList(listFiles));
        int i = 0;
        for (File file : listFiles) {
            if (file.getName().contains("TOTP_Backup") || file.getName().contains("export_data_external")) {
                hashMap.put(Integer.valueOf(i), file);
                i++;
            }
        }
        return new TreeMap<>(hashMap);
    }

    public String readCustomPathData(String str) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(str));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
                sb.append(10);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(sb);
    }

    public String readCustomPathData(Uri uri) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.context.getContentResolver().openInputStream(uri)));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    return sb.toString();
                }
                sb.append(readLine);
                sb.append(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
