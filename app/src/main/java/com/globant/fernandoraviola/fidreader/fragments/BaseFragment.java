package com.globant.fernandoraviola.fidreader.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

/**
 * Created by fernando.raviola on 11/10/2014.
 */
public class BaseFragment extends Fragment {

    protected ProgressDialog mDialog;

    protected void showProgressDialog(int message) {
        if (getActivity() != null) {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setIndeterminate(false);
            mDialog.setCancelable(false);
            mDialog.setMessage(getString(message));
            mDialog.show();
        }
    }

    protected void dismissProgressDialog() {
        if(mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    protected void showErrorDialog(int message) {
        // getActivity could return null if the device is rotated and the activity restored.
        if(getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(message)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.create().show();
        }
    }

}
