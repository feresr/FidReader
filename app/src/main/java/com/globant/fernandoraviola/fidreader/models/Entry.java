package com.globant.fernandoraviola.fidreader.models;

import java.util.ArrayList;

/**
 * Created by fernando.raviola on 11/4/2014.
 * <p/>
 * Stores all attributes and methods related to a specific Entry.
 */
public class Entry {

    private String title;
    private String link;
    private String author;
    private String publishedDate;
    private String contentSnippet;
    private String content;
    private ArrayList<String> categories;


    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getContentSnippet() {
        return contentSnippet;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}
