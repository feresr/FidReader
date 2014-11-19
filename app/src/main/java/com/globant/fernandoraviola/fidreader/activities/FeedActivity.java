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
    private boolean isDualPane;


    EntryDetailFragment entryDetailFragment;
    EntryListFragment entryListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigator = new Navigator(this);
        setContentView(R.layout.activity_feed);

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

        entryDetailFragment = (EntryDetailFragment) getSupportFragmentManager().findFragmentById(
                R.id.entry_details_frg);
        entryListFragment = (EntryListFragment) getSupportFragmentManager().findFragmentById(R.id.entry_list_frg);

    }

    @Override
    protected void onResume() {
        super.onResume();

        isDualPane = findViewById(R.id.container) == null;
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
            entryDetailFragment.displayEntry(entry);
        } else {
            // If we're not in dual pane, we need to replace the current fragment with "details fragment"
            navigator.pushFragment(EntryDetailFragment.newInstance(entry.getTitle(), entry.getAuthor(), entry.getPublishedDate(), entry.getContent()), null, true);
        }
    }
    @Override
    public void saveEntry(ArrayList<Entry> entries) {
        headlessFragment.setEntries(entries);
    }

    @Override
    public ArrayList<Entry> loadEntries() {
        return headlessFragment.getEntries();
    }
}