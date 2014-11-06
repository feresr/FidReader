package networking;

import models.FeedResponse;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by fernando.raviola on 11/4/2014.
 *
 * Specifies the http methods supported by the API along with its parameters and expected responses.
 *
 */
public interface GoogleFeedInterface {

    final static String URL_FIND_FEEDS = "/find?v=1.0&q=Android";

    /**
     * Get a list of feeds to subscribe to.
     * @param callback
     */
    @GET(URL_FIND_FEEDS)
    public void getFeeds(Callback<FeedResponse> callback);

}
