package com.globant.fernandoraviola.fidreader.networking.response;

/**
 * Created by fernando.raviola on 11/4/2014.
 *
 * Stores all attributes and methods related to a specific FeedResponse.
 * FeedResponse is the Model representation of the JSON response returned by the API.
 *
 */
public class FeedResponse {

    private String responseDetails;
    private String responseStatus;
    private FeedResponseData responseData;


    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public FeedResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(FeedResponseData responseData) {
        this.responseData = responseData;
    }
}
