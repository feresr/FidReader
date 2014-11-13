package com.globant.fernandoraviola.fidreader.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.adapters.EntryAdapter;
import com.globant.fernandoraviola.fidreader.networking.GoogleFeedClient;
import com.globant.fernandoraviola.fidreader.networking.GoogleFeedInterface;
import com.globant.fernandoraviola.fidreader.networking.response.EntryResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the FragmentInteractionInterface
 * to handle interaction events.
 * Use the {@link EntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryFragment extends BaseFragment {

    private static final String FEED_URL = "url";
    private GoogleFeedInterface mFeedInterface = GoogleFeedClient.getGoogleFeedInterface();
    private String feedUrl;
    private EntryAdapter adapter;
    private ListView listView;

    public EntryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param feedUrl feed url.
     * @return A new instance of fragment EntryFragment.
     */
    public static EntryFragment newInstance(String feedUrl) {
        EntryFragment fragment = new EntryFragment();
        Bundle args = new Bundle();
        args.putString(FEED_URL, feedUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            feedUrl = getArguments().getString(FEED_URL);
        }

        adapter = new EntryAdapter(getActivity(), R.layout.entry_item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //get entries to populate list view
        View view = inflater.inflate(R.layout.fragment_entry, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);
        listView.setAdapter(adapter);
        listView.setEmptyView(view.findViewById(android.R.id.empty));

        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        loadFeed();
    }

    public void loadFeed() {
        showProgressDialog(R.string.loading_entries);
        mFeedInterface.loadFeed(feedUrl, new Callback<EntryResponse>() {
            @Override
            public void success(EntryResponse entryResponse, Response response) {
                dismissProgressDialog();
                adapter.updateFeeds(entryResponse.getResponseData().getFeed().getEntries());
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
