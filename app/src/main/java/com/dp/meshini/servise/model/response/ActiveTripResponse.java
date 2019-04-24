package com.dp.meshini.servise.model.response;

import com.google.gson.annotations.SerializedName;

public class ActiveTripResponse {

    @SerializedName("id")
    private String activeTripId;

    public String getActiveTripId() {
        return activeTripId;
    }

    public void setActiveTripId(String activeTripId) {
        this.activeTripId = activeTripId;
    }
}
