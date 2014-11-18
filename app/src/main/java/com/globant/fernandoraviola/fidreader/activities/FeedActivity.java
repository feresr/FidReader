package com.globant.fernandoraviola.fidreader.activities;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.fragments.EntryDetailFragment;
import com.globant.fernandoraviola.fidreader.fragments.EntryListFragment;
import com.globant.fernandoraviola.fidreader.fragments.HeadlessFragment;
import com.globant.fernandoraviola.fidreader.helpers.Navigator;
import com.globant.fernandoraviola.fidreader.models.Entry;

import java.util.ArrayList;

public class FeedActivity extends FragmentActivity implements EntriesInterface, HeadlessFragment.HeadlessEntriesInterface {

    public static final String FEED_URL_TAG = "FEED_URL";

    /**
     * Used to navigate back and forth between fragments.
     */
    private Navigator navigator;

    /**
     * Headless fragment used to preserve states between rotations
     */
    private HeadlessFragment headlessFragment;

    /**
     * Indicates whether two pane view is enabled or not.
     */
    Boolean isDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        navigator = new Navigator(this);

        View entryDetailView = findViewById(R.id.entry_details_frg);
        isDualPane = entryDetailView != null &&
                entryDetailView.getVisibility() == View.VISIBLE;

        //Provides back navigation on the action bar
        getActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        headlessFragment = (HeadlessFragment) fragmentManager
                .findFragmentByTag(HeadlessFragment.TAG);

        if (headlessFragment == null) {
            headlessFragment = new HeadlessFragment();
            fragmentManager.beginTransaction().add(headlessFragment,
                    HeadlessFragment.TAG).commit();
        }

        if (!isDualPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, EntryListFragment.newInstance(getIntent().getExtras().getString(FEED_URL_TAG)))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed, menu);
        return true;
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
            // I know I should update the details fragment and not replace it with another one
            // I will reformat this once it's working
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction t = fragmentManager.beginTransaction();
            t.replace(R.id.entry_details_frg , EntryDetailFragment.newInstance(entry.getTitle(), entry.getAuthor(), entry.getPublishedDate(), entry.getContent()));

        } else {
            // If we're not in dual pane, we need to replace the current fragment "details fragment"
            navigator.pushFragment(EntryDetailFragment.newInstance(entry.getTitle(), entry.getAuthor(), entry.getPublishedDate(), entry.getContent()), null, true);
        }
    }
    @Override
    public void saveEntry(ArrayList<Entry> entries) {
        headlessFragment.setEntries(entries);
    }

    @Override
    public ArrayList<Entry> loadEntry() {
        return headlessFragment.getEntries();
    }
}