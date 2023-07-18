package com.applock.singleapplock;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;



import java.util.ArrayList;
import java.util.Iterator;

public class EnterKeyCustomEditText extends EditText {
    ArrayList<EnterKeyEditTextOnChangeListener> listeners = new ArrayList<>();

    public void onTextCopy() {
    }

    public void onTextCut() {
    }

    public EnterKeyCustomEditText(Context context) {
        super(context);
    }

    public EnterKeyCustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EnterKeyCustomEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void addListener(EnterKeyEditTextOnChangeListener enterKeyEditTextOnChangeListener) {
        try {
            this.listeners.add(enterKeyEditTextOnChangeListener);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public boolean onTextContextMenuItem(int i) {
        boolean onTextContextMenuItem = super.onTextContextMenuItem(i);
        switch (i) {
            case 16908320:
                onTextCut();
                break;
            case 16908321:
                onTextCopy();
                break;
            case 16908322:
                onTextPaste();
                break;
        }
        return onTextContextMenuItem;
    }

    public void onTextPaste() {
        Iterator<EnterKeyEditTextOnChangeListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            it.next().onUpdate();
        }
    }
}
