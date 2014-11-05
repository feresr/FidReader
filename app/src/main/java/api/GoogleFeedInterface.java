package api;

import models.FeedResponse;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by fernando.raviola on 11/4/2014.
 */
public interface GoogleFeedInterface {

    final static String URL_FIND_FEEDS = "/find?v=1.0&q=Android";

    @GET(URL_FIND_FEEDS)
    public void getFeeds(Callback<FeedResponse> callback);

}
