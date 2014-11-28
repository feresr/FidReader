package com.globant.fernandoraviola.fidreader.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class SearchFeedsFragment extends BaseFragment implements ListView.OnItemClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SECTION = "section";
    private static final String KEY_FEEDS = "KEY_FEEDS";
    /**
     * Handles navigation-drawer related actions and other callbacks to the activity
     */
    protected SearchFeedsInterface fragmentInteractionsListener;
    private GoogleFeedInterface mFeedInterface = GoogleFeedClient.getGoogleFeedInterface();
    private Button searchBtn;
    private TextView searchTxt;
    private ArrayList<Feed> feeds;
    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private FeedAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new FeedAdapter(getActivity(),
                android.R.layout.simple_list_item_2);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            title = getResources().getString(R.string.search_feeds_title);
        }
        super.onActivityCreated(savedInstanceState);
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

        if (savedInstanceState != null) {
            feeds = savedInstanceState.getParcelableArrayList(KEY_FEEDS);
            if (feeds != null) {
                mAdapter.updateFeeds(feeds);
            }
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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        fragmentInteractionsListener.showFeedEntries(mAdapter.getItem(position));
    }

    private void fetchFeeds(String keyword) {

        showProgressDialog(R.string.loading_feeds);

        mFeedInterface.getFeeds(keyword, new Callback<FeedResponse>() {
            @Override
            public void success(FeedResponse feedResponse, Response response) {
                feeds = feedResponse.getResponseData().getEntries();
                mAdapter.updateFeeds(feeds);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_FEEDS, feeds);
    }
}
