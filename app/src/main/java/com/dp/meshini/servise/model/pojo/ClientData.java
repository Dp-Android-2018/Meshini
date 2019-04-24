package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ClientData implements Serializable {

    @SerializedName("id")
    private int userId;

    @SerializedName("countryCityPojo")
    private CountryCityPojo countryCityPojo;

    @SerializedName("phone")
    private String phone;

    @SerializedName("device_token")
    private Object deviceToken;

    @SerializedName("api_token")
    private Object apiToken;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("profile_picture")
    private String profilePicture;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("email")
    private String email;

    @SerializedName("activated")
    private boolean activated;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setCountryCityPojo(CountryCityPojo countryCityPojo) {
        this.countryCityPojo = countryCityPojo;
    }

    public CountryCityPojo getCountryCityPojo() {
        return countryCityPojo;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setDeviceToken(Object deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Object getDeviceToken() {
        return deviceToken;
    }

    public void setApiToken(Object apiToken) {
        this.apiToken = apiToken;
    }

    public Object getApiToken() {
        return apiToken;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}