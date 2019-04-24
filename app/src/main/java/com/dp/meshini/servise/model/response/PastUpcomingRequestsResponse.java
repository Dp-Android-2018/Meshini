package com.dp.meshini.servise.model.response;

import com.dp.meshini.servise.model.pojo.Links;
import com.dp.meshini.servise.model.pojo.PastUpcomingRequest;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PastUpcomingRequestsResponse {

    @SerializedName("data")
    private List<PastUpcomingRequest>requests;

    @SerializedName("links")
    private Links links;

    public List<PastUpcomingRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<PastUpcomingRequest> requests) {
        this.requests = requests;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
