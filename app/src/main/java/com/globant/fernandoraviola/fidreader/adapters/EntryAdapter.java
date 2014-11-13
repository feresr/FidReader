package com.globant.fernandoraviola.fidreader.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.models.Entry;

import java.util.ArrayList;

/**
 * Created by fernando.raviola on 11/12/2014.
 * <p/>
 * Adapter for entries
 * Configures the way Entries appear on EntryFragment
 */

public class EntryAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private ArrayList<Entry> entries = new ArrayList<Entry>();

    public EntryAdapter(Context context, int resource) {
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return entries.size();
    }

    @Override
    public Entry getItem(int position) {
        return entries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        EntryViewholder entryViewholder;

        if (view == null) {

            view = View.inflate(context, resource, null);
            entryViewholder = new EntryViewholder(view);
            view.setTag(entryViewholder);

        } else {
            entryViewholder = (EntryViewholder) view.getTag();
        }

        Entry entry = getItem(position);

        entryViewholder.title.setText(entry.getTitle());
        entryViewholder.author.setText(entry.getAuthor());
        entryViewholder.date.setText(entry.getPublishedDate());
        entryViewholder.snippet.setText(entry.getContentSnippet());

        return view;
    }

    public void updateFeeds(ArrayList<Entry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    public class EntryViewholder {

        public final TextView title;
        public final TextView author;
        public final TextView date;
        public final TextView snippet;

        public EntryViewholder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
            date = (TextView) view.findViewById(R.id.date);
            snippet = (TextView) view.findViewById(R.id.snippet);
        }

    }
}
