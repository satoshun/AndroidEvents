package com.github.satoshun.events.ui;

import android.support.v4.app.Fragment;

import com.github.satoshun.events.AndroidApplication;
import com.github.satoshun.events.internal.AppComponent;

public class BaseFragment extends Fragment {

    protected AppComponent appComponent() {
        return ((AndroidApplication) getActivity().getApplication()).getAppComponent();
    }
}
