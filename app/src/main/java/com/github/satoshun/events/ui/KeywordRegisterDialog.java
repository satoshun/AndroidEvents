package com.github.satoshun.events.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.github.satoshun.events.R;
import com.github.satoshun.events.databinding.FragmentDialogKeywordRegisterBinding;

public class KeywordRegisterDialog extends DialogFragment {

    public static final String CALLBACK_INTENT_KEYWORD = "keyword";
    private FragmentDialogKeywordRegisterBinding binding;

    public static KeywordRegisterDialog newInstance(Fragment targetFragment, int resultCode) {
        KeywordRegisterDialog dialog = new KeywordRegisterDialog();
        dialog.setTargetFragment(targetFragment, resultCode);
        return dialog;
    }

    public KeywordRegisterDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        binding = FragmentDialogKeywordRegisterBinding.inflate(inflater,
                null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(R.string.register, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent data = new Intent();
                String keyword = binding.keyword.getText().toString();
                if (TextUtils.isEmpty(keyword)) {
                    return;
                }

                data.putExtra(CALLBACK_INTENT_KEYWORD, keyword);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
            }
        });
        builder.setView(binding.rootView);

        return builder.create();
    }
}
