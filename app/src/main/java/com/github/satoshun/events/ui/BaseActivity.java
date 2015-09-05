package com.github.satoshun.events.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.satoshun.events.AndroidApplication;
import com.github.satoshun.events.R;
import com.github.satoshun.events.internal.AppComponent;

public class BaseActivity extends AppCompatActivity {

    private Toolbar mActionBarToolbar;

    protected <T extends AppCompatActivity, M extends ViewDataBinding> M bindContentView(T activity, @LayoutRes int layoutResId) {
        M binding = DataBindingUtil.setContentView(activity, layoutResId);
        getActionBarToolbar();
        return binding;
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
