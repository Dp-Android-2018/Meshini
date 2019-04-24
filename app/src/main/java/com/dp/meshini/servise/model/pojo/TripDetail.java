package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TripDetail {
    @SerializedName("id")
    private int id;

    @SerializedName("service_provider")
    private ServiceProvider serviceProvider;

    @SerializedName("country")
    private String country;

    @SerializedName("pickup_address")
    private String pickupAddress;

    @SerializedName("pickup_lat")
    private double pickupLat;

    @SerializedName("date")
    private String date;

    @SerializedName("pickup_time")
    private String pickupTime;

    @SerializedName("pickup_long")
    private double pickupLong;

    @SerializedName("vehicle_type_string")
    private String vehicleType;

    @SerializedName("places")
    private List<String> places;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public double getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(double pickupLat) {
        this.pickupLat = pickupLat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public double getPickupLong() {
        return pickupLong;
    }

    public void setPickupLong(double pickupLong) {
        this.pickupLong = pickupLong;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<String> getPlaces() {
        return places;
    }

    public void setPlaces(List<String> places) {
        this.places = places;
    }
}
