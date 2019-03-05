package com.dp.meshini.servise.model.request;

import com.dp.meshini.servise.model.pojo.City;
import com.google.gson.annotations.SerializedName;

public class ClientRegisterRequest {

    @SerializedName("city_id")
    private int cityId;

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
}
