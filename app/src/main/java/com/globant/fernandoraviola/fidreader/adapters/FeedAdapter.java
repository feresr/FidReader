package com.globant.fernandoraviola.fidreader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.globant.fernandoraviola.fidreader.models.Feed;

import java.util.ArrayList;

/**
 * Created by fernando.raviola on 11/5/2014.
 * <p/>
 * Adapter for feeds
 * Configures the way Feeds appear on the listview
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private ArrayList<Feed> feeds = new ArrayList<Feed>();
    private int resource;
    private OnClickListener listener;

    public FeedAdapter(int resource, OnClickListener listener) {
        this.resource = resource;
        this.listener = listener;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View parent = LayoutInflater.from(context).inflate(resource, viewGroup, false);
        return FeedViewHolder.newInstance(parent);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder feedViewHolder, int position) {
        final Feed feed = feeds.get(position);
        feedViewHolder.setFeedTitle(Html.fromHtml(feed.getTitle()));
        feedViewHolder.setFeedLink(feed.getLink());
        feedViewHolder.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(feed);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return feeds.size();
    }

    public void updateFeeds(ArrayList<Feed> feeds) {
        this.feeds = feeds;
        notifyDataSetChanged();
    }

    /**
     * Custom interface for callbacks
     */
    public interface OnClickListener {
        public void onClick(Feed feed);
    }

    /**
     * Custom viewHolder class
     */
    public static final class FeedViewHolder extends RecyclerView.ViewHolder {

        public final TextView feedTitle;
        public final TextView feedLink;
        private View parent;

        public FeedViewHolder(View parent, TextView title, TextView feedLink) {
            super(parent);
            this.feedTitle = title;
            this.feedLink = feedLink;
            this.parent = parent;
        }

        public static FeedViewHolder newInstance(View itemView) {
            TextView feedTitle = (TextView) itemView.findViewById(android.R.id.text1);
            TextView feedLink = (TextView) itemView.findViewById(android.R.id.text2);
            return new FeedViewHolder(itemView, feedTitle, feedLink);
        }

        public void setFeedTitle(CharSequence text) {
            feedTitle.setText(text);
        }

        public void setFeedLink(CharSequence text) {
            feedLink.setText(text);
        }


        public void setClickListener(View.OnClickListener v) {
            parent.setOnClickListener(v);
        }
    }
}
