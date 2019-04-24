package com.dp.meshini.servise.model.pojo;

import android.view.View;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Offer {


    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_image")
    private String profileImageUrl;

    @SerializedName("rating")
    private float rating;

    @SerializedName("trips_count")
    private int tripsCount;

    @SerializedName("price")
    private double price;

    @SerializedName("reviews")
    private List<String>reviews;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getTripsCount() {
        return tripsCount;
    }

    public void setTripsCount(int tripsCount) {
        this.tripsCount = tripsCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

}
