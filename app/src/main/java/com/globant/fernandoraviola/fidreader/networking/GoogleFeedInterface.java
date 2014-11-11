package com.globant.fernandoraviola.fidreader.networking;

import com.globant.fernandoraviola.fidreader.networking.response.FeedResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by fernando.raviola on 11/4/2014.
 * <p/>
 * Specifies the http methods supported by the API along with its parameters and expected responses.
 */
public interface GoogleFeedInterface {

    final static String URL_FIND_FEEDS = "/find?v=1.0";

    /**
     * Get a list of feeds to subscribe to.
     *
     * @param callback
     */
    @GET(URL_FIND_FEEDS)
    public void getFeeds(@Query("q") String keyword,Callback<FeedResponse> callback);

}
