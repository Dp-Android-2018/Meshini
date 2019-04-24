package com.dp.meshini.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class CreateCommentRequest {

    @SerializedName("rating")
    private float rate;

    @SerializedName("review")
    private String comment;

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
