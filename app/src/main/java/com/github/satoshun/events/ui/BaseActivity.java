package com.github.satoshun.events.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.satoshun.events.AndroidApplication;
import com.github.satoshun.events.R;
import com.github.satoshun.events.internal.AppComponent;

public class BaseActivity extends AppCompatActivity {

    private Toolbar mActionBarToolbar;

    private void setupNavDrawer() {
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }

    protected AppComponent appComponent() {
        return ((AndroidApplication) getApplication()).getAppComponent();
    }
}
