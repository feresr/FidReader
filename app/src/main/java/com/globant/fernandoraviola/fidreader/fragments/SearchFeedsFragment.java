package com.globant.fernandoraviola.fidreader.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.activities.SearchFeedsInterface;
import com.globant.fernandoraviola.fidreader.adapters.FeedAdapter;
import com.globant.fernandoraviola.fidreader.models.Feed;
import com.globant.fernandoraviola.fidreader.networking.GoogleFeedClient;
import com.globant.fernandoraviola.fidreader.networking.GoogleFeedInterface;
import com.globant.fernandoraviola.fidreader.networking.response.FeedResponse;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A fragment representing a list of Feeds.
 */
public class SearchFeedsFragment extends BaseFragment implements AbsListView.OnItemClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SECTION = "section";
    /**
     * Handles navigation-drawer related actions and other callbacks to the activity
     */
    protected SearchFeedsInterface fragmentInteractionsListener;
    private GoogleFeedInterface mFeedInterface = GoogleFeedClient.getGoogleFeedInterface();
    private int section;
    private Button searchBtn;
    private TextView searchTxt;
    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private FeedAdapter mAdapter;

    /**
     * The listener used to save and load feeds upon state changes / rotation
     */
    private HeadlessFragment.HeadlessFeedsInterface headlessListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SearchFeedsFragment() {
    }

    public static SearchFeedsFragment newInstance(int section) {
        SearchFeedsFragment fragment = new SearchFeedsFragment();
        Bundle args = new Bundle();
        args.putInt(SECTION, section);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            section = getArguments().getInt(SECTION);
        }

        mAdapter = new FeedAdapter(getActivity(),
                android.R.layout.simple_list_item_2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_feeds, container, false);

        // Set the adapter
        mListView = (ListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(view.findViewById(android.R.id.empty));

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        searchBtn = (Button) view.findViewById(R.id.keyword_search_button);
        searchTxt = (TextView) view.findViewById(R.id.keyword_search_editText);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = searchTxt.getText().toString();
                if (!keyword.isEmpty()) {
                    fetchFeeds(keyword);
                } else {
                    searchTxt.setError("Missing keywords");
                }
            }
        });

        if (headlessListener.loadFeeds() != null) {
            mAdapter.updateFeeds(headlessListener.loadFeeds());
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentInteractionsListener = (SearchFeedsInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement FragmentInteractionsInterface");
        }
        fragmentInteractionsListener.onSectionAttached(section);

        try {
            headlessListener = (HeadlessFragment.HeadlessFeedsInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement HeadlessInterface");
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        fragmentInteractionsListener.showFeedEntries(mAdapter.getItem(position).getUrl());
    }

    private void fetchFeeds(String keyword) {

        showProgressDialog(R.string.loading_feeds);

        mFeedInterface.getFeeds(keyword, new Callback<FeedResponse>() {
            @Override
            public void success(FeedResponse feedResponse, Response response) {
                ArrayList<Feed> feeds = feedResponse.getResponseData().getEntries();
                mAdapter.updateFeeds(feeds);
                headlessListener.saveFeeds(feeds);
                dismissProgressDialog();
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

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteractionsListener = null;
    }
}
