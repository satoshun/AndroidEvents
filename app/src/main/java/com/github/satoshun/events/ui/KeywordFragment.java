package com.github.satoshun.events.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.satoshun.events.databinding.FragmentKeywordBinding;
import com.github.satoshun.events.ui.adapter.SearchHistoryAdapter;
import com.github.satoshun.events.ui.presenter.KeywordPresenter;
import com.github.satoshun.events.ui.presenter.SearchHistoryPresenter;

import java.util.List;

import javax.inject.Inject;

public class KeywordFragment extends BaseFragment {

    public static final int DIALOG_FRAGMENT_CODE = 123;
    private static final String DIALOG_TAG = "SearchHistoryFragment_KeywordRegisterDialog";

    @Inject KeywordPresenter presenter;

    public void showRegisterDialog() {
        FragmentManager manager = getFragmentManager();
        KeywordRegisterDialog dialog = KeywordRegisterDialog.newInstance(this, DIALOG_FRAGMENT_CODE);
        dialog.show(manager, DIALOG_TAG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case DIALOG_FRAGMENT_CODE:
                    String keyword = data.getStringExtra(KeywordRegisterDialog.CALLBACK_INTENT_KEYWORD);
                    presenter.inputKeyword(keyword);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
