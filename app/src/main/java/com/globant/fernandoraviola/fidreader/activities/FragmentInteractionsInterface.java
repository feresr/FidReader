package com.globant.fernandoraviola.fidreader.activities;


/**
 * Created by fernando.raviola on 11/12/2014.
 * <p/>
 * This interface handles fragment-activity communication
 */
public interface FragmentInteractionsInterface {

    void showFeedEntries(String feedUrl);

    void onSectionAttached(int section);
}
