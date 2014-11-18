package com.globant.fernandoraviola.fidreader.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.activities.EntriesInterface;
import com.globant.fernandoraviola.fidreader.adapters.EntryAdapter;
import com.globant.fernandoraviola.fidreader.networking.GoogleFeedClient;
import com.globant.fernandoraviola.fidreader.networking.GoogleFeedInterface;
import com.globant.fernandoraviola.fidreader.networking.response.EntryResponse;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the FragmentInteractionInterface
 * to handle interaction events.
 * Use the {@link EntryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryListFragment extends BaseFragment {

    private static final String FEED_URL = "url";
    private GoogleFeedInterface mFeedInterface;
    private String feedUrl;
    private EntryAdapter adapter;
    private ListView listView;
    private EntriesInterface selectEntryListener;

    /**
     * The listener used to save and load feeds upon state changes / rotation
     */
    private HeadlessFragment.HeadlessEntriesInterface headlessListener;

    public EntryListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param feedUrl feed url.
     * @return A new instance of fragment EntryFragment.
     */
    public static EntryListFragment newInstance(String feedUrl) {
        EntryListFragment fragment = new EntryListFragment();
        Bundle args = new Bundle();
        args.putString(FEED_URL, feedUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            selectEntryListener = (EntriesInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must Implement SelectItemInterface");
        }

        try {
            headlessListener = (HeadlessFragment.HeadlessEntriesInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must Implement HeadlessEntriesInterface");
        }
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeedInterface = GoogleFeedClient.getGoogleFeedInterface();
        if (getArguments() != null) {
            feedUrl = getArguments().getString(FEED_URL);
        } else {
            // Initialized with a default URL for testing purposes.
            feedUrl = "http://googlepress.blogspot.com/feeds/posts/default";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get entries to populate list view
        View view = inflater.inflate(R.layout.fragment_entry, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);
        listView.setEmptyView(view.findViewById(android.R.id.empty));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new EntryAdapter(getActivity(), R.layout.entry_item);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectEntryListener.showEntryDetails(adapter.getItem(position));
            }
        });

        if (headlessListener.loadEntry() != null) {
            adapter.updateFeeds(headlessListener.loadEntry());
        } else {
            loadFeed();
        }
    }

    private void loadFeed() {
        showProgressDialog(R.string.loading_entries);
        mFeedInterface.loadFeed(feedUrl, new Callback<EntryResponse>() {
            @Override
            public void success(EntryResponse entryResponse, Response response) {
                dismissProgressDialog();
                ArrayList entries = entryResponse.getResponseData().getFeed().getEntries();
                adapter.updateFeeds(entries);
                headlessListener.saveEntry(entries);
            }

            @Override
            public void failure(RetrofitError error) {
                dismissProgressDialog();

                /** The request wasn't able to reach the server. */
                if (error.getKind() == RetrofitError.Kind.NETWORK) {
                    showErrorDialog(R.string.retrofit_network_error);
                }

                /** A non-200 HTTP status code was received from the server. */
                if (error.getKind() == RetrofitError.Kind.HTTP) {
                    showErrorDialog(R.string.retrofit_http_error);
                }
            }
        });
    }

}
