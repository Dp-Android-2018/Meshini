package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

public class PastUpcomingRequest extends PendingRequest {

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("service_provider")
    private String serviceProviderName;

    @SerializedName("offer_price")
    private double offerPrice;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(double offerPrice) {
        this.offerPrice = offerPrice;
    }
}
