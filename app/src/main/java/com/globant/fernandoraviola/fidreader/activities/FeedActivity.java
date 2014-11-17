package com.globant.fernandoraviola.fidreader.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        navigator = new Navigator(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, EntryListFragment.newInstance(getIntent().getExtras().getString(FEED_URL_TAG)))
                    .commit();
        }
        getActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        headlessFragment = (HeadlessFragment) fragmentManager
                .findFragmentByTag(HeadlessFragment.TAG);

        if (headlessFragment == null) {
            headlessFragment = new HeadlessFragment();
            fragmentManager.beginTransaction().add(headlessFragment,
                    HeadlessFragment.TAG).commit();
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
        navigator.pushFragment(EntryDetailFragment.newInstance(entry.getTitle(), entry.getAuthor(), entry.getPublishedDate(), entry.getContent()), null, true);
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
