package com.applock.singleapplock;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class EnterPinCustomEditText extends EditText {
    public EnterPinCustomEditText(Context context) {
        super(context);
    }

    public EnterPinCustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EnterPinCustomEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setTypeface(Typeface typeface, int i) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "SourceSansPro-Bold.ttf"), i);
    }

    public void setTypeface(Typeface typeface) {
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "SourceSansPro-Bold.ttf"));
    }
}
