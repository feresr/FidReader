package com.globant.fernandoraviola.fidreader.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.activities.DetailsActivity;
import com.globant.fernandoraviola.fidreader.adapters.FavoriteAdapter;
import com.globant.fernandoraviola.fidreader.models.Entry;

/**
 * Shows a list all those entries saved as favorites.
 */
public class FavoriteFragment extends BaseFragment implements ListView.OnItemClickListener {

    public static final String PORTRAIT_ALLOWED = "PORTRAIT_ALLOWED";
    ListView listView;
    FavoriteAdapter adapter;
    
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        adapter = new FavoriteAdapter(getActivity(), R.layout.entry_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            title = getResources().getString(R.string.title_section2);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getActivity(), DetailsActivity.class);
        Entry entry = Entry.fromFavorite(adapter.getItem(position));
        i.putExtra(Entry.TAG, entry);
        i.putExtra(PORTRAIT_ALLOWED, true);
        startActivity(i);
    }
}