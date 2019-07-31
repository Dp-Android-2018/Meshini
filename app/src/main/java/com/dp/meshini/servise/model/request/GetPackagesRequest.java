package com.dp.meshini.servise.model.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetPackagesRequest implements Serializable {
    @SerializedName("country_id")
    private int countryId;

    @SerializedName("city_id")
    private int cityId;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;


    @SerializedName("seats_number")
    private int seatsNumber;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }
}
