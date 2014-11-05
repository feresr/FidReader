package com.globant.fernandoraviola.fidreader;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import models.Feed;

/**
 * Created by fernando.raviola on 11/5/2014.
 */
public class FeedAdapter extends ArrayAdapter<Feed> {

    private ArrayList<Feed> feeds = new ArrayList<Feed>();
    private Context context;
    private int resource;

    public FeedAdapter(Context context, int resource, ArrayList<Feed> feeds) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.feeds = feeds;
    }

    @Override
    public int getCount() {
        return feeds.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Feed getItem(int position) {
        return feeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView feedTitle;
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(resource, null);

        }

        feedTitle = (TextView) v.findViewById(android.R.id.text1);

        Feed feed = getItem(position);

        feedTitle.setText(Html.fromHtml(feed.getTitle()));

        return v;
    }

    public void setFeeds(ArrayList<Feed> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }
}
