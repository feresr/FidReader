package com.globant.fernandoraviola.fidreader.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.fragments.EntryDetailFragment;
import com.globant.fernandoraviola.fidreader.models.Entry;


/**
 * This class shows details for a specific Entry. It's meant to be present only on portrait
 * orientation and will be destroyed upon rotation.
 */
public class DetailsActivity extends FragmentActivity {

    EntryDetailFragment entryDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.finish();
            return;
        }

        FragmentManager fm = getSupportFragmentManager();
        Entry entry = getIntent().getExtras().getParcelable(Entry.TAG);
        entryDetailFragment = (EntryDetailFragment) fm.findFragmentById(R.id.entry_details_frg);

        entryDetailFragment.updateEntry(entry);
        entryDetailFragment.refreshView();
    }
}
