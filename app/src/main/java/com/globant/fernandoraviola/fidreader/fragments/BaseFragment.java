package com.globant.fernandoraviola.fidreader.fragments;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by fernando.raviola on 11/10/2014.
 * <p/>
 * This is intended as a base class for all fragments in the application and provides basic
 * functionality that should be present in all of them.
 */
public class BaseFragment extends Fragment {

    protected ProgressDialog mProgressDialog;
    protected AlertDialog mErrorDialog;
    protected String title;
    private static String KEY_TITLE = "KEY_TITLE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            title = savedInstanceState.getString(KEY_TITLE);
        }
    }

    protected void showProgressDialog(int message) {
        if (getActivity() != null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(message));
            mProgressDialog.show();
        }
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null && title != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(title);
        }
    }

    protected void showErrorDialog(int message) {
        // getActivity could return null if the device is rotated and the activity restored.
        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(message)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            mErrorDialog = builder.create();
            mErrorDialog.show();
        }
    }

    protected void dismissErrorDialog() {
        if (mErrorDialog != null && mErrorDialog.isShowing()) {
            mErrorDialog.dismiss();
        }
    }

    protected void setTitle(String title) {
        this.title = title;
    }


    @Override
    public void onPause() {
        dismissProgressDialog();
        dismissErrorDialog();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TITLE, title);
    }
}
