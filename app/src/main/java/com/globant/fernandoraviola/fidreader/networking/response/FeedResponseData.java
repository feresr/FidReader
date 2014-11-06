package com.globant.fernandoraviola.fidreader.networking.response;

import java.util.ArrayList;

import com.globant.fernandoraviola.fidreader.models.Feed;

/**
 * Created by fernando.raviola on 11/4/2014.
 * Stores all attributes and methods related to a specific FeedResponseData.
 *
 */
public class FeedResponseData {
    private String query;
    private ArrayList<Feed> entries;

    public ArrayList<Feed> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Feed> entries) {
        this.entries = entries;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
