package com.applock.singleapplock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;


public class GeneralSettingsActivity extends BaseActivity implements View.OnClickListener, AppSecurityHelper.AppSecuityCallback {
    private AppDefaultHelper appDefaultHelper;
    private Context context;
    private SwitchCompat screenshotSwitch;
    private SwitchCompat securitySwitch;
    private SharedPrefsHelper sharedPrefsHelper;
    private ToastMaker toastMaker;
    private boolean appSecuritySet = false;
    private boolean screenshotFlag = false;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AppDefaultHelper appDefaultHelper = new AppDefaultHelper(this);
        this.appDefaultHelper = appDefaultHelper;
        setTheme(appDefaultHelper.setAppTheme());
        setContentView(R.layout.activity_general_settings);
        setSupportActionBar((Toolbar) findViewById(R.id.activity_general_settings_toolbar));
        this.context = this;
        this.sharedPrefsHelper = new SharedPrefsHelper();
        ImageView imageView = (ImageView) findViewById(R.id.activity_general_settings_toolbar_close_button);
        /*lock = (LinearLayout) findViewById(R.id.security_settings_layout);

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwitchCompat switchCompat5 = securitySwitch;
                switchCompat5.setChecked(!switchCompat5.isChecked());
            }
        });*/

        initViews();
        String str = (String) this.sharedPrefsHelper.getSharedPrefs(this.context, SharedPrefsKeys.DEVICE_SYNC_PRIORITY, "");
        if (str.length() == 0) {
            str = "AA";
        }
        imageView.setOnClickListener(this);
        this.toastMaker = new ToastMaker(this);
        getSharedPreferencesData();
        presetValues();
        this.screenshotSwitch.setOnCheckedChangeListener(onCheckedChanged());
        this.securitySwitch.setOnCheckedChangeListener(onCheckedChanged());
    }

    private void initViews() {
        this.screenshotSwitch = (SwitchCompat) findViewById(R.id.screenshot_switch);
        this.securitySwitch = (SwitchCompat) findViewById(R.id.security_switch);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_general_settings_toolbar_close_button:
                gotoMain();
                return;
            case R.id.security_settings_layout:
                SwitchCompat switchCompat5 = this.securitySwitch;
                switchCompat5.setChecked(!switchCompat5.isChecked());
                return;
            default:
                return;
        }
    }

    private void disableScreenshotFlag(boolean z) {
        String str;
        if (z) {
            str = getString(R.string.screenshot_enable_string);
        } else {
            str = getString(R.string.screenshot_disable_string);
        }
        this.sharedPrefsHelper.setSharePrefs(this.context, SharedPrefsKeys.SCREENSHOT_FLAG, Boolean.valueOf(z));
        this.toastMaker.makeToast(str, 0);
        refreshActivity();
    }

    private void getSharedPreferencesData() {
        this.appSecuritySet = AppSecurityHelper.isSecurityEnable(this.context);
        this.screenshotFlag = ((Boolean) this.sharedPrefsHelper.getSharedPrefs(this.context, SharedPrefsKeys.SCREENSHOT_FLAG, false)).booleanValue();
    }

    private void presetValues() {
        this.screenshotSwitch.setChecked(this.screenshotFlag);
        if (this.appSecuritySet) {
            this.securitySwitch.setChecked(true);
        }
    }

    private void gotoMain() {
        Intent intent = new Intent(this.context, HomeActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

    private void launchSetupSecurityDialog() {
        AppSecurityHelper appSecurityHelper = new AppSecurityHelper(this, this);
        if (appSecurityHelper.checkBiometricEnable().length() == 0) {
            appSecurityHelper.showDiaLog();
            return;
        }
        ToastMaker.showToast(this.context, appSecurityHelper.checkBiometricEnable());
        this.securitySwitch.setChecked(false);
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChanged() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                GeneralSettingsActivity.this.lambda$onCheckedChanged$28$GeneralSettingsActivity(compoundButton, z);
            }
        };
    }

    public void lambda$onCheckedChanged$28$GeneralSettingsActivity(CompoundButton compoundButton, boolean z) {
        switch (compoundButton.getId()) {

            case R.id.screenshot_switch:
                disableScreenshotFlag(z);
                return;
            case R.id.security_switch:
                if (z) {
                    launchSetupSecurityDialog();
                    return;
                }
                this.sharedPrefsHelper.setSharePrefs(this.context, SharedPrefsKeys.APP_SECURITY_SET, false);
                this.sharedPrefsHelper.setSharePrefs(this.context, SharedPrefsKeys.APP_SECURITY_IS_PIN, false);
                return;
            default:
                return;
        }
    }

    private void refreshActivity() {
        getIntent().setFlags(65536);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        gotoMain();
    }

    @Override
    public void onSuccess() {
        Log.e("TAG==", "onSuccess:===s " );
        this.sharedPrefsHelper.setSharePrefs(this.context, SharedPrefsKeys.APP_SECURITY_SET, true);
        this.sharedPrefsHelper.setSharePrefs(this.context, SharedPrefsKeys.APP_SECURITY_IS_PIN, false);
    }

    @Override
    public void onFailure() {
        Log.e("TAG==", "onSuccess:===f " );
        this.sharedPrefsHelper.setSharePrefs(this.context, SharedPrefsKeys.APP_SECURITY_SET, false);
        this.securitySwitch.setChecked(false);
    }

    @Override
    public void notEnrolled() {
        Log.e("TAG==", "onSuccess:===n " );
        startActivity(new Intent(this, SetupPinActivity.class));
    }
}