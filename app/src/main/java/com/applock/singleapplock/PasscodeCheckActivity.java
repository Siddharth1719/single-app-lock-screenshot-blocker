package com.applock.singleapplock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class PasscodeCheckActivity extends AppCompatActivity implements EnterPinOverlayDialog.EnterPinOverlayDialogInterface, EnterFingerprintOverlayDialog.EnterFingerprintOverlayDialogInterface, AppSecurityHelper.AppSecuityCallback {
    private boolean appSecuritySet = false;
    private String destination;
    private String oldPin;
    private boolean pinSet = false;
    Pinview pinView;
    SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper();
    private ToastMaker toastMaker;

    public void enterPinOverlayDialogBackBtnClicked() {
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(new AppDefaultHelper(this).setAppTheme());
        setContentView((int) R.layout.activity_passcode_check);
        this.pinView = (Pinview) findViewById(R.id.pinview);
        TextView textView = (TextView) findViewById(R.id.activity_pass_code_check_title);
        TextView textView2 = (TextView) findViewById(R.id.activity_pass_code_check__disclaimer);
        this.destination = getIntent().getStringExtra("PATH");
        this.toastMaker = new ToastMaker(getApplicationContext());
        checkPref();
        if (!this.appSecuritySet) {
            passwordVerified();
        } else if (!this.pinSet || !StringHelper.isValidString(this.oldPin)) {
            AppSecurityHelper appSecurityHelper = new AppSecurityHelper(this, this);
            if (appSecurityHelper.checkBiometricEnable().length() == 0) {
                appSecurityHelper.showDiaLog();
            }
        } else {
            this.pinView.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            showPinEventListener();
        }
    }

    private void showPinEventListener() {
        this.pinView.setPinViewEventListener(new Pinview.PinViewEventListener() {
            public final void onDataEntered(Pinview pinview, boolean z) {
                PasscodeCheckActivity.this.lambda$showPinEventListener$0$PasscodeCheckActivity(pinview, z);
            }
        });
    }

    public void lambda$showPinEventListener$0$PasscodeCheckActivity(Pinview pinview, boolean z) {
        if (pinview.getValue().equals(this.oldPin)) {
            passwordVerified();
            return;
        }
        this.toastMaker.makeToast("Invalid Password", 0);
        pinview.setValue("");
    }

    private void passwordVerified() {
        String str = this.destination;
        if (str != null) {
            str.hashCode();
            if (str.equals("WIDGET")) {
                finish();
            } else if (str.equals("Settings")) {
                goToSettings();
            }
        } else {
            goToMain();
        }
    }

    public void goToMain() {
        try {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("DEEP_LINK", getIntent().getStringExtra("DEEP_LINK"));
            startActivity(intent);
            finish();
        } catch (Exception unused) {
            finish();
        }
    }

    public void goToSettings() {
        Intent intent = new Intent(getApplicationContext(), GeneralSettingsActivity.class);
        if (getIntent().getStringExtra("SCROLL") != null) {
            intent.putExtra("SCROLL", getIntent().getStringExtra("SCROLL"));
            intent.putExtra("HOME", "HOME");
        }
        startActivity(intent);
        finish();
    }

    private void checkPref() {
        this.appSecuritySet = ((Boolean) this.sharedPrefsHelper.getSharedPrefs(getApplicationContext(), SharedPrefsKeys.APP_SECURITY_SET, false)).booleanValue();
        boolean booleanValue = ((Boolean) this.sharedPrefsHelper.getSharedPrefs(getApplicationContext(), SharedPrefsKeys.APP_SECURITY_IS_PIN, false)).booleanValue();
        this.pinSet = booleanValue;
        if (booleanValue) {
            this.oldPin = (String) this.sharedPrefsHelper.getSharedPrefs(getApplicationContext(), SharedPrefsKeys.APP_SECURITY_PIN, "");
        }
    }

    public void verified() {
        String str = this.destination;
        if (str == null) {
            goToMain();
        } else if (str.equals("Settings")) {
            goToSettings();
        }
    }

    public void enterFingerprintOverlayDialogBackBtnClicked() {
        finish();
    }

    public void onBackPressed() {
        String str = this.destination;
        if (str != null && str.equals("WIDGET")) {
            ActivityCompat.finishAffinity(this);
        }
        super.onBackPressed();
    }

    public void onSuccess() {
        passwordVerified();
    }

    public void onFailure() {
        finish();
    }

    public void notEnrolled() {
        this.sharedPrefsHelper.getSharedPrefs(getApplicationContext(), SharedPrefsKeys.APP_SECURITY_SET, false);
        passwordVerified();
    }
}
