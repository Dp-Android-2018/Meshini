package com.dp.meshini.servise.model.response;

import com.dp.meshini.servise.model.pojo.Links;
import com.dp.meshini.servise.model.pojo.PendingRequest;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingRequestsResponse {

    @SerializedName("data")
    private List<PendingRequest>pendingRequests;


    @SerializedName("links")
    private Links links;

    public Links getLinks() {
        return links;
    }

    public List<PendingRequest> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(List<PendingRequest> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }
}
