package com.dp.meshini.servise.model.response;

import com.dp.meshini.servise.model.pojo.Links;
import com.dp.meshini.servise.model.pojo.Offer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OffersResponse {

    @SerializedName("data")
    private List<Offer>offers;


    @SerializedName("links")
    private Links links;

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
