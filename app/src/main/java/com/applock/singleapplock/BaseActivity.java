package com.applock.singleapplock;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public abstract class BaseActivity extends AppCompatActivity {
    public static HashMap<String, Integer> accountsLabel;
    /*public static Map<String, CollapsedHeader> collapsedHash;*/
    public static Map<String, String> labelHash;

    public void onStart() {
        super.onStart();
        /*HashImageHelper.SetHashImages(getBaseContext());*/
        accountsLabel = new HashMap<>();
        if (AppDefaultHelper.isScreenShotEnable(this)) {
            getWindow().setFlags(8192, 8192);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initList();
    }

   /* public static void setActiveProductToDefault() {
        activeProduct = new ActiveProduct(ApplicationSettings.EXTENSION_SYNC_PURCHASE_ID, "EARLY SALE", "EARLY", "SALE", "100", "PERCENT", "#000000", "#000000", ApplicationSettings.APP_LINK);
    }*/

    private void initList() {
        HashMap hashMap = new HashMap();
        labelHash = hashMap;
        hashMap.put(getString(R.string.string_label_work), "#7D7CFE");
        labelHash.put(getString(R.string.string_label_personal), "#FF9B2E");
        labelHash.put(getString(R.string.string_label_social), "#88D1F7");
        labelHash.put(getString(R.string.string_label_entertainment), "#FB8772");
        labelHash.put(getString(R.string.string_label_tools), "#5FE79E");
        labelHash.put(getString(R.string.string_label_favourtite), "#F3E523");
        labelHash.put(getString(R.string.string_label_other), "#707070");
        labelHash = new TreeMap(labelHash);
        /*collapsedHash = StaticListHelper.getCollapsedStatus(getApplicationContext());*/
    }

    public void onPause() {
        super.onPause();
    }
}
