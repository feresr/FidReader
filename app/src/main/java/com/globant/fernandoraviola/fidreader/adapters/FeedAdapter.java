package com.globant.fernandoraviola.fidreader.adapters;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.globant.fernandoraviola.fidreader.models.Feed;

/**
 * Created by fernando.raviola on 11/5/2014.
 * <p/>
 * Adapter for feeds
 * Configures the way Feeds appear on the listview
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

        View view = convertView;
        FeedViewHolder feedViewHolder;

        if (view == null) {
            view = View.inflate(context, resource, null);
            feedViewHolder = new FeedViewHolder(view);
            view.setTag(feedViewHolder);
        } else {
            feedViewHolder = (FeedViewHolder) view.getTag();
        }

        Feed feed = getItem(position);
        feedViewHolder.feedTitle.setText(Html.fromHtml(feed.getTitle()));
        feedViewHolder.feedLink.setText(Html.fromHtml(feed.getLink()));

        return view;
    }

    public void updateFeeds(ArrayList<Feed> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    private static class FeedViewHolder {

        public final TextView feedTitle;
        public final TextView feedLink;

        public FeedViewHolder(View view) {
            this.feedTitle = (TextView) view.findViewById(android.R.id.text1);
            this.feedLink = (TextView) view.findViewById(android.R.id.text2);
        }
    }
}
