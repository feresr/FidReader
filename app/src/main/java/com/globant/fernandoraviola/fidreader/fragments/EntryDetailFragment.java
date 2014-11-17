package com.globant.fernandoraviola.fidreader.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.globant.fernandoraviola.fidreader.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailFragment extends BaseFragment {

    private static final String ARG_TITLE = "TITLE";
    private static final String ARG_AUTHOR = "AUTHOR";
    private static final String ARG_DATE = "DATE";
    private static final String ARG_CONTENT = "CONTENT";

    private String title;
    private String author;
    private String date;
    private String content;

    private TextView titleTextView;
    private TextView authorTextView;
    private TextView dateTextView;
    private TextView contentTextView;

    public EntryDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title   title
     * @param author  author
     * @param date    date
     * @param content content
     * @return A new instance of fragment DetailFragment.
     */
    public static EntryDetailFragment newInstance(String title, String author, String date, String content) {
        EntryDetailFragment fragment = new EntryDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_AUTHOR, author);
        args.putString(ARG_DATE, date);
        args.putString(ARG_CONTENT, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            author = getArguments().getString(ARG_AUTHOR);
            date = getArguments().getString(ARG_DATE);
            content = getArguments().getString(ARG_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        titleTextView = (TextView) view.findViewById(R.id.title);
        authorTextView = (TextView) view.findViewById(R.id.author);
        dateTextView = (TextView) view.findViewById(R.id.date);
        contentTextView = (TextView) view.findViewById(R.id.content);

        titleTextView.setText(Html.fromHtml(title));
        authorTextView.setText(Html.fromHtml(author));
        dateTextView.setText(Html.fromHtml(date));
        contentTextView.setText(Html.fromHtml(content));

        return view;
    }
}
