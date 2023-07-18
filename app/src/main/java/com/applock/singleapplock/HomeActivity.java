package com.applock.singleapplock;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Context context;
    private SharedPrefsHelper sharedPrefsHelper;
    private boolean firstRun = true;
    private boolean tutorialCompletedSuccessfully = false;
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.homeactivity_main);
        initUtils();
        loadAppBar();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getIntentData();
        getSharedPreferencesData();
        this.sharedPrefsHelper.setSharePrefs(this.context, SharedPrefsKeys.ASK_FOR_VERIFICATION, true);
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
        Intent intent = new Intent(this.context, PasscodeCheckActivity.class);
        intent.putExtra("DEEP_LINK", str);
        startActivity(intent);
    }

    private void loadAppBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initUtils() {
        this.context = this;
        this.sharedPrefsHelper = new SharedPrefsHelper();
    }

    private void getIntentData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.tutorialCompletedSuccessfully = extras.getBoolean(IntentDataLabels.TUTORIAL_COMPLETE_SUCCESSFULLY);
        }
    }

    private void getSharedPreferencesData() {
        if (new SharedPrefsHelper().getSharedPrefs(this.context, SharedPrefsKeys.DEFAULT_ENCRYPTION_KEY, "").equals(ApplicationSettings.PASSWORD[0])) {
            new SharedPrefsHelper().setSharePrefs(this.context, SharedPrefsKeys.DEFAULT_ENCRYPTION_KEY, "");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                if (!getClass().getSimpleName().equals("HomeActivity")) {
                    reloadActivity(false);
                    break;
                }
                break;
            case R.id.nav_settings:
                gotoAccountSettings();
                break;
        }
        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
        return false;
    }

    private void gotoAccountSettings() {
        Intent intent = new Intent(this.context, PasscodeCheckActivity.class);
        intent.putExtra("PATH", "Settings");
        startActivityForResult(intent, 104);
    }

    public void reloadActivity(boolean z) {
        this.sharedPrefsHelper.setSharePrefs(this.context, SharedPrefsKeys.ASK_FOR_VERIFICATION, false);
        Intent intent = getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (z) {
            intent.putExtra("autoSyncReload", "reload");
        }
        if (((Boolean) this.sharedPrefsHelper.getSharedPrefs(this.context, SharedPrefsKeys.EXTENSION_PUSH_NOTIFICATION_SWITCH, false)).booleanValue()) {
            intent.putExtra("pushSyncReload", "reload");
        }
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.firstRun) {
            this.firstRun = false;
        }
        handleDeepLink();
    }

}
