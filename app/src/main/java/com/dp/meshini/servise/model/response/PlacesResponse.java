package com.dp.meshini.servise.model.response;

import com.dp.meshini.servise.model.pojo.Place;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlacesResponse {

    @SerializedName("data")
    private List<Place> places;


    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
