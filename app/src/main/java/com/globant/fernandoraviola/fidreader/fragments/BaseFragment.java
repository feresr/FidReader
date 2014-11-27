package com.globant.fernandoraviola.fidreader.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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


    @Override
    public void onPause() {
        dismissProgressDialog();
        dismissErrorDialog();
        super.onPause();
    }
}
