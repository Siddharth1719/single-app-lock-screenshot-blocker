package com.applock.singleapplock;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kotlin.text.Typography;

public class Pinview extends LinearLayout implements TextWatcher, View.OnFocusChangeListener, View.OnKeyListener {
    private final float DENSITY;
    View currentFocus;
    private List<EditText> editTextList;
    InputFilter[] filters;
    private boolean finalNumberPin;
    private boolean fromSetValue;
    private InputType inputType;
    View.OnClickListener mClickListener;
    private boolean mCursorVisible;
    private boolean mDelPressed;
    private boolean mForceKeyboard;
    private String mHint;
    private PinViewEventListener mListener;
    private boolean mPassword;
    private int mPinBackground;
    private int mPinHeight;
    private int mPinLength;
    private int mPinWidth;
    private int mSplitWidth;
    private int mTextSize;
    LinearLayout.LayoutParams params;

    public enum InputType {
        TEXT,
        NUMBER
    }

    public interface PinViewEventListener {
        void onDataEntered(Pinview pinview, boolean z);
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public Pinview(Context context) {
        this(context, null);
    }

    public Pinview(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Pinview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.DENSITY = getContext().getResources().getDisplayMetrics().density;
        this.mPinLength = 4;
        this.editTextList = new ArrayList();
        this.mPinWidth = 50;
        this.mTextSize = 12;
        this.mPinHeight = 50;
        this.mSplitWidth = 20;
        this.mCursorVisible = false;
        this.mDelPressed = false;
        this.mPinBackground = R.drawable.sample_background;
        this.mPassword = false;
        this.mHint = "";
        this.inputType = InputType.TEXT;
        this.finalNumberPin = false;
        this.fromSetValue = false;
        this.mForceKeyboard = true;
        this.currentFocus = null;
        this.filters = new InputFilter[1];
        setGravity(17);
        init(context, attributeSet, i);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        removeAllViews();
        float f = this.DENSITY;
        this.mPinHeight = (int) (this.mPinHeight * f);
        this.mPinWidth = (int) (this.mPinWidth * f);
        this.mSplitWidth = (int) (this.mSplitWidth * f);
        setWillNotDraw(false);
        initAttributes(context, attributeSet, i);
        this.params = new LinearLayout.LayoutParams(this.mPinWidth, this.mPinHeight);
        setOrientation(0);
        createEditTexts();
        super.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean z;
                Iterator it = Pinview.this.editTextList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    EditText editText = (EditText) it.next();
                    if (editText.length() == 0) {
                        editText.requestFocus();
                        Pinview.this.openKeyboard();
                        z = true;
                        break;
                    }
                }
                if (!z && Pinview.this.editTextList.size() > 0) {
                    ((EditText) Pinview.this.editTextList.get(Pinview.this.editTextList.size() - 1)).requestFocus();
                }
                if (Pinview.this.mClickListener != null) {
                    Pinview.this.mClickListener.onClick(Pinview.this);
                }
            }
        });
        EditText editText = this.editTextList.get(0);
        if (editText != null) {
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Pinview.this.openKeyboard();
                }
            }, 200L);
        }
        updateEnabledState();
    }

    private void createEditTexts() {
        removeAllViews();
        this.editTextList.clear();
        for (int i = 0; i < this.mPinLength; i++) {
            EditText editText = new EditText(getContext());
            editText.setTextSize(this.mTextSize);
            this.editTextList.add(i, editText);
            addView(editText);
            generateOneEditText(editText, "" + i);
        }
        setTransformation();
    }

    private void initAttributes(Context context, AttributeSet attributeSet, int i) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Pinview, i, 0);
            this.mPinBackground = obtainStyledAttributes.getResourceId(R.styleable.Pinview_pinBackground, this.mPinBackground);
            this.mPinLength = obtainStyledAttributes.getInt(R.styleable.Pinview_pinLength, this.mPinLength);
            this.mPinHeight = (int) obtainStyledAttributes.getDimension(R.styleable.Pinview_pinHeight, this.mPinHeight);
            this.mPinWidth = (int) obtainStyledAttributes.getDimension(R.styleable.Pinview_pinWidth, this.mPinWidth);
            this.mSplitWidth = (int) obtainStyledAttributes.getDimension(R.styleable.Pinview_splitWidth, this.mSplitWidth);
            this.mTextSize = (int) obtainStyledAttributes.getDimension(R.styleable.Pinview_textSize, this.mTextSize);
            this.mCursorVisible = obtainStyledAttributes.getBoolean(R.styleable.Pinview_cursorVisible, this.mCursorVisible);
            this.mPassword = obtainStyledAttributes.getBoolean(R.styleable.Pinview_password, this.mPassword);
            this.mForceKeyboard = obtainStyledAttributes.getBoolean(R.styleable.Pinview_forceKeyboard, this.mForceKeyboard);
            this.mHint = obtainStyledAttributes.getString(R.styleable.Pinview_hint);
            this.inputType = InputType.values()[obtainStyledAttributes.getInt(R.styleable.Pinview_inputType, 0)];
            obtainStyledAttributes.recycle();
        }
    }

    private void generateOneEditText(EditText editText, String str) {
        LinearLayout.LayoutParams layoutParams = this.params;
        int i = this.mSplitWidth;
        layoutParams.setMargins(i / 2, i / 2, i / 2, i / 2);
        this.filters[0] = new InputFilter.LengthFilter(1);
        editText.setFilters(this.filters);
        editText.setLayoutParams(this.params);
        editText.setGravity(17);
        editText.setCursorVisible(this.mCursorVisible);
        if (!this.mCursorVisible) {
            editText.setClickable(false);
            editText.setHint(this.mHint);
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Pinview.this.mDelPressed = false;
                    return false;
                }
            });
        }
        editText.setBackgroundResource(this.mPinBackground);
        editText.setPadding(0, 0, 0, 0);
        editText.setTag(str);
        editText.setInputType(getKeyboardInputType());
        editText.addTextChangedListener(this);
        editText.setOnFocusChangeListener(this);
        editText.setOnKeyListener(this);
    }

    public static class AnonymousClass5 {
        static final int[] $SwitchMap$com$goodiebag$pinview$Pinview$InputType;

        static {
            int[] iArr = new int[InputType.values().length];
            $SwitchMap$com$goodiebag$pinview$Pinview$InputType = iArr;
            try {
                iArr[InputType.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$goodiebag$pinview$Pinview$InputType[InputType.TEXT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private int getKeyboardInputType() {
        return AnonymousClass5.$SwitchMap$com$goodiebag$pinview$Pinview$InputType[this.inputType.ordinal()] != 1 ? 1 : 18;
    }

    public String getValue() {
        StringBuilder sb = new StringBuilder();
        for (EditText editText : this.editTextList) {
            sb.append(editText.getText().toString());
        }
        return sb.toString();
    }

    public View requestPinEntryFocus() {
        EditText editText = this.editTextList.get(Math.max(0, getIndexOfCurrentFocus()));
        if (editText != null) {
            editText.requestFocus();
        }
        openKeyboard();
        return editText;
    }

    public void openKeyboard() {
        if (this.mForceKeyboard) {
            ((InputMethodManager) getContext().getSystemService("input_method")).toggleSoftInput(2, 1);
        }
    }

    public void clearValue() {
        setValue("");
    }

    public void setValue(String str) {
        this.fromSetValue = true;
        if (this.inputType != InputType.NUMBER || str.matches("[0-9]*")) {
            int i = -1;
            for (int i2 = 0; i2 < this.editTextList.size(); i2++) {
                if (str.length() > i2) {
                    this.editTextList.get(i2).setText(Character.valueOf(str.charAt(i2)).toString());
                    i = i2;
                } else {
                    this.editTextList.get(i2).setText("");
                }
            }
            int i3 = this.mPinLength;
            if (i3 > 0) {
                if (i < i3 - 1) {
                    this.currentFocus = this.editTextList.get(i + 1);
                } else {
                    this.currentFocus = this.editTextList.get(i3 - 1);
                    if (this.inputType == InputType.NUMBER || this.mPassword) {
                        this.finalNumberPin = true;
                    }
                    PinViewEventListener pinViewEventListener = this.mListener;
                    if (pinViewEventListener != null) {
                        pinViewEventListener.onDataEntered(this, false);
                    }
                }
                this.currentFocus.requestFocus();
            }
            this.fromSetValue = false;
            updateEnabledState();
        }
    }

    @Override
    public void onFocusChange(View view, boolean z) {
        if (!z || this.mCursorVisible) {
            if (!z || !this.mCursorVisible) {
                view.clearFocus();
            } else {
                this.currentFocus = view;
            }
        } else if (this.mDelPressed) {
            this.currentFocus = view;
            this.mDelPressed = false;
        } else {
            for (EditText editText : this.editTextList) {
                if (editText.length() == 0) {
                    if (editText != view) {
                        editText.requestFocus();
                        return;
                    } else {
                        this.currentFocus = view;
                        return;
                    }
                }
            }
            List<EditText> list = this.editTextList;
            if (list.get(list.size() - 1) != view) {
                List<EditText> list2 = this.editTextList;
                list2.get(list2.size() - 1).requestFocus();
                return;
            }
            this.currentFocus = view;
        }
    }

    private void setTransformation() {
        if (this.mPassword) {
            for (EditText editText : this.editTextList) {
                editText.removeTextChangedListener(this);
                editText.setTransformationMethod(new PinTransformationMethod());
                editText.addTextChangedListener(this);
            }
            return;
        }
        for (EditText editText2 : this.editTextList) {
            editText2.removeTextChangedListener(this);
            editText2.setTransformationMethod(null);
            editText2.addTextChangedListener(this);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        PinViewEventListener pinViewEventListener;
        if (charSequence.length() == 1 && this.currentFocus != null) {
            final int indexOfCurrentFocus = getIndexOfCurrentFocus();
            if (indexOfCurrentFocus < this.mPinLength - 1) {
                long j = 1;
                if (this.mPassword) {
                    j = 25;
                }
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EditText editText = (EditText) Pinview.this.editTextList.get(indexOfCurrentFocus + 1);
                        editText.setEnabled(true);
                        editText.requestFocus();
                    }
                }, j);
            }
            if ((indexOfCurrentFocus == this.mPinLength - 1 && this.inputType == InputType.NUMBER) || (indexOfCurrentFocus == this.mPinLength - 1 && this.mPassword)) {
                this.finalNumberPin = true;
            }
        } else if (charSequence.length() == 0) {
            int indexOfCurrentFocus2 = getIndexOfCurrentFocus();
            this.mDelPressed = true;
            if (this.editTextList.get(indexOfCurrentFocus2).getText().length() > 0) {
                this.editTextList.get(indexOfCurrentFocus2).setText("");
            }
        }
        for (int i4 = 0; i4 < this.mPinLength && this.editTextList.get(i4).getText().length() >= 1; i4++) {
            if (!this.fromSetValue && i4 + 1 == this.mPinLength && (pinViewEventListener = this.mListener) != null) {
                pinViewEventListener.onDataEntered(this, true);
            }
        }
        updateEnabledState();
    }

    private void updateEnabledState() {
        int max = Math.max(0, getIndexOfCurrentFocus());
        int i = 0;
        while (i < this.editTextList.size()) {
            this.editTextList.get(i).setEnabled(i <= max);
            i++;
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 1 || i != 67) {
            return false;
        }
        int indexOfCurrentFocus = getIndexOfCurrentFocus();
        if ((this.inputType == InputType.NUMBER && indexOfCurrentFocus == this.mPinLength - 1 && this.finalNumberPin) || (this.mPassword && indexOfCurrentFocus == this.mPinLength - 1 && this.finalNumberPin)) {
            if (this.editTextList.get(indexOfCurrentFocus).length() > 0) {
                this.editTextList.get(indexOfCurrentFocus).setText("");
            }
            this.finalNumberPin = false;
        } else if (indexOfCurrentFocus > 0) {
            this.mDelPressed = true;
            if (this.editTextList.get(indexOfCurrentFocus).length() == 0) {
                this.editTextList.get(indexOfCurrentFocus - 1).requestFocus();
                this.editTextList.get(indexOfCurrentFocus).setText("");
            } else {
                this.editTextList.get(indexOfCurrentFocus).setText("");
            }
        } else if (this.editTextList.get(indexOfCurrentFocus).getText().length() > 0) {
            this.editTextList.get(indexOfCurrentFocus).setText("");
        }
        return true;
    }

    public class PinTransformationMethod implements TransformationMethod {
        private char BULLET;

        @Override
        public void onFocusChanged(View view, CharSequence charSequence, boolean z, int i, Rect rect) {
        }

        private PinTransformationMethod() {
            this.BULLET = Typography.bullet;
        }

        @Override
        public CharSequence getTransformation(CharSequence charSequence, View view) {
            return new PasswordCharSequence(charSequence);
        }

        private class PasswordCharSequence implements CharSequence {
            private final CharSequence source;

            public PasswordCharSequence(CharSequence charSequence) {
                this.source = charSequence;
            }

            @Override
            public int length() {
                return this.source.length();
            }

            @Override
            public char charAt(int i) {
                return PinTransformationMethod.this.BULLET;
            }

            @Override
            public CharSequence subSequence(int i, int i2) {
                return new PasswordCharSequence(this.source.subSequence(i, i2));
            }
        }
    }

    private int getIndexOfCurrentFocus() {
        return this.editTextList.indexOf(this.currentFocus);
    }

    public int getSplitWidth() {
        return this.mSplitWidth;
    }

    public void setSplitWidth(int i) {
        this.mSplitWidth = i;
        int i2 = i / 2;
        this.params.setMargins(i2, i2, i2, i2);
        for (EditText editText : this.editTextList) {
            editText.setLayoutParams(this.params);
        }
    }

    public int getPinHeight() {
        return this.mPinHeight;
    }

    public void setPinHeight(int i) {
        this.mPinHeight = i;
        this.params.height = i;
        for (EditText editText : this.editTextList) {
            editText.setLayoutParams(this.params);
        }
    }

    public int getPinWidth() {
        return this.mPinWidth;
    }

    public void setPinWidth(int i) {
        this.mPinWidth = i;
        this.params.width = i;
        for (EditText editText : this.editTextList) {
            editText.setLayoutParams(this.params);
        }
    }

    public int getPinLength() {
        return this.mPinLength;
    }

    public void setPinLength(int i) {
        this.mPinLength = i;
        createEditTexts();
    }

    public boolean isPassword() {
        return this.mPassword;
    }

    public void setPassword(boolean z) {
        this.mPassword = z;
        setTransformation();
    }

    public String getHint() {
        return this.mHint;
    }

    public void setHint(String str) {
        this.mHint = str;
        for (EditText editText : this.editTextList) {
            editText.setHint(str);
        }
    }

    public int getPinBackground() {
        return this.mPinBackground;
    }

    public void setPinBackgroundRes(int i) {
        this.mPinBackground = i;
        for (EditText editText : this.editTextList) {
            editText.setBackgroundResource(i);
        }
    }

    @Override
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mClickListener = onClickListener;
    }

    public InputType getInputType() {
        return this.inputType;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
        int keyboardInputType = getKeyboardInputType();
        for (EditText editText : this.editTextList) {
            editText.setInputType(keyboardInputType);
        }
    }

    public void setPinViewEventListener(PinViewEventListener pinViewEventListener) {
        this.mListener = pinViewEventListener;
    }

    public void showCursor(boolean z) {
        this.mCursorVisible = z;
        List<EditText> list = this.editTextList;
        if (!(list == null || list.isEmpty())) {
            for (EditText editText : this.editTextList) {
                editText.setCursorVisible(z);
            }
        }
    }

    public void setTextSize(int i) {
        this.mTextSize = i;
        List<EditText> list = this.editTextList;
        if (!(list == null || list.isEmpty())) {
            for (EditText editText : this.editTextList) {
                editText.setTextSize(this.mTextSize);
            }
        }
    }

    public void setCursorColor(int i) {
        List<EditText> list = this.editTextList;
        if (!(list == null || list.isEmpty())) {
            for (EditText editText : this.editTextList) {
                setCursorColor(editText, i);
            }
        }
    }

    public void setTextColor(int i) {
        List<EditText> list = this.editTextList;
        if (!(list == null || list.isEmpty())) {
            for (EditText editText : this.editTextList) {
                editText.setTextColor(i);
            }
        }
    }

    public void setCursorShape(int i) {
        List<EditText> list = this.editTextList;
        if (!(list == null || list.isEmpty())) {
            for (EditText editText : this.editTextList) {
                try {
                    Field declaredField = TextView.class.getDeclaredField("mCursorDrawableRes");
                    declaredField.setAccessible(true);
                    declaredField.set(editText, Integer.valueOf(i));
                } catch (Exception unused) {
                }
            }
        }
    }

    private void setCursorColor(EditText editText, int i) {
        try {
            Field declaredField = TextView.class.getDeclaredField("mCursorDrawableRes");
            declaredField.setAccessible(true);
            int i2 = declaredField.getInt(editText);
            Field declaredField2 = TextView.class.getDeclaredField("mEditor");
            declaredField2.setAccessible(true);
            Object obj = declaredField2.get(editText);
            Drawable drawable = ContextCompat.getDrawable(editText.getContext(), i2);
            if (drawable != null) {
                drawable.setColorFilter(i, PorterDuff.Mode.SRC_IN);
            }
            Drawable[] drawableArr = {drawable, drawable};
            Field declaredField3 = obj.getClass().getDeclaredField("mCursorDrawable");
            declaredField3.setAccessible(true);
            declaredField3.set(obj, drawableArr);
        } catch (Exception unused) {
        }
    }
}