package com.globant.fernandoraviola.fidreader.networking.response;

/**
 * Created by fernando.raviola on 11/12/2014.
 * <p/>
 * Stores all attributes and methods related to a specific FeedResponse.
 * FeedResponse is the Model representation of the JSON response returned by the API.
 */

public class EntryResponse {

    private String responseDetails;
    private int responseStatus;
    private EntryResponseData responseData;

    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public EntryResponseData getResponseData() {
        return responseData;
    }
}
