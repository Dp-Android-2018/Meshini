package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActiveTripDetail {

   @SerializedName("id")
    private int id;

   @SerializedName("service_provider")
    private ServiceProvider serviceProvider;

   @SerializedName("pickup_address")
    private String pickupAddress;

   @SerializedName("pickup_lat")
    private double pickupLat;

   @SerializedName("pickup_long")
    private double pickupLang;

   @SerializedName("places")
    private List<Place>places;

   @SerializedName("vehicle_type")
   private String vehicleType;

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

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

    public double getPickupLang() {
        return pickupLang;
    }

    public void setPickupLang(double pickupLang) {
        this.pickupLang = pickupLang;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
