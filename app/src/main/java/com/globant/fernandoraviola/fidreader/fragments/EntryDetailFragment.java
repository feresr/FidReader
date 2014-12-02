package com.globant.fernandoraviola.fidreader.fragments;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.models.Entry;
import com.globant.fernandoraviola.fidreader.models.Favorite;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * This fragment displays details about about a particular entry.
 * The #updateEntry() method is used to set the entry to display.
 */
public class EntryDetailFragment extends BaseFragment {

    private TextView titleTextView;
    private TextView authorTextView;
    private TextView dateTextView;
    private TextView contentTextView;
    private CheckBox addToFavoriteCheckBox;
    private Entry entry;
    private CompoundButton.OnCheckedChangeListener checkListener;
    private Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_entry_detail, container, false);

        realm = Realm.getInstance(getActivity());

        titleTextView = (TextView) view.findViewById(R.id.title);
        authorTextView = (TextView) view.findViewById(R.id.author);
        dateTextView = (TextView) view.findViewById(R.id.date);
        contentTextView = (TextView) view.findViewById(R.id.content);
        addToFavoriteCheckBox = (CheckBox) view.findViewById(R.id.favorite_checkBox);
        checkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                realm.beginTransaction();

                if (isChecked) {

                    // Save entry as favorite
                    Favorite favorite = realm.createObject(Favorite.class);
                    favorite.setTitle(entry.getTitle());
                    favorite.setContent(entry.getContent());
                    favorite.setPublishedDate(entry.getPublishedDate());
                    favorite.setLink(entry.getLink());
                    favorite.setAuthor(entry.getAuthor());

                } else {

                    //Remove entry from favorites.
                    Favorite favorite = realm.where(Favorite.class)
                            .equalTo("title", entry.getTitle())
                            .findFirst();
                    if (favorite != null) {
                        favorite.removeFromRealm();
                    }
                }

                realm.commitTransaction();
            }
        };

        return view;
    }

    public void refreshView() {
        titleTextView.setText(Html.fromHtml(entry.getTitle()));
        authorTextView.setText(Html.fromHtml(entry.getAuthor()));
        dateTextView.setText(Html.fromHtml(entry.getPublishedDate()));
        contentTextView.setText(Html.fromHtml(entry.getContent()));
    }

    public void updateEntry(final Entry entry) {
        this.entry = entry;

        //Every time we update an entry, we avoid changes to the db by nullifying the lister set on
        //the checkbox adapter.
        addToFavoriteCheckBox.setOnCheckedChangeListener(null);

        //Then, we set the correct state for the selected entry. Assured that this change won't
        // affect the db.
        addToFavoriteCheckBox.setChecked(existsInDb(entry));

        //And put the listener back in. To start listening for user input.
        addToFavoriteCheckBox.setOnCheckedChangeListener(checkListener);
    }

    /**
     * Check if a particular entry exists in the db.
     *
     * @param entry Entry
     * @return whether a particular entry already exists inside the db or not.
     */
    public boolean existsInDb(Entry entry) {

        realm.beginTransaction();
        boolean existsInDb = realm.where(Favorite.class).equalTo("title", entry.getTitle()).findFirst() != null;
        realm.commitTransaction();
        return existsInDb;

    }
}
