package models;

import java.util.ArrayList;

/**
 * Created by fernando.raviola on 11/4/2014.
 * Stores all attributes and methods related to a specific ResponseData.
 *
 */
public class ResponseData {
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
