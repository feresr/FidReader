package com.globant.fernandoraviola.fidreader.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.globant.fernandoraviola.fidreader.helpers.Storage;
import com.globant.fernandoraviola.fidreader.models.Favorite;

import io.realm.RealmResults;

/**
 * Shows a list all those entries saved as favorites.
 */
public class FavoriteFragment extends ListFragment {


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Storage storage = Storage.getInstance(getActivity());

        RealmResults<Favorite> favorites = storage.getAllFavorites();

        setListAdapter(new ArrayAdapter<Favorite>(getActivity(), android.R.layout.simple_list_item_1, favorites));

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


    }

}
