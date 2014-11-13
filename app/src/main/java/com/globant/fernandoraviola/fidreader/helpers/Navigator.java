package com.globant.fernandoraviola.fidreader.helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.globant.fernandoraviola.fidreader.R;

/**
 * Created by fernando.raviola on 11/10/2014.
 * <p/>
 * Helper class to navigate between fragments
 */
public class Navigator {

    FragmentManager mFragmentManager;

    public Navigator(FragmentActivity activity) {
        mFragmentManager = activity.getSupportFragmentManager();
    }

    /**
     * Initializes a fragment transaction
     *
     * @param fragment       the fragment to be inserted
     * @param tag            Unique identifier for the fragment
     * @param addToBackstack boolean, should this fragment be added to the back stack.
     * @return
     */
    public int pushFragment(Fragment fragment, String tag, boolean addToBackstack) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (addToBackstack) {
            transaction.addToBackStack(tag);
        }

        transaction.replace(R.id.container, fragment);
        return transaction.commit();
    }
}
