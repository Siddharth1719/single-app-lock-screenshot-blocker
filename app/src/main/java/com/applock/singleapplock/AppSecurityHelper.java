package com.applock.singleapplock;

import android.content.Context;


import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;


public class AppSecurityHelper {
    public AppSecuityCallback appSecuityCallback;
    private Context context;

    public interface AppSecuityCallback {
        void notEnrolled();

        void onFailure();

        void onSuccess();
    }

    public AppSecurityHelper(Context context2, AppSecuityCallback appSecuityCallback2) {
        this.context = context2;
        this.appSecuityCallback = appSecuityCallback2;
    }

    public String checkBiometricEnable() {
        int canAuthenticate = BiometricManager.from(this.context).canAuthenticate();
        if (canAuthenticate == 0) {
            return "";
        }
        if (canAuthenticate == 1) {
            return "Biometric features are currently unavailable.";
        }
        if (canAuthenticate != 11) {
            return canAuthenticate != 12 ? "Error" : "No biometric features available on this device.";
        }
        this.appSecuityCallback.notEnrolled();
        return "No device security enabled";
    }

    public static boolean isSecurityEnable(Context context2) {
        return ((Boolean) new SharedPrefsHelper().getSharedPrefs(context2, SharedPrefsKeys.APP_SECURITY_SET, false)).booleanValue();
    }

    public void showDiaLog() {
        new BiometricPrompt((FragmentActivity) this.context, ContextCompat.getMainExecutor(this.context), (BiometricPrompt.AuthenticationCallback) new BiometricPrompt.AuthenticationCallback() {
            public void onAuthenticationError(int i, CharSequence charSequence) {
                super.onAuthenticationError(i, charSequence);
                AppSecurityHelper.this.appSecuityCallback.onFailure();
            }

            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult authenticationResult) {
                super.onAuthenticationSucceeded(authenticationResult);
                AppSecurityHelper.this.appSecuityCallback.onSuccess();
            }

            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                AppSecurityHelper.this.appSecuityCallback.onFailure();
            }
        }).authenticate(new BiometricPrompt.PromptInfo.Builder().setTitle("Welcome to totp authenticator").setSubtitle("Please verify to continue").setDeviceCredentialAllowed(true).build());
    }
}
