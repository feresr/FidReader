package com.globant.fernandoraviola.fidreader.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;

import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.fragments.EntryDetailFragment;
import com.globant.fernandoraviola.fidreader.fragments.EntryListFragment;
import com.globant.fernandoraviola.fidreader.models.Entry;

/**
 * This class shows a list of entries corresponding to a particular feed in both single and dual
 * pane. It handles interactions between EntryListFragment and EntryDetailFragment displaying
 * details about the selected entry on a fragment or in new Activity depending upon the current
 * orientation of the device.
 */
public class FeedActivity extends FragmentActivity implements EntryListFragment.EntryListCallbacksInterface {

    public static final String FEED_URL_TAG = "FEED_URL";
    EntryDetailFragment entryDetailFragment;
    EntryListFragment entryListFragment;

    /**
     * Indicates whether two pane view is enabled or not.
     */
    private boolean isDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feed);

        isDualPane = findViewById(R.id.entry_details_frg) != null && findViewById(R.id.entry_details_frg).getVisibility() == View.VISIBLE;

        entryDetailFragment = (EntryDetailFragment) getSupportFragmentManager().findFragmentById(R.id.entry_details_frg);
        entryListFragment = (EntryListFragment) getSupportFragmentManager().findFragmentById(R.id.entry_list_frg);

        if (savedInstanceState == null) {
            //If it's the FIRST time we get into the activity, we will be passed in a string with the Feed Url
            entryListFragment.setFeedUrl(getIntent().getExtras().getString(FEED_URL_TAG));
        }

        //Provides back navigation on the action bar
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        entryListFragment.showPreviouslySelectedEntry();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEntryDetails(Entry entry) {

        if (isDualPane) {
            entryDetailFragment.updateEntry(entry);
            entryDetailFragment.refreshView();
        } else {
            Intent i = new Intent(this, DetailsActivity.class);
            i.putExtra(Entry.TAG, entry);
            startActivity(i);
        }
    }
}