package com.globant.fernandoraviola.fidreader.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.adapters.EntryAdapter;
import com.globant.fernandoraviola.fidreader.models.Entry;
import com.globant.fernandoraviola.fidreader.models.Favorite;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * interface.
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

        Realm realm = Realm.getInstance(getActivity());

        // TODO: Change Adapter to display your content

        RealmResults<Favorite> favorites = realm.where(Favorite.class).findAll();

        setListAdapter(new ArrayAdapter<Favorite>(getActivity(),android.R.layout.simple_list_item_1, favorites));

    }





    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


    }

}
