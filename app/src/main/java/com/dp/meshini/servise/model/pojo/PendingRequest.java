package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

public class PendingRequest {

    @SerializedName("id")
    private int id;

    @SerializedName("country")
    private String country;

    @SerializedName("date")
    private String date;

    @SerializedName("vehicle_type_string")
    private String vehicleType;

    @SerializedName("pickup_address")
    private String pickupAddress;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }
}
