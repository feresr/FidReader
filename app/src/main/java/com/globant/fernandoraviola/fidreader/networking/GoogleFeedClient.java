package com.globant.fernandoraviola.fidreader.networking;

import android.content.Context;

import retrofit.RestAdapter;

/**
 * Created by fernando.raviola on 11/4/2014.
 * <p/>
 * The purpose of this class is to provide basic configuration to retrofit.
 * It provides the API end-point, log levels, http interceptors, among others.
 */

public class GoogleFeedClient {

    private static final String API_URL = "https://ajax.googleapis.com/ajax/services/feed/";
    private static GoogleFeedInterface mFeedInterface;

    /**
     * @return GoogleFeedInterface - The interface necessary to interact with the Google feed API
     */
    public static GoogleFeedInterface getGoogleFeedInterface(final Context context) {
        if (mFeedInterface == null) {

            RestAdapter restAdapter = new RestAdapter.Builder()
                    //.setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(API_URL)
                    .build();

            mFeedInterface = restAdapter.create(GoogleFeedInterface.class);
        }
        return mFeedInterface;
    }
}
