package pl.checkersmobile.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;


public class ProgressDialogFragment extends DialogFragment {
    public static final String Dialog = "progress_dialog";
    private static final String ARG_MESSAGE = "messageResId";

    @NonNull
    public static ProgressDialogFragment newInstance(String message) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_MESSAGE, message);

        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.setArguments(arguments);
        progressDialogFragment.setCancelable(false);
        return progressDialogFragment;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(getDialogContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getArguments().getString(ARG_MESSAGE));
        return progressDialog;
    }

    private Context getDialogContext() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getActivity();
        } else {
            return new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light);
        }
    }
}
