package com.dp.meshini.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class AcceptOfferRequest {
    @SerializedName("offer_id")
    private int offerId;

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }
}
