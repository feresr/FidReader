package com.globant.fernandoraviola.fidreader.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.helpers.Storage;
import com.globant.fernandoraviola.fidreader.models.Favorite;


import java.util.List;

import io.realm.RealmResults;

/**
 * Created by fernando.raviola on 11/12/2014.
 * <p/>
 * Adapter for entries
 * Configures the way Favorites appear on EntryFragment
 */

public class FavoriteAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private List<Favorite> favorites;

    public FavoriteAdapter(Context context, int resource) {
        this.context = context;
        this.resource = resource;

        Storage storage = Storage.getInstance(context);
        favorites = storage.getAllFavorites();
    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public Favorite getItem(int position) {
        return favorites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        FavoriteViewholder favoriteViewholder;

        if (view == null) {

            view = View.inflate(context, resource, null);
            favoriteViewholder = new FavoriteViewholder(view);
            view.setTag(favoriteViewholder);

        } else {
            favoriteViewholder = (FavoriteViewholder) view.getTag();
        }

        Favorite favorite = getItem(position);

        favoriteViewholder.title.setText(favorite.getTitle());
        favoriteViewholder.author.setText(favorite.getAuthor());
        favoriteViewholder.date.setText(favorite.getPublishedDate());
        favoriteViewholder.snippet.setText(favorite.getContent());

        return view;
    }

    public class FavoriteViewholder {

        private final TextView title;
        private final TextView author;
        private final TextView date;
        private final TextView snippet;

        public FavoriteViewholder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
            date = (TextView) view.findViewById(R.id.date);
            snippet = (TextView) view.findViewById(R.id.snippet);
        }
    }
}
