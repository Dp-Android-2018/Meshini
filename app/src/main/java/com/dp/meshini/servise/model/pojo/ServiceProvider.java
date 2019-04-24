package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

public class ServiceProvider {

    @SerializedName("trips_count")
    private int tripsCount;

    @SerializedName("profile_picture")
    private String profileImageUrl;

    @SerializedName("rating")
    private float rating;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("phone")
    private String phone;

    public int getTripsCount() {
        return tripsCount;
    }

    public void setTripsCount(int tripsCount) {
        this.tripsCount = tripsCount;
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

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
