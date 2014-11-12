package com.globant.fernandoraviola.fidreader.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.globant.fernandoraviola.fidreader.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link EntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryFragment extends BaseFragment {

    private static final String FEED_URL = "url";

    private String feedUrl;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param feedUrl feed url.
     *
     * @return A new instance of fragment EntryFragment.
     */
    public static EntryFragment newInstance(String feedUrl) {
        EntryFragment fragment = new EntryFragment();
        Bundle args = new Bundle();
        args.putString(FEED_URL, feedUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public EntryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            feedUrl = getArguments().getString(FEED_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entry, container, false);
    }
}
