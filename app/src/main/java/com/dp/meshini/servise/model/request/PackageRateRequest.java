package com.dp.meshini.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class PackageRateRequest {

    @SerializedName("company_id")
    private int companyId;

    @SerializedName("rating")
    private float rating;

    @SerializedName("review")
    private String review;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
