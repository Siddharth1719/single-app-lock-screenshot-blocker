package com.applock.singleapplock;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.Objects;

public class EnterPinOverlayDialog extends DialogFragment {
    private Context context;
    private EnterPinCustomEditText editText1;
    private EnterPinCustomEditText editText2;
    private EnterPinCustomEditText editText3;
    private EnterPinCustomEditText editText4;
    private EnterPinOverlayDialogInterface enterPinOverlayDialogInterface;
    private String oldPin;
    private String pin;
    private StringHelper stringHelper;
    private TextView toastMsgTextView;

    public interface EnterPinOverlayDialogInterface {
        void enterPinOverlayDialogBackBtnClicked();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onAttach(Context context2) {
        this.context = context2;
        super.onAttach(context2);
        this.enterPinOverlayDialogInterface = (EnterPinOverlayDialogInterface) this.context;
        this.stringHelper = new StringHelper();
        setStyle(0, R.style.FullScreenDialogStyle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.enter_pin_overlay_dialog, viewGroup, false);
        setCancelable(false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.oldPin = arguments.getString(IntentDataLabels.OLD_PIN);
        }
        this.toastMsgTextView = (TextView) inflate.findViewById(R.id.enter_pin_dialog_toast_msg_text_view);
        TextView customTextViewOpenSansBold = (TextView) inflate.findViewById(R.id.enter_pin_dialog_back_btn);
        TextView customTextViewOpenSansBold2 = (TextView) inflate.findViewById(R.id.enter_pin_dialog_proceed_btn);
        return inflate;
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
}
