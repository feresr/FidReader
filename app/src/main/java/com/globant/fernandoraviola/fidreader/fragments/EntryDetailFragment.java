package com.globant.fernandoraviola.fidreader.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.helpers.Storage;
import com.globant.fernandoraviola.fidreader.models.Entry;


/**
 * This fragment displays details about about a particular entry.
 * The #updateEntry() method is used to set the entry to display.
 */
public class EntryDetailFragment extends BaseFragment {

    private TextView titleTextView;
    private TextView authorTextView;
    private TextView dateTextView;
    private WebView contentTextView;
    private CheckBox addToFavoriteCheckBox;
    private Entry entry;
    private CompoundButton.OnCheckedChangeListener checkListener;
    private Storage storage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_entry_detail, container, false);

        storage = Storage.getInstance(getActivity());

        titleTextView = (TextView) view.findViewById(R.id.title);
        authorTextView = (TextView) view.findViewById(R.id.author);
        dateTextView = (TextView) view.findViewById(R.id.date);
        contentTextView = (WebView) view.findViewById(R.id.content);
        addToFavoriteCheckBox = (CheckBox) view.findViewById(R.id.favorite_checkBox);
        checkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    storage.saveFavorite(entry);
                } else {
                    storage.removeFromFavorites(entry);
                }
            }
        };

        return view;
    }

    public void refreshView() {
        titleTextView.setText(Html.fromHtml(entry.getTitle()));
        authorTextView.setText(Html.fromHtml(entry.getAuthor()));
        dateTextView.setText(Html.fromHtml(entry.getPublishedDate()));
        contentTextView.loadData(entry.getContent(), "text/html", null);

        addToFavoriteCheckBox.setVisibility(View.VISIBLE);

    }

    public void updateEntry(final Entry entry) {
        this.entry = entry;

        //Every time we update an entry, we avoid changes to the db by nullifying the lister set on
        //the checkbox adapter.
        addToFavoriteCheckBox.setOnCheckedChangeListener(null);

        //Then, we set the correct state for the selected entry. Assured that this change won't
        // affect the db.
        addToFavoriteCheckBox.setChecked(storage.isFavorite(entry));

        //And put the listener back in. To start listening for user input.
        addToFavoriteCheckBox.setOnCheckedChangeListener(checkListener);
    }
}
