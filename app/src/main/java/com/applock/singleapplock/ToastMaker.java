package com.applock.singleapplock;

import android.content.Context;
import android.widget.Toast;

public class ToastMaker {
    private Context context;

    public ToastMaker(Context context2) {
        this.context = context2;
    }

    public void makeToast(String str, int i) {
        if (i == 0) {
            Toast.makeText(this.context, str, 0).show();
        } else if (i == 1) {
            Toast.makeText(this.context, str, 1).show();
        }
    }

    public static void showToast(Context context2, String str) {
        Toast.makeText(context2, str, 0).show();
    }
}
