package com.globant.fernandoraviola.fidreader.activities;

import android.support.v4.app.Fragment;

/**
 * Created by fernando.raviola on 11/12/2014.
 * <p/>
 * This interface handles fragment-activity communication
 */
public interface FragmentInteractionsInterface {

    void pushFragment(Fragment fragment, String tag, boolean addToBackstack);

    void onSectionAttached(int section);
}
