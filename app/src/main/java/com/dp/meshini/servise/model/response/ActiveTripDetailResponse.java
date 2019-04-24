package com.dp.meshini.servise.model.response;

import com.dp.meshini.servise.model.pojo.ActiveTripDetail;
import com.google.gson.annotations.SerializedName;

public class ActiveTripDetailResponse {

    @SerializedName("data")
    private ActiveTripDetail activeTripDetail;

    public ActiveTripDetail getActiveTripDetail() {
        return activeTripDetail;
    }

    public void setActiveTripDetail(ActiveTripDetail activeTripDetail) {
        this.activeTripDetail = activeTripDetail;
    }
}
