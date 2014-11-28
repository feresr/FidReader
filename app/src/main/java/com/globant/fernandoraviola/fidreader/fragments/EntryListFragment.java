package com.globant.fernandoraviola.fidreader.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.globant.fernandoraviola.fidreader.R;
import com.globant.fernandoraviola.fidreader.adapters.EntryAdapter;
import com.globant.fernandoraviola.fidreader.models.Entry;
import com.globant.fernandoraviola.fidreader.networking.GoogleFeedClient;
import com.globant.fernandoraviola.fidreader.networking.GoogleFeedInterface;
import com.globant.fernandoraviola.fidreader.networking.response.EntryResponse;

import org.apache.http.HttpStatus;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Shows a list of entry titles to select from and diverts callbacks to its parent activity for
 * proper displaying of their details.
 */
public class EntryListFragment extends BaseFragment {

    public static final String KEY_ENTRIES = "ENTRIES";
    public static final String KEY_SELECTED_ENTRY_INDEX = "ENTRY_INDEX";
    private String feedUrl;
    private EntryAdapter adapter;
    private ListView listView;
    private EntryListCallbacksInterface listCallbackListener;
    private ArrayList<Entry> entries;
    private int selectedEntryIndex = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listCallbackListener = (EntryListCallbacksInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must Implement SelectItemInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //get entries to populate list view
        View view = inflater.inflate(R.layout.fragment_entry_list, container, false);
        listView = (ListView) view.findViewById(android.R.id.list);
        listView.setEmptyView(view.findViewById(android.R.id.empty));
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new EntryAdapter(getActivity(), R.layout.entry_item);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedEntryIndex = position;
                listCallbackListener.showEntryDetails(adapter.getItem(position));
            }
        });

        if (savedInstanceState != null) {
            // Retrieve entries and update adapter
            entries = savedInstanceState.getParcelableArrayList(KEY_ENTRIES);
            if (entries != null) {
                adapter.updateFeeds(entries);
            }
            // Retrieve selected position
            selectedEntryIndex = savedInstanceState.getInt(KEY_SELECTED_ENTRY_INDEX);

        }

        //Update listview with the last selected entry, or 0 if none was selected yet.
        listView.setItemChecked(selectedEntryIndex, true);

    }

    private void loadFeed() {
        showProgressDialog(R.string.loading_entries);
        GoogleFeedInterface mFeedInterface = GoogleFeedClient.getGoogleFeedInterface();
        mFeedInterface.loadFeed(feedUrl, new Callback<EntryResponse>() {
            @Override
            public void success(EntryResponse entryResponse, Response response) {
                dismissProgressDialog();
                if (entryResponse.getResponseStatus() == HttpStatus.SC_OK){
                    entries = entryResponse
                            .getResponseData()
                            .getFeed()
                            .getEntries();
                    adapter.updateFeeds(entries);
                    showPreviouslySelectedEntry();
                } else {
                    Toast.makeText(getActivity(), entryResponse.getResponseDetails(), Toast.LENGTH_SHORT ).show();
                }
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_ENTRIES, entries);
        outState.putInt(KEY_SELECTED_ENTRY_INDEX, selectedEntryIndex);
    }

    public void setFeedUrl(String url) {
        feedUrl = url;
        loadFeed();
    }

    public interface EntryListCallbacksInterface {
        public void showEntryDetails(Entry entry);
    }

    public void showPreviouslySelectedEntry() {
        if(adapter.getCount() > 0 && getActivity().findViewById(R.id.entry_details_frg) != null &&
                getActivity().findViewById(R.id.entry_details_frg).getVisibility() == View.VISIBLE) {
            listCallbackListener.showEntryDetails(adapter.getItem(selectedEntryIndex));
        }
    }
}
