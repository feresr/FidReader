package com.globant.fernandoraviola.fidreader.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

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
    private AlertDialog keywordInputDialog;

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

        createKeywordInputDialog();
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
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity.onSectionAttached(section);
    }

    @Override
    public void onResume() {
        super.onResume();
        showKeywordInputDialog();
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

    private void showKeywordInputDialog() {
        if (keywordInputDialog != null && !keywordInputDialog.isShowing()) {
            keywordInputDialog.show();
        }
    }

    private void createKeywordInputDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.keyword_input_title);

        // Set up the input
        final EditText input = new EditText(getActivity());
        input.setHint(R.string.keyword_input_hint);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fetchFeeds(input.getText().toString());
            }
        });

        keywordInputDialog = builder.create();
    }

    private void dismissKeywordInputDialog() {
        if (keywordInputDialog != null && keywordInputDialog.isShowing()) {
            keywordInputDialog.dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissKeywordInputDialog();
    }
}
