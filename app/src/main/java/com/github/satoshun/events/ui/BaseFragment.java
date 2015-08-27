package com.github.satoshun.events.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.satoshun.events.AndroidApplication;
import com.github.satoshun.events.R;
import com.github.satoshun.events.databinding.FragmentEventBinding;
import com.github.satoshun.events.internal.AppComponent;
import com.github.satoshun.events.network.Connpass;
import com.github.satoshun.events.network.ConnpassResponse;
import com.github.satoshun.events.widget.EventAdapter;

import javax.inject.Inject;

import rx.Observer;
import rx.subscriptions.CompositeSubscription;

public class BaseFragment extends Fragment {
    protected AppComponent appComponent() {
        return ((AndroidApplication) getActivity().getApplication()).getAppComponent();
    }
}
