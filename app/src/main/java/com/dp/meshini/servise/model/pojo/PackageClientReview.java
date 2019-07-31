package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

public class PackageClientReview {

    @SerializedName("id")
    private int id;

    @SerializedName("rating")
    private float rating;

    @SerializedName("client_name")
    private String clientName;

    @SerializedName("client_image")
    private String clientImage;

    @SerializedName("comment")
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientImage() {
        return clientImage;
    }

    public void setClientImage(String clientImage) {
        this.clientImage = clientImage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
