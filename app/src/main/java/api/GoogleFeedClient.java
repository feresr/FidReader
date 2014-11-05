package api;

import android.content.Context;

import retrofit.RestAdapter;

/**
 * Created by fernando.raviola on 11/4/2014.
 */
public class GoogleFeedClient {

    private static final String API_URL = "https://ajax.googleapis.com/ajax/services/feed/";
    private static GoogleFeedInterface mFeedInterface;

    public static GoogleFeedInterface getGoogleFeedClient(final Context context) {
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
