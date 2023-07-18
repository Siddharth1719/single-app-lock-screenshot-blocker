package com.applock.singleapplock;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

import java.util.Objects;

public class EnterFingerprintOverlayDialog extends DialogFragment implements FingerprintHandlerInterface, FingerPrintAuthCallback {
    private TextView activityDesc;
    private TextView activityTitle;
    private Context context;
    private EnterFingerprintOverlayDialogInterface enterFingerprintOverlayDialogInterface;
    private FingerPrintAuthHelper fingerPrintAuthHelper;
    private ImageView fingerprintImageView;
    private TextView toastMsgTextView;

    public interface EnterFingerprintOverlayDialogInterface {
        void enterFingerprintOverlayDialogBackBtnClicked();

        void verified();
    }

    public void onBelowMarshmallow() {
    }

    public void onNoFingerPrintHardwareFound() {
    }

    public void onNoFingerPrintRegistered() {
    }

    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        releaseFingerprint();
        placeToastMessage(this.context.getString(R.string.authentication_success_msg_string), false);
        this.fingerprintImageView.setImageResource(R.drawable.activity_ico_fingerprint_correct_192x192);
        this.enterFingerprintOverlayDialogInterface.verified();
        try {
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAuthFailed(int i, String str) {
        placeToastMessage("Authentication error \nretry", true);
        this.fingerprintImageView.setImageResource(R.drawable.activity_ico_fingerprint_incorrect_192x192);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onAttach(Context context2) {
        this.context = context2;
        super.onAttach(context2);
        this.enterFingerprintOverlayDialogInterface = (EnterFingerprintOverlayDialogInterface) this.context;
        this.fingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(context2, this);
        setStyle(0, R.style.FullScreenDialogStyle);
        this.fingerPrintAuthHelper.startAuth();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.enter_fingerprint_overlay_dialog, viewGroup, false);
        setCancelable(false);
        this.fingerprintImageView = (ImageView) inflate.findViewById(R.id.fingerprint_image_view);
        this.toastMsgTextView = (TextView) inflate.findViewById(R.id.enter_fp_dialog_toast_msg_text_view);
        this.activityTitle = (TextView) inflate.findViewById(R.id.enter_fp_dialog_main_wrapper_heading);
        this.activityDesc = (TextView) inflate.findViewById(R.id.enter_fp_dialog_main_wrapper_disclaimer);
        ((TextView) inflate.findViewById(R.id.enter_fp_dialog_back_btn)).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                EnterFingerprintOverlayDialog.this.lambda$onCreateView$0$EnterFingerprintOverlayDialog(view);
            }
        });
        clearFingerprint();
        if (AppDefaultHelper.getThemeId(getActivity()) == 1) {
            inflate.setBackgroundColor(getResources().getColor(R.color.black));
            this.activityTitle.setTextColor(getResources().getColor(R.color.colorWhite));
            this.activityDesc.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        return inflate;
    }

    public /* synthetic */ void lambda$onCreateView$0$EnterFingerprintOverlayDialog(View view) {
        this.enterFingerprintOverlayDialogInterface.enterFingerprintOverlayDialogBackBtnClicked();
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            Objects.requireNonNull(window);
            window.setLayout(-1, -1);
        }
    }

    public void onStop() {
        super.onStop();
        FingerPrintAuthHelper fingerPrintAuthHelper2 = this.fingerPrintAuthHelper;
        if (fingerPrintAuthHelper2 != null) {
            fingerPrintAuthHelper2.stopAuth();
        }
    }

    private void clearFingerprint() {
        this.fingerprintImageView.setImageResource(R.drawable.activity_ico_fingerprint_192x192);
        this.toastMsgTextView.setText("");
        this.toastMsgTextView.setVisibility(8);
    }

    private void placeToastMessage(String str, boolean z) {
        Context context2;
        int i;
        this.toastMsgTextView.setVisibility(0);
        this.toastMsgTextView.setText(str);
        TextView customTextViewOpenSans = this.toastMsgTextView;
        if (z) {
            context2 = this.context;
            i = R.color.colorAccent;
        } else {
            context2 = this.context;
            i = R.color.colorPrimary;
        }
        customTextViewOpenSans.setTextColor(ContextCompat.getColor(context2, i));
    }

    private void releaseFingerprint() {
        if (Build.VERSION.SDK_INT >= 23) {
            this.fingerPrintAuthHelper.stopAuth();
        }
    }

    public void fatalErrorOccurred(String str) {
        placeToastMessage(str, true);
        this.fingerprintImageView.setImageResource(R.drawable.activity_ico_fingerprint_incorrect_192x192);
    }

    public void authenticationErrorOccurred(String str) {
        placeToastMessage(str, true);
        this.fingerprintImageView.setImageResource(R.drawable.activity_ico_fingerprint_incorrect_192x192);
    }

    public void authenticationFailed() {
        placeToastMessage(this.context.getString(R.string.authentication_failed_msg), true);
        this.fingerprintImageView.setImageResource(R.drawable.activity_ico_fingerprint_incorrect_192x192);
    }

    public void authenticationSucceeded() {
        releaseFingerprint();
        placeToastMessage(this.context.getString(R.string.authentication_success_msg_string), false);
        this.fingerprintImageView.setImageResource(R.drawable.activity_ico_fingerprint_correct_192x192);
        this.enterFingerprintOverlayDialogInterface.verified();
        dismiss();
    }
}
