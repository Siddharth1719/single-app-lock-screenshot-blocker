package com.applock.singleapplock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

public class Startactivity extends AppCompatActivity {

    private SharedPrefsHelper sharedPrefsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button button = findViewById(R.id.btn2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void handleDeepLink() {
        Intent intent = getIntent();
        if ("android.intent.action.VIEW".equals(intent.getAction())) {
            if (((Boolean) this.sharedPrefsHelper.getSharedPrefs(getApplicationContext(), SharedPrefsKeys.APP_SECURITY_SET, false)).booleanValue()) {
                Uri data = intent.getData();
                Objects.requireNonNull(data);
                passCodeCheck(data.toString());
            } else {
                Uri data2 = intent.getData();
                Objects.requireNonNull(data2);

            }
        }
        String stringExtra = intent.getStringExtra("DEEP_LINK");
        if (stringExtra != null) {

            getIntent().setData(null);
            getIntent().setAction(null);
        }
    }

    private void passCodeCheck(String str) {
        Intent intent = new Intent(Startactivity.this, PasscodeCheckActivity.class);
        intent.putExtra("DEEP_LINK", str);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleDeepLink();
    }
}