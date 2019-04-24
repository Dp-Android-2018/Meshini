package com.dp.meshini.servise.model.response;

import com.google.gson.annotations.SerializedName;

public class CreateTripResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("id")
    private int id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
