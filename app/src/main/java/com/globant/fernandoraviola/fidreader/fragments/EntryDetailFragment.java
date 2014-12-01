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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_entry_detail, container, false);

        titleTextView = (TextView) view.findViewById(R.id.title);
        authorTextView = (TextView) view.findViewById(R.id.author);
        dateTextView = (TextView) view.findViewById(R.id.date);
        contentTextView = (TextView) view.findViewById(R.id.content);
        addToFavoriteCheckBox = (CheckBox) view.findViewById(R.id.favorite_checkBox);
        addToFavoriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Realm realm = Realm.getInstance(getActivity());

                    Favorite isAlreadyAdded = realm.where(Favorite.class).contains("title",entry.getTitle()).findFirst();
                    if (isAlreadyAdded == null) {
                        // Transactions give you easy thread-safety
                        realm.beginTransaction();
                        Favorite favorite = realm.createObject(Favorite.class);
                        favorite.setTitle(entry.getTitle());
                        favorite.setContent(entry.getContent());
                        favorite.setPublishedDate(entry.getPublishedDate());
                        favorite.setLink(entry.getLink());
                        favorite.setAuthor(entry.getAuthor());
                    }

                    realm.commitTransaction();
                }
            }
        });
        return view;
    }

    public void refreshView() {
        titleTextView.setText(Html.fromHtml(entry.getTitle()));
        authorTextView.setText(Html.fromHtml(entry.getAuthor()));
        dateTextView.setText(Html.fromHtml(entry.getPublishedDate()));
        contentTextView.setText(Html.fromHtml(entry.getContent()));
    }

    public void updateEntry(Entry entry) {
        this.entry = entry;
    }
}
