package com.globant.fernandoraviola.fidreader.activities;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;

import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.fragments.EntryDetailFragment;
import com.globant.fernandoraviola.fidreader.fragments.FavoriteFragment;
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

        /* By default, this activity is not available on landscape mode. But in some cases we do
         need to use the portrait version. By sending a boolean extra "portrait_allowed" we specify
         whether this behaviour is available or not.
         Use cases where landscape mode is allowed include 'user reviewing favorite entries'. */
        boolean portrait_allowed = getIntent().getExtras().getBoolean(FavoriteFragment.PORTRAIT_ALLOWED, false);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !portrait_allowed) {
            this.finish();
            return;
        }
        setContentView(R.layout.activity_details);

        FragmentManager fm = getSupportFragmentManager();
        Entry entry = getIntent().getExtras().getParcelable(Entry.TAG);
        entryDetailFragment = (EntryDetailFragment) fm.findFragmentById(R.id.entry_details_frg);

        entryDetailFragment.updateEntry(entry);
        entryDetailFragment.refreshView();


        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.title_activity_details));
        }
    }
}
