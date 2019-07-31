package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageData {

    @SerializedName("id")
    private int id;

    @SerializedName("company")
    private String companyName;

    @SerializedName("minimum_price")
    private double price;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("city_name")
    private List<String> cities;

    @SerializedName("image")
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
