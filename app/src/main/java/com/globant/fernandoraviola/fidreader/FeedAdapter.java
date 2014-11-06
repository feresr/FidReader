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
 *
 * Adapter for feeds
 * Configures the way Feeds appear on the listview
 *
 */
public class FeedAdapter extends BaseAdapter {

    private ArrayList<Feed> feeds = new ArrayList<Feed>();
    private Context context;
    private int resource;

    public FeedAdapter(Context context, int resource, ArrayList<Feed> feeds) {
        this.context = context;
        this.resource = resource;
        this.feeds = feeds;
    }

    @Override
    public int getCount() {
        return feeds.size();
    }

    @Override
    public Feed getItem(int position) {
        return feeds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView feedTitle;
        TextView feedLink;

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(resource, parent, false);
            feedTitle = (TextView) convertView.findViewById(android.R.id.text1);
            feedLink = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(new ViewHolder(feedTitle, feedLink));
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            feedTitle = viewHolder.feedTitle;
            feedLink = viewHolder.feedLink;
        }

        Feed feed = getItem(position);

        feedTitle.setText(Html.fromHtml(feed.getTitle()));
        feedLink.setText(Html.fromHtml(feed.getLink()));
        return convertView;
    }

    public void updateFeeds(ArrayList<Feed> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    private static class ViewHolder {

        public final TextView feedTitle;
        public final TextView feedLink;

        public ViewHolder(TextView feedTitle, TextView feedLink) {
            this.feedTitle = feedTitle;
            this.feedLink = feedLink;
        }
    }

}
