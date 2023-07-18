package com.applock.singleapplock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;



public class SetupPinActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Pinview pinview;
    private SharedPrefsHelper sharedPrefsHelper;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(new AppDefaultHelper(this).setAppTheme());
        setContentView((int) R.layout.activity_setup_pin);
        ((ImageView) findViewById(R.id.activity_setup_pin_toolbar_close_button)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.activity_setup_pin_toolbar_check_button)).setOnClickListener(this);
        this.context = this;
        this.sharedPrefsHelper = new SharedPrefsHelper();
        Pinview pinview2 = (Pinview) findViewById(R.id.pinview);
        this.pinview = pinview2;
        pinview2.setPinViewEventListener(new Pinview.PinViewEventListener() {
            public void onDataEntered(Pinview pinview, boolean z) {
                SetupPinActivity.this.lambda$onCreate$0$SetupPinActivity(pinview, z);
            }
        });
    }

    public void lambda$onCreate$0$SetupPinActivity(Pinview pinview2, boolean z) {
        launchSetupPinDialog(pinview2.getValue());
    }

    private void closeKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(this.pinview.getWindowToken(), 0);
        }
    }

    public void onBackPressed() {
        gotoMain();
    }

    private void goBack(boolean z, String str) {
        Intent intent = new Intent();
        if (z) {
            intent.putExtra(IntentDataLabels.SECURITY_TYPE, 0);
            intent.putExtra(IntentDataLabels.PIN, str);
            saveSecurityParams(str);
            setResult(-1, intent);
        } else {
            setResult(0, intent);
        }
        gotoMain();
        /*finish();*/
    }

    private void launchSetupPinDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetupPinActivity.this);
        builder.setTitle(getString(R.string.replace_previous_credentials_dialog_title));
        builder.setMessage(getString(R.string.replace_previous_credentials_dialog_message) + "\npress ok to confirm password: " + this.pinview.getValue());
        builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {

            public final void onClick(DialogInterface dialogInterface, int i) {
                SetupPinActivity.this.lambda$launchSetupPinDialog$1$SetupPinActivity(str,dialogInterface, i);
            }
        });
        builder.setNegativeButton(R.string.cancel_button, $$Lambda$SetupPinActivity$wWIsRaU02gxB5t3Mkzi6marDBQo.INSTANCE);
        builder.create().show();
    }

    public /* synthetic */ void lambda$launchSetupPinDialog$1$SetupPinActivity(String str, DialogInterface dialogInterface, int i) {
        goBack(true, str);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_setup_pin_toolbar_check_button /*2131361920*/:
                Pinview pinview2 = this.pinview;
                if (pinview2 == null) {
                    return;
                }
                if (pinview2.getValue().length() == 4) {
                    launchSetupPinDialog(this.pinview.getValue());
                    return;
                } else {
                    Toast.makeText(this.context, "Pin must be contain 4 digit", 0).show();
                    return;
                }
            case R.id.activity_setup_pin_toolbar_close_button :
                goBack(false, "");
                return;
            default:
                return;
        }
    }

    private void gotoMain() {
        Intent intent = new Intent(this.context, GeneralSettingsActivity.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

    private void saveSecurityParams(String str) {
        this.sharedPrefsHelper.setSharePrefs(this.context, SharedPrefsKeys.APP_SECURITY_SET, true);
        this.sharedPrefsHelper.setSharePrefs(this.context, SharedPrefsKeys.APP_SECURITY_IS_PIN, true);
        SharedPrefsHelper sharedPrefsHelper2 = this.sharedPrefsHelper;
        Context context2 = this.context;
        if (!StringHelper.isValidString(str)) {
            str = "";
        }
        sharedPrefsHelper2.setSharePrefs(context2, SharedPrefsKeys.APP_SECURITY_PIN, str);
    }
}
