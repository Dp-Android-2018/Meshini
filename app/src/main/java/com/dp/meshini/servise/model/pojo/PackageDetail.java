package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageDetail {

    @SerializedName("package_id")
    private int packageId;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("company_address")
    private String companyAddress;

    @SerializedName("company_image")
    private String companyImageUrl;

    @SerializedName("company_rating")
    private float companyRating;

    @SerializedName("program")
    private Program program;

    @SerializedName("details")
    private String details;

    @SerializedName("photos")
    private List<String>photos;

    @SerializedName("")
    private List<PackageClientReview> reviews;

    @SerializedName("about_company")
    private String aboutCompany;

    @SerializedName("minimum_price")
    private double minimumPrice;

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyImageUrl() {
        return companyImageUrl;
    }

    public void setCompanyImageUrl(String companyImageUrl) {
        this.companyImageUrl = companyImageUrl;
    }

    public float getCompanyRating() {
        return companyRating;
    }

    public void setCompanyRating(float companyRating) {
        this.companyRating = companyRating;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<PackageClientReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<PackageClientReview> reviews) {
        this.reviews = reviews;
    }

    public String getAboutCompany() {
        return aboutCompany;
    }

    public void setAboutCompany(String aboutCompany) {
        this.aboutCompany = aboutCompany;
    }
}
