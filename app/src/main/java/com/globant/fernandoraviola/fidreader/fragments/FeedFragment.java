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
public class FeedFragment extends BaseFragment implements AbsListView.OnItemClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SECTION = "section";
    private ArrayList<Feed> feeds = new ArrayList<Feed>();
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
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FeedFragment() {
    }

    public static FeedFragment newInstance(int section) {
        FeedFragment fragment = new FeedFragment();
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
                android.R.layout.simple_list_item_2, feeds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

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

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity.onSectionAttached(section);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO: to be implemented.
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

}
