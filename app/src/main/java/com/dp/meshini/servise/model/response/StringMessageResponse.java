package com.dp.meshini.servise.model.response;

import com.google.gson.annotations.SerializedName;

public class StringMessageResponse {

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
