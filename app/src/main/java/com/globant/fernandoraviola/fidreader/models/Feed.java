package com.globant.fernandoraviola.fidreader.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by fernando.raviola on 11/4/2014.
 * <p/>
 * Stores all attributes and methods related to a specific Feed.
 */
public class Feed implements Parcelable {

    private String url;
    private String title;
    private String contentSnippet;
    private String link;
    private String description;
    private ArrayList<Entry> entries;

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

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public String getDescription() {
        return description;
    }

    protected Feed(Parcel in) {
        url = in.readString();
        title = in.readString();
        contentSnippet = in.readString();
        link = in.readString();
        description = in.readString();
        if (in.readByte() == 0x01) {
            entries = new ArrayList<Entry>();
            in.readList(entries, Entry.class.getClassLoader());
        } else {
            entries = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(title);
        dest.writeString(contentSnippet);
        dest.writeString(link);
        dest.writeString(description);
        if (entries == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(entries);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Feed> CREATOR = new Parcelable.Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };
}
