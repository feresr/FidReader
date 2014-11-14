package com.globant.fernandoraviola.fidreader.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.globant.fernandoraviola.fidreader.models.Feed;

import java.util.ArrayList;

/**
 * Created by fernando.raviola on 11/14/2014.
 */
public class HeadlessFragment extends Fragment{

    private ArrayList<Feed> feeds;
    public static String TAG = "headless";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //The heart and mind of headless fragment is below line. It will keep the fragment alive during configuration change when activities and   //subsequent fragments are "put to death" and recreated
        setRetainInstance(true);
    }

    public ArrayList<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(ArrayList<Feed> feeds) {
        this.feeds = feeds;
    }

    public interface HeadlessInterface{
        public void saveFeeds(ArrayList<Feed> feeds);

        public ArrayList<Feed> loadFeeds();
    }
}
