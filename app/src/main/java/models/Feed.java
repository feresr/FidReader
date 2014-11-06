package models;

import java.util.ArrayList;

/**
 * Created by fernando.raviola on 11/4/2014.
 *
 * Stores all attributes and methods related to a specific Feed.
 *
 */
public class Feed {

    private String url;
    private String title;
    private String contentSnippet;
    private String link;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentSnippet() {
        return contentSnippet;
    }

    public void setContentSnippet(String contentSnippet) {
        this.contentSnippet = contentSnippet;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return title + " -- url: " + url;
    }
}
