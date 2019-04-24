package com.dp.meshini.servise.model.response;

import com.dp.meshini.servise.model.pojo.TripDetail;
import com.google.gson.annotations.SerializedName;

public class TripDetailResponse {
   @SerializedName("data")
    private TripDetail tripDeatail;

    public TripDetail getTripDeatail() {
        return tripDeatail;
    }

    public void setTripDeatail(TripDetail tripDeatail) {
        this.tripDeatail = tripDeatail;
    }
}
